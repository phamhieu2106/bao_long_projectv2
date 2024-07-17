package org.example.quotationcommand.service;

import org.example.quotationdomain.command.QuotationScheduleStatusCommand;

import java.util.List;

public interface QuotationInternalService {

    void updateQuotationStatus(QuotationScheduleStatusCommand command);

    void updateQuotationStatusByCustomerId(String customerId);

    void updateQuotationsStatusByCustomerIds(List<String> customerIds);
}
