package org.example.customercommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer-query", path = "/internal/api/customers")
public interface CustomerQueryClient {

    @GetMapping("/generateCode")
    String generateCustomerCode();

    @GetMapping("/existsByPhoneNumber")
    boolean existsByPhoneNumber(@RequestParam String phoneNumber);

    @GetMapping("/existsByEmail")
    boolean existsByEmail(@RequestParam String email);

    @GetMapping("/existsByPhoneNumberAndIdIsNot")
    boolean existsByPhoneNumberAndIdIsNot(@RequestParam String phoneNumber, @RequestParam String id);

    @GetMapping("/existsByEmailAndIdIsNot")
    boolean existsByEmailAndIdIsNot(@RequestParam String email, @RequestParam String id);
}
