package org.example.policyquery.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policydomain.response.PolicyHistoryResponse;
import org.example.policydomain.response.PolicyResponse;
import org.example.policydomain.view.PolicyView;
import org.example.policyquery.request.PolicyPageRequest;
import org.example.policyquery.service.PolicyEsService;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_constant.view.PolicyViewConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PolicyEsServiceImpl implements PolicyEsService {

    private final PolicyEntityRepository entityRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public WrapperResponse getPolicyPage(PolicyPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(),
                PageConstant.getSortBys(request.getSortRequests())
        );

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        if (request.getUserOffice() != null && !request.getUserOffice().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_USER_OFFICE).value(request.getUserOffice())));
        }
        if (request.getApartment() != null && !request.getApartment().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_APARTMENT).value(request.getApartment())));
        }
        if (request.getProductType() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_PRODUCT_TYPE).value(request.getProductType().name())));
        }
        if (request.getApprovedAt() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_APPROVED_AT).value(FieldValue.of(request.getApprovedAt()))));
        }

        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            boolQuery.should(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_POLICY_CODE).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.term(t -> t.field(PolicyViewConstant.POLICY_CUSTOMER_NAME_KEYWORD).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(PolicyViewConstant.POLICY_CUSTOMER_NAME).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(PolicyViewConstant.POLICY_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(PolicyViewConstant.POLICY_MOTO_IDENTITY_NUMBER).value(request.getKeyword())))
            ));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(PolicyViewConstant.POLICY_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(PolicyViewConstant.POLICY_MOTO_FRAME_NUMBER).value(request.getKeyword())))
            ));
        }
        if (request.getCreatedFrom() != null && request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));

        } else if (request.getCreatedFrom() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))));

        } else if (request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(PolicyViewConstant.POLICY_TIMESTAMP)
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));
        }

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQUery = boolQuery.build()._toQuery();
        Query query = new StringQuery(nativeQUery.toString().substring(6)).setPageable(pageable);

        SearchHits<PolicyView> hits = elasticsearchTemplate.search(query, PolicyView.class);
        SearchPage<PolicyView> page = SearchHitSupport.searchPageFor(hits, pageable);
        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getPolicyDetail(String policyId) {
        if (policyId == null || policyId.isBlank()) {
            return WrapperResponse.fail(
                    "Invalid Id", HttpStatus.BAD_REQUEST
            );
        }
        Optional<PolicyEntity> optional = entityRepository.findById(policyId);
        if (optional.isEmpty()) {
            return WrapperResponse.fail(
                    "Not Found", HttpStatus.NOT_FOUND
            );
        }
        PolicyEntity policyEntity = optional.get();

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .term(t -> t.field(PolicyViewConstant.POLICY_AGGREGATE_ID).value(policyEntity.getId())));

        Query query = new StringQuery(nativeQuery.toString().substring(6)).addSort(
                Sort.by(Sort.Direction.DESC, PolicyViewConstant.POLICY_VERSION)
        );
        SearchHits<PolicyHistoryResponse> hits = elasticsearchTemplate.search(query, PolicyHistoryResponse.class);
        PolicyResponse response = new PolicyResponse(policyEntity, hits.stream().map(SearchHit::getContent).toList());
        return WrapperResponse.success(HttpStatus.OK, response);
    }
}
