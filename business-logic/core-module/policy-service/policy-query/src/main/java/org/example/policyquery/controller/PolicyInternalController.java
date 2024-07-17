package org.example.policyquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.policyquery.service.PolicyInternalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/policies")
public class PolicyInternalController {
    private final PolicyInternalService policyInternalService;

    @GetMapping("/generatePolicyCode")
    public String generatePolicyCode(@RequestParam String productCode) {
        return policyInternalService.getPolicyCode(productCode);
    }
}
