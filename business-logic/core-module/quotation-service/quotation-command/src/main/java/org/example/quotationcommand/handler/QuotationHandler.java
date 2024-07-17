package org.example.quotationcommand.handler;

import org.example.quotationdomain.command.*;

import java.util.List;

public interface QuotationHandler {
    void handle(QuotationCreateCommand command);

    void handle(QuotationUpdateCommand command);

    void handle(QuotationChangeStatusCommand command);

    void handle(QuotationScheduleStatusCommand command);

    void handle(QuotationCopyCommand copyCommand);

    void handle(String customerId);

    void handle(List<String> customerIds);

}
