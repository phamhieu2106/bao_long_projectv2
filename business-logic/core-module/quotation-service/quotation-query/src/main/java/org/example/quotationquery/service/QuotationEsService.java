package org.example.quotationquery.service;

import org.example.quotationquery.request.QuotationPageRequest;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface QuotationEsService {

    WrapperResponse getQuotationPage(QuotationPageRequest request);

    WrapperResponse getSearchQuotationPage(String keyword);

    WrapperResponse getQuotationDetail(String quotationId);
}
