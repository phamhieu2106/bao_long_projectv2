package org.example.customerquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customerdomain.entity.CustomerEntity;
import org.example.customerdomain.repository.CustomerEntityRepository;
import org.example.customerdomain.response.CustomerHistoryResponse;
import org.example.customerdomain.response.CustomerResponse;
import org.example.customerdomain.view.CustomerView;
import org.example.customerquery.request.CustomerPageRequest;
import org.example.customerquery.service.CustomerEsService;
import org.example.sharedlibrary.base_constant.ElasticsearchConstant;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_constant.view.CustomerViewConstant;
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
public class CustomerEsServiceImpl implements CustomerEsService {

    private final CustomerEntityRepository entityRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public WrapperResponse getPageCustomer(CustomerPageRequest request) {

        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(), PageConstant.getSortBys(
                        request.getSortRequests()
                )
        );

        Query query = Query.findAll().setPageable(pageable);
        SearchHits<CustomerView> hits = elasticsearchTemplate.search(query, CustomerView.class);
        SearchPage<CustomerView> page = SearchHitSupport.searchPageFor(hits, query.getPageable());

        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse searchCustomers(CustomerPageRequest request) {

        if (request.getKeyword().isBlank()) {
            return getPageCustomer(request);
        }

        Pageable pageable = PageRequest.of(
                request.getPageNumber(), request.getPageSize(), PageConstant.getSortBys(
                        request.getSortRequests()
                )
        );

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .bool(b -> b
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_ID).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_CODE).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_STATUS).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_NAME_KEYWORD).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_EMAIL).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_PHONE_NUMBER).value(request.getKeyword())))
                                .should(t1 -> t1.term(t -> t.field(CustomerViewConstant.CUSTOMER_IN_CHARGE_BY).value(request.getKeyword())))
                                .should(s2 -> s2.match(m -> m.field(CustomerViewConstant.CUSTOMER_NAME_TEXT_SEARCH).query(
                                                ElasticsearchConstant.returnNoSpecialCharactersString(request.getKeyword().toLowerCase())
                                        ))
                                )
                        )
                );


        Query query = new StringQuery(nativeQuery.toString().substring(6)).setPageable(pageable);
        SearchHits<CustomerView> hits = elasticsearchTemplate.search(query, CustomerView.class);
        SearchPage<CustomerView> page = SearchHitSupport.searchPageFor(hits, query.getPageable());

        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse getCustomer(String customerId) {

        if (customerId == null || customerId.isBlank()) {
            return WrapperResponse.fail(
                    "Invalid Id", HttpStatus.BAD_REQUEST
            );
        }

        Optional<CustomerEntity> optional = entityRepository.findById(customerId);
        if (optional.isEmpty()) {
            return WrapperResponse.fail(
                    "Not Found", HttpStatus.NOT_FOUND
            );
        }

        CustomerEntity customerEntity = optional.get();

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .term(t -> t.field("aggregateId").value(customerEntity.getId())));

        Query query = new StringQuery(nativeQuery.toString().substring(6)).addSort(
                Sort.by(Sort.Direction.DESC, "version")
        );
        SearchHits<CustomerHistoryResponse> hits = elasticsearchTemplate.search(query, CustomerHistoryResponse.class);

        CustomerResponse response = new CustomerResponse(customerEntity, hits.stream().map(SearchHit::getContent).toList());
        return WrapperResponse.success(HttpStatus.OK, response);
    }
}
