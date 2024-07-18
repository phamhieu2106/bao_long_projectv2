package org.example.quotationcommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationService;
import org.example.quotationdomain.command.cud.QuotationCopyCommand;
import org.example.quotationdomain.command.cud.QuotationCreateCommand;
import org.example.quotationdomain.command.cud.QuotationUpdateCommand;
import org.example.quotationdomain.command.status.*;
import org.example.sharedlibrary.base_constant.HeaderRequestConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/quotations")
@RequiredArgsConstructor
public class QuotationCommandController {

    private final QuotationService quotationService;

    @PostMapping("/create")
    public WrapperResponse create(@RequestBody QuotationCreateCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                          , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        command.setCreatedBy(username);
        return quotationService.create(command);
    }

    @PostMapping("/copy")
    public WrapperResponse copy(@RequestParam String quotationId,
                                @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                        , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.copy(new QuotationCopyCommand(quotationId, username));
    }

    @PostMapping("/update/{quotationId}")
    public WrapperResponse update(@RequestBody QuotationUpdateCommand command,
                                  @PathVariable String quotationId,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                          , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        command.setCreatedBy(username);
        command.setQuotationId(quotationId);
        return quotationService.update(command);
    }

    @PostMapping("/toAwaitApprove")
    public WrapperResponse toAwaitApprove(@RequestParam String quotationId,
                                          @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                  , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.toAwaitApprove(new QuotationChangeToAwaitApproveStatusCommand(
                quotationId,
                username
        ));
    }

    @PostMapping("/toApproved")
    public WrapperResponse toApproved(@RequestParam String quotationId,
                                      @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                              , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.toApproved(new QuotationChangeToApprovedStatusCommand(
                quotationId,
                username,
                new Date()
        ));
    }

    @PostMapping("/toDisabled")
    public WrapperResponse toDisabled(@RequestParam String quotationId,
                                      @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                              , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.toDisabled(new QuotationChangeToDisabledStatusCommand(
                quotationId,
                username
        ));
    }

    @PostMapping("/toRejected")
    public WrapperResponse toRejected(@RequestParam String quotationId,
                                      @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                              , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.toRejected(new QuotationChangeToRejectedStatusCommand(
                quotationId,
                username
        ));
    }

    @PostMapping("/toRequireInformation")
    public WrapperResponse toRequireInformation(@RequestParam String quotationId,
                                                @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                        , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        return quotationService.toRequireInformation(new QuotationChangeToRequireInformationStatusCommand(
                quotationId,
                username
        ));
    }

    @PostMapping("/policyRelease")
    public WrapperResponse policyRelease(@RequestParam String quotationId) {
        return quotationService.policyRelease(quotationId);
    }


}
