package org.example.customerquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customerdomain.entity.CustomerEntity;
import org.example.customerdomain.repository.CustomerEntityRepository;
import org.example.customerdomain.view.CustomerView;
import org.example.customerquery.request.CustomerPageRequest;
import org.example.customerquery.service.CustomerEsService;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
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
                        request.getSortBys(), request.getSortOrder()
                )
        );


        Query query = Query.findAll().setPageable(pageable);
        SearchHits<CustomerView> hits = elasticsearchTemplate.search(query, CustomerView.class);
        SearchPage<CustomerView> page = SearchHitSupport.searchPageFor(hits, query.getPageable());

        return WrapperResponse.success(HttpStatus.OK, page);
    }

    @Override
    public WrapperResponse searchCustomers(String keyword) {

        CustomerPageRequest pageRequest = new CustomerPageRequest();
        Pageable pageable = PageRequest.of(
                pageRequest.getPageNumber(), pageRequest.getPageSize(), PageConstant.getSortBys(
                        pageRequest.getSortBys(), pageRequest.getSortOrder()
                )
        );

        co.elastic.clients.elasticsearch._types.query_dsl.Query nativeQuery =
                co.elastic.clients.elasticsearch._types.query_dsl.Query.of(nq -> nq
                        .bool(b -> b
                                .should(t1 -> t1.term(t -> t.field("id").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("customerCode").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("statusCustomer").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("customerNameKeyword").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("email").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("phoneNumber").value(keyword)))
                                .should(t1 -> t1.term(t -> t.field("inChargeBy").value(keyword)))
                                .should(s2 -> s2.match(m -> m.field("customerName").query(keyword))
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
        return optional.map(customerEntity -> WrapperResponse.success(
                HttpStatus.OK, customerEntity
        )).orElseGet(() -> WrapperResponse.fail(
                "Not Found", HttpStatus.NOT_FOUND
        ));
    }
}
