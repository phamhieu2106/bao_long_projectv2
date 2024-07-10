package org.example.quotationquery.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface QuotationService {

    String getQuotationCode(String productCode);

    boolean exitsById(@PathVariable String quotationId);
}
