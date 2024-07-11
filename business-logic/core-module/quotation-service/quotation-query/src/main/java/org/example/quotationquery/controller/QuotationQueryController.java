package org.example.quotationquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.quotationquery.request.QuotationPageRequest;
import org.example.quotationquery.service.QuotationEsService;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotations")
@RequiredArgsConstructor
public class QuotationQueryController {

    private final QuotationEsService esService;

    @GetMapping
    public WrapperResponse getQuotationsPage(@RequestBody QuotationPageRequest request) {
        return esService.getQuotationPage(request);
    }

    @GetMapping("/search")
    public WrapperResponse getQuotationSearchPage(@RequestBody QuotationPageRequest request) {
        return esService.getSearchQuotationPage(request);
    }

    @GetMapping("/detail")
    public WrapperResponse getQuotation(@RequestParam String quotationId) {
        return esService.getQuotationDetail(quotationId);
    }

}
