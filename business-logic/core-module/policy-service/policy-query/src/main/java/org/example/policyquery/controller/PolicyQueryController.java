package org.example.policyquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.policyquery.request.PolicyPageRequest;
import org.example.policyquery.service.PolicyEsService;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policies")
public class PolicyQueryController {

    private final PolicyEsService esService;

    @GetMapping
    public WrapperResponse getQuotationsPage(@RequestBody PolicyPageRequest request) {
        return esService.getPolicyPage(request);
    }

    //search page
    @GetMapping("/detail")
    public WrapperResponse getPolicy(@RequestParam String policyId) {
        return esService.getPolicyDetail(policyId);
    }
    
    @GetMapping("/additional-modification-detail")
    public WrapperResponse getAdditionalModificationDetail(@RequestParam String additionalModificationId) {
        return esService.getAdditionalModificationDetail(additionalModificationId);
    }

}
