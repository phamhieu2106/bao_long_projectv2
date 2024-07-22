package org.example.policyquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.policyquery.request.PolicyPageRequest;
import org.example.policyquery.service.PolicyEsService;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/policies")
public class PolicyQueryController {

    private final PolicyEsService esService;

    @GetMapping
    public WrapperResponse getQuotationsPage(@RequestBody PolicyPageRequest request) {
        return esService.getPolicyPage(request);
    }

    @GetMapping("/detail")
    public WrapperResponse getQuotation(@RequestParam String policyId) {
        return esService.getPolicyDetail(policyId);
    }
}
