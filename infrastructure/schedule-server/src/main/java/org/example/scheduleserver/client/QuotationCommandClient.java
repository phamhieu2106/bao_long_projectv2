package org.example.scheduleserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "quotation-command", path = "/internal/api/quotations")
public interface QuotationCommandClient {

    @GetMapping("/updateStatus")
    void updateQuotationStatus();
}
