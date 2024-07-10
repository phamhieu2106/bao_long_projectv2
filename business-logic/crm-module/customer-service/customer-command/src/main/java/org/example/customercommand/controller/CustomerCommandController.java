package org.example.customercommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.customercommand.service.CustomerCommandService;
import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;
import org.example.sharedlibrary.base_constant.HeaderRequestConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerCommandController {

    private final CustomerCommandService customerCommandService;

    @PostMapping("/create")
    public WrapperResponse create(@RequestBody CustomerCreateCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER, defaultValue = "anonymous") String username) {
        command.setCreatedBy(username);
        return customerCommandService.create(command);
    }

    @PostMapping("/update/{customerId}")
    public WrapperResponse update(@RequestBody CustomerUpdateCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER, defaultValue = "anonymous") String username,
                                  @PathVariable String customerId) {
        command.setCreatedBy(username);
        command.setCustomerId(customerId);
        return customerCommandService.update(command);
    }

    @PostMapping("/delete/{customerId}")
    public WrapperResponse delete(@RequestBody CustomerDeleteCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER, defaultValue = "anonymous") String username,
                                  @PathVariable String customerId) {
        command.setCreatedBy(username);
        command.setCustomerId(customerId);
        return customerCommandService.delete(command);
    }
}
