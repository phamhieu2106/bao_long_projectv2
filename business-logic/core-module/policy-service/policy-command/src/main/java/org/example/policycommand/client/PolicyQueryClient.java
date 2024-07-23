package org.example.policycommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name = "policy-query", path = "/internal/api/policies")
public interface PolicyQueryClient {

    @GetMapping("/generatePolicyCode")
    String generatePolicyCode(@RequestParam String productCode);

    @GetMapping("/isExitsById")
    boolean isExitsById(@RequestParam String policyId);

    @PostMapping("/isValidEffectDate")
    boolean isValidEffectDate(@RequestParam String policyId, @RequestBody Date effectiveDate);

    @GetMapping("/isCreateAble")
    boolean isCreateAble(@RequestParam String policyId);
}
