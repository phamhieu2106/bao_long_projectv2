package org.example.quotationquery.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.domain.view.QuotationView;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.example.quotationdomain.response.QuotationHistoryResponse;
import org.example.quotationdomain.response.QuotationResponse;
import org.example.quotationquery.request.QuotationPageRequest;
import org.example.quotationquery.service.QuotationEsService;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_constant.view.QuotationViewConstant;
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
public class QuotationEsServiceImpl implements QuotationEsService {

    private final ElasticsearchTemplate template;
    private final QuotationEntityRepository entityRepository;

    @Override
    public WrapperResponse getQuotationPage(QuotationPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(),
                PageConstant.getSortBys(request.getSortBys(), request.getSortOrder())
        );

        Query query = Query.findAll().setPageable(pageable);

        SearchHits<QuotationView> hits = template.search(query, QuotationView.class);
        SearchPage<QuotationView> page = SearchHitSupport.searchPageFor(hits, pageable);
        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getSearchQuotationPage(QuotationPageRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(),
                PageConstant.getSortBys(request.getSortRequests())
        );

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();
        if (request.getUserOffice() != null && !request.getUserOffice().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_USER_OFFICE).value(request.getUserOffice())));
        }
        if (request.getApartment() != null && !request.getApartment().isBlank()) {
            boolQuery.must(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_APARTMENT).value(request.getApartment())));
        }
        if (request.getProductType() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_PRODUCT_TYPE).value(request.getProductType().name())));
        }
        if (request.getApprovedAt() != null) {
            boolQuery.must(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_APPROVED_AT).value(FieldValue.of(request.getApprovedAt()))));
        }

        if (request.getKeyword() != null && !request.getKeyword().isBlank()) {
            boolQuery.should(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_QUOTATION_CODE).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.term(t -> t.field(QuotationViewConstant.QUOTATION_CUSTOMER_NAME_KEYWORD).value(request.getKeyword())));
            boolQuery.should(QueryBuilders.match(t -> t.field(QuotationViewConstant.QUOTATION_CUSTOMER_NAME).query(request.getKeyword())));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(QuotationViewConstant.QUOTATION_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(QuotationViewConstant.QUOTATION_MOTO_IDENTITY_NUMBER).value(request.getKeyword())))
            ));
            boolQuery.should(QueryBuilders.nested(nes -> nes.path(QuotationViewConstant.QUOTATION_MOTO_IDENTITY).query(q -> q
                    .term(t -> t.field(QuotationViewConstant.QUOTATION_MOTO_FRAME_NUMBER).value(request.getKeyword())))
            ));
        }
        if (request.getCreatedFrom() != null && request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(QuotationViewConstant.QUOTATION_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));

        } else if (request.getCreatedFrom() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(QuotationViewConstant.QUOTATION_TIMESTAMP)
                    .gte(JsonData.of(request.getCreatedFrom().toInstant()))));

        } else if (request.getCreatedTo() != null) {
            boolQuery.must(QueryBuilders.range(r -> r
                    .field(QuotationViewConstant.QUOTATION_TIMESTAMP)
                    .lte(JsonData.of(request.getCreatedTo().toInstant()))));
        }

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQUery = boolQuery.build()._toQuery();
        Query query = new StringQuery(nativeQUery.toString().substring(6)).setPageable(pageable);

        SearchHits<QuotationView> hits = template.search(query, QuotationView.class);
        SearchPage<QuotationView> page = SearchHitSupport.searchPageFor(hits, pageable);
        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getQuotationDetail(String quotationId) {
        if (quotationId == null || quotationId.isBlank()) {
            return WrapperResponse.fail(
                    "Invalid Id", HttpStatus.BAD_REQUEST
            );
        }
        Optional<QuotationEntity> optional = entityRepository.findById(quotationId);
        if (optional.isEmpty()) {
            return WrapperResponse.fail(
                    "Not Found", HttpStatus.NOT_FOUND
            );
        }
        QuotationEntity quotationEntity = optional.get();

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .term(t -> t.field(QuotationViewConstant.QUOTATION_AGGREGATE_ID).value(quotationEntity.getId())));

        Query query = new StringQuery(nativeQuery.toString().substring(6)).addSort(
                Sort.by(Sort.Direction.DESC, QuotationViewConstant.QUOTATION_VERSION)
        );
        SearchHits<QuotationHistoryResponse> hits = template.search(query, QuotationHistoryResponse.class);
        QuotationResponse response = new QuotationResponse(quotationEntity, hits.stream().map(SearchHit::getContent).toList());
        return WrapperResponse.success(HttpStatus.OK, response);
    }
}
