package org.example.policycommand.client;

import org.example.sharedlibrary.QuotationEntityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "quotation-query", path = "/internal/api/quotations")
public interface QuotationQueryClient {

    @GetMapping("/getQuotationByQuotationId")
    QuotationEntityResponse getQuotationByQuotationId(@RequestParam String quotationId);

}
