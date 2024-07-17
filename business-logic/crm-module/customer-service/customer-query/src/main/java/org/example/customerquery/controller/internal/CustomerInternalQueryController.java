package org.example.customerquery.controller.internal;

import lombok.RequiredArgsConstructor;
import org.example.customerquery.service.CustomerQueryService;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/api/customers")
@RequiredArgsConstructor
public class CustomerInternalQueryController {

    private final CustomerQueryService queryService;

    @GetMapping("/exitsById")
    public boolean exitsById(@RequestParam("customerId") String customerId) {
        return queryService.exitsById(customerId);
    }

    @GetMapping("/generateCode")
    public String generateCode() {
        return queryService.generateCode();
    }

    @GetMapping("/existsByPhoneNumber")
    public boolean existsByPhoneNumber(@RequestParam String phoneNumber) {
        return queryService.existsByPhoneNumber(phoneNumber);
    }

    @GetMapping("/existsByEmail")
    public boolean existsByEmail(@RequestParam String email) {
        return queryService.existsByEmail(email);
    }

    @GetMapping("/existsByPhoneNumberAndIdIsNot")
    public boolean existsByPhoneNumberAndIdIsNot(@RequestParam String phoneNumber, @RequestParam String id) {
        return queryService.existsByPhoneNumberAndIdIsNot(phoneNumber, id);
    }

    @GetMapping("/existsByEmailAndIdIsNot")
    public boolean existsByEmailAndIdIsNot(@RequestParam String email, @RequestParam String id) {
        return queryService.existsByEmailAndIdIsNot(email, id);
    }

    @GetMapping("/getCustomerModelById")
    public CustomerModel getCustomerModelById(@RequestParam("customerId") String customerId) {
        return queryService.getCustomerModelById(customerId);
    }

    @GetMapping("/findAllCustomerIdWithUserUsername")
    public List<String> findAllCustomerIdWithUserUsername(@RequestParam String username) {
        return queryService.findAllCustomerIdWithUserUsername(username);
    }
}
