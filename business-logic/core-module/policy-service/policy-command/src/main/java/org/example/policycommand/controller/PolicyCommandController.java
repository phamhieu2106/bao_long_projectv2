package org.example.policycommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyCommandService;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.sharedlibrary.base_constant.HeaderRequestConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyCommandController {
    private final PolicyCommandService policyCommandService;

    @PostMapping("/additional-modification-create")
    public WrapperResponse additionalModificationCreate(@RequestBody AdditionalModificationCreateCommand command,
                                                        @RequestParam String policyId,
                                                        @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationCreate(policyId, command, username);
    }
}
