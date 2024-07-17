package org.example.quotationcommand.controller.internal;


import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationInternalService;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/quotations")
@RequiredArgsConstructor
public class QuotationInternalCommandController {

    private final QuotationInternalService internalService;

    @GetMapping("/updateStatus")
    public void updateQuotationStatus() {
        internalService.updateQuotationStatus(new QuotationScheduleStatusCommand());
    }

    @GetMapping("/updateQuotationStatusByCustomerId")
    public void updateQuotationStatusByCustomerId(@RequestParam String customerId) {
        internalService.updateQuotationStatusByCustomerId(customerId);
    }

    @PostMapping("/updateQuotationsStatusByCustomerIds")
    public void updateQuotationsStatusByCustomerIds(@RequestBody List<String> customerIds) {
        internalService.updateQuotationsStatusByCustomerIds(customerIds);
    }

}
