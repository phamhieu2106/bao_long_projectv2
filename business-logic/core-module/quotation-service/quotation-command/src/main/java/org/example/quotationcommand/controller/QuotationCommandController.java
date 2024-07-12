package org.example.quotationcommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationService;
import org.example.quotationdomain.command.QuotationChangeStatusCommand;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.sharedlibrary.base_constant.HeaderRequestConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/update/{quotationId}")
    public WrapperResponse update(@RequestBody QuotationUpdateCommand command,
                                  @PathVariable String quotationId,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                          , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        command.setCreatedBy(username);
        command.setQuotationId(quotationId);
        return quotationService.update(command);
    }

    @PostMapping("/change-status")
    public WrapperResponse changeStatus(@RequestBody QuotationChangeStatusCommand command,
                                        @RequestParam String quotationId,
                                        @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
                                                , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        command.setCreatedBy(username);
        command.setId(quotationId);
        return quotationService.changeStatus(command);
    }


//    @PostMapping("/delete/{quotationId}")
//    public WrapperResponse delete(@RequestBody QuotationDeleteCommand command,
//                                  @PathVariable String quotationId,
//                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER
//                                          , defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
//        command.setCreatedBy(username);
//        command.setQuotationId(quotationId);
//        return quotationService.delete(command);
//    }

}
