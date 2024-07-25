package org.example.policyquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.policyquery.service.PolicyInternalService;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
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

    @GetMapping("/isChangeAMStatusAble")
    public boolean isChangeAMStatusAble(@RequestParam String username, @RequestParam String additionalModificationId) {
        return policyInternalService.isChangeAMStatusAble(username, additionalModificationId);
    }

    @GetMapping("/isToAwaitApproveAble")
    public boolean isToAwaitApproveAble(@RequestParam String additionalModificationId) {
        return policyInternalService.isToAwaitApproveAble(additionalModificationId);
    }

    @GetMapping("/isToRequireInformationAble")
    boolean isToRequireInformationAble(@RequestParam String additionalModificationId) {
        return policyInternalService.isToRequireInformationAble(additionalModificationId);
    }

    @GetMapping("/isToApprovedAble")
    boolean isToApprovedAble(@RequestParam String additionalModificationId) {
        return policyInternalService.isToApprovedAble(additionalModificationId);
    }

    @GetMapping("/isToRejectedAble")
    boolean isToRejectedAble(@RequestParam String additionalModificationId) {
        return policyInternalService.isToRejectedAble(additionalModificationId);
    }

    @GetMapping("/isToUndoneAble")
    boolean isToUndoneAble(@RequestParam String additionalModificationId) {
        return policyInternalService.isToUndoneAble(additionalModificationId);
    }

    @GetMapping("/generateAMCode")
    public String generateAMCode(@RequestParam String policyId, @RequestParam String additionalModificationId, @RequestParam ModificationType modificationType) {
        return policyInternalService.generateAMCode(policyId, additionalModificationId, modificationType);
    }
}
