package org.example.quotationcommand.service;

import org.example.quotationdomain.command.QuotationScheduleStatusCommand;

public interface QuotationInternalService {

    void updateQuotationStatus(QuotationScheduleStatusCommand command);

    void updateQuotationStatusByCustomerId(String customerId);
}
