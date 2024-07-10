package org.example.quotationquery.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.quotationquery.service.QuotationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/quotations")
@RequiredArgsConstructor
public class QuotationInternalQueryController {

    private final QuotationService quotationService;

    @GetMapping("/get-quotation-code/{productCode}")
    public String getQuotationCode(@PathVariable String productCode) {
        return quotationService.getQuotationCode(productCode);
    }
    
}
