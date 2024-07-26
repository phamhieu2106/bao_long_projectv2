package org.example.policycommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyCommandService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/policies")
public class PolicyInternalController {

    private final PolicyCommandService commandService;

    @PostMapping("/releasePolicy")
    public String releasePolicy(@RequestParam String quotationId) {
        return commandService.releasePolicy(quotationId);
    }

    @PostMapping("/policyUpdateScheduled")
    public void policyUpdateScheduled() {
        commandService.policyUpdateScheduled();
    }
}
