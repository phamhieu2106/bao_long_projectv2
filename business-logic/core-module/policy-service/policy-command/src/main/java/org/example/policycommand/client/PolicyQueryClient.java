package org.example.policycommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "policy-query", path = "/internal/api/policies")
public interface PolicyQueryClient {

    @GetMapping("/generatePolicyCode")
    String generatePolicyCode(@RequestParam String productCode);
}
