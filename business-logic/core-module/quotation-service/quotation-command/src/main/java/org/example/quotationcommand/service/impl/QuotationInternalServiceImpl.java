package org.example.quotationcommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationInternalService;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationInternalServiceImpl implements QuotationInternalService {

    private final QuotationHandler handler;

    @Override
    public void updateQuotationStatus(QuotationScheduleStatusCommand command) {
        handler.handle(command);
    }

    @Override
    public void updateQuotationStatusByCustomerId(String customerId) {
        handler.handle(customerId);
    }

    @Override
    public void updateQuotationsStatusByCustomerIds(List<String> customerIds) {
        handler.handle(customerIds);
    }
}
