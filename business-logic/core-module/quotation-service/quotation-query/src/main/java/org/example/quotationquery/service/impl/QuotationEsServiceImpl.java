package org.example.quotationquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.domain.view.QuotationView;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.example.quotationquery.request.QuotationPageRequest;
import org.example.quotationquery.service.QuotationEsService;
import org.example.sharedlibrary.base_constant.PageConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.Query;
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
    public WrapperResponse getSearchQuotationPage(String keyword) {
        return null;
    }

    @Override
    public WrapperResponse getQuotationDetail(String quotationId) {
        if (quotationId == null || quotationId.isBlank()) {
            return WrapperResponse.fail(
                    "Invalid Id", HttpStatus.BAD_REQUEST
            );
        }
        Optional<QuotationEntity> optional = entityRepository.findById(quotationId);
        return optional.map(customerEntity -> WrapperResponse.success(
                HttpStatus.OK, customerEntity
        )).orElseGet(() -> WrapperResponse.fail(
                "Not Found", HttpStatus.NOT_FOUND
        ));
    }
}
