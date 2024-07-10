package org.example.quotationcommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-query", path = "/internal/api/customers")
public interface CustomerQueryClient {

    @GetMapping("/exitsById")
    boolean exitsById(@RequestParam("customerId") String customerId);
}
