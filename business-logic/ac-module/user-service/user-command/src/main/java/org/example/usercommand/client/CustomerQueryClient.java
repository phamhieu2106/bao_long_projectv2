package org.example.usercommand.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-query", path = "/internal/api/customers")
public interface CustomerQueryClient {

    @GetMapping("/findAllCustomerIdWithUserUsername")
    List<String> findAllCustomerIdWithUserUsername(@RequestParam String username);
}
