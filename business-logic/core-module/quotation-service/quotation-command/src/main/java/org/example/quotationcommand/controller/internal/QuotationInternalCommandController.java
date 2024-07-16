package org.example.quotationcommand.controller.internal;


import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationInternalService;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
