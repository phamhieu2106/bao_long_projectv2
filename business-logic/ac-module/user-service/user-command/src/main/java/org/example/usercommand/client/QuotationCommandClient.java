package org.example.usercommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "quotation-command", path = "/internal/api/quotations")
public interface QuotationCommandClient {

    @PostMapping("/updateQuotationsStatusByCustomerIds")
    void updateQuotationsStatusByCustomerIds(@RequestBody List<String> customerIds);
}
