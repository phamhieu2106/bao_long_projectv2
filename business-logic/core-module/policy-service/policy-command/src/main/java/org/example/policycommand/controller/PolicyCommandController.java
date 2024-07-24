package org.example.policycommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyCommandService;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToApprovedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToAwaitApproveCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRejectedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRequireInformationCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToUndoneCommand;
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

    @PostMapping("/additional-modification-to-await-approve")
    public WrapperResponse additionalModificationToAwaitApprove(@RequestParam String additionalModificationId,
                                                                @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                        , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationToAwaitApprove(
                additionalModificationId, new AdditionalModificationToAwaitApproveCommand(), username);
    }

    @PostMapping("/additional-modification-to-approved")
    public WrapperResponse additionalModificationToApproved(@RequestParam String additionalModificationId,
                                                            @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                    , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationToApproved(
                additionalModificationId, new AdditionalModificationToApprovedCommand(), username);
    }

    @PostMapping("/additional-modification-to-rejected")
    public WrapperResponse additionalModificationToRejected(@RequestParam String additionalModificationId,
                                                            @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                    , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationToRejected(
                additionalModificationId, new AdditionalModificationToRejectedCommand(), username);
    }

    @PostMapping("/additional-modification-to-require-information")
    public WrapperResponse additionalModificationToRequireInformation(@RequestParam String additionalModificationId,
                                                                      @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                              , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationToRequireInformation(
                additionalModificationId, new AdditionalModificationToRequireInformationCommand(), username);
    }

    @PostMapping("/additional-modification-to-undone")
    public WrapperResponse additionalModificationToUndone(@RequestParam String additionalModificationId,
                                                          @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                                  , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return policyCommandService.additionalModificationToUndone(
                additionalModificationId, new AdditionalModificationToUndoneCommand(), username);
    }
}
