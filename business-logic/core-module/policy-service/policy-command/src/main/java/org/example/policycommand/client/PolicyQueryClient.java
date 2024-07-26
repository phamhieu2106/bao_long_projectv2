package org.example.policycommand.client;

import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

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

    @GetMapping("/isToAwaitApproveAble")
    boolean isToAwaitApproveAble(@RequestParam String additionalModificationId);

    @GetMapping("/isToRequireInformationAble")
    boolean isToRequireInformationAble(@RequestParam String additionalModificationId);

    @GetMapping("/isToApprovedAble")
    boolean isToApprovedAble(@RequestParam String additionalModificationId);

    @GetMapping("/isToRejectedAble")
    boolean isToRejectedAble(@RequestParam String additionalModificationId);

    @GetMapping("/isToUndoneAble")
    boolean isToUndoneAble(@RequestParam String additionalModificationId);

    @GetMapping("/generateAMCode")
    String generateAMCode(@RequestParam String policyId, @RequestParam String additionalModificationId, @RequestParam ModificationType modificationType);

    @GetMapping("/isChangeAMStatusAble")
    boolean isChangeAMStatusAble(@RequestParam String username, @RequestParam String additionalModificationId);

    @GetMapping("/isChangeAMStatusAble")
    List<AdditionalModificationEntity> findAllAMEffected();
}
