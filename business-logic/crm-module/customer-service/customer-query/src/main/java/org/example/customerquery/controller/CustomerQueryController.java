package org.example.customerquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.customerquery.request.CustomerPageRequest;
import org.example.customerquery.service.CustomerEsService;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerQueryController {

    private final CustomerEsService customerEsService;

    @GetMapping
    public WrapperResponse getCustomers(@RequestBody CustomerPageRequest request) {
        return customerEsService.getPageCustomer(request);
    }

    @GetMapping("/search")
    public WrapperResponse searchCustomers(@RequestParam String keyword) {
        return customerEsService.searchCustomers(keyword);
    }

    @GetMapping("/detail")
    public WrapperResponse getCustomer(@RequestParam String customerId) {
        return customerEsService.getCustomer(customerId);
    }
}
