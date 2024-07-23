package org.example.policyquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.policyquery.service.PolicyInternalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/policies")
public class PolicyInternalController {
    private final PolicyInternalService policyInternalService;

    @GetMapping("/generatePolicyCode")
    public String generatePolicyCode(@RequestParam String productCode) {
        return policyInternalService.getPolicyCode(productCode);
    }

    @GetMapping("/isExitsById")
    public boolean isExitsById(@RequestParam String policyId) {
        return policyInternalService.isExitsById(policyId);
    }

    @PostMapping("/isValidEffectDate")
    public boolean isValidEffectDate(@RequestParam String policyId, @RequestBody Date effectiveDate) {
        return policyInternalService.isValidEffectDate(policyId, effectiveDate);
    }

    @GetMapping("/isCreateAble")
    public boolean isCreateAble(@RequestParam String policyId) {
        return policyInternalService.isCreateAble(policyId);
    }
}
