package org.example.quotationcommand.handler;

import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.quotationdomain.command.cud.QuotationCopyCommand;
import org.example.quotationdomain.command.cud.QuotationCreateCommand;
import org.example.quotationdomain.command.cud.QuotationCreateNewVersionCommand;
import org.example.quotationdomain.command.cud.QuotationUpdateCommand;
import org.example.quotationdomain.command.status.*;

import java.util.List;

public interface QuotationHandler {
    void handle(QuotationCreateCommand command);

    void handle(QuotationUpdateCommand command);

    void handle(QuotationScheduleStatusCommand command);

    void handle(QuotationCopyCommand copyCommand);

    void handle(String customerId);

    void handle(List<String> customerIds);

    void releasePolicy(String quotationId);

    void handle(QuotationChangeToAwaitApproveStatusCommand quotationChangeToAwaitApproveStatusCommand);

    void handle(QuotationChangeToApprovedStatusCommand quotationChangeToApprovedStatusCommand);

    void handle(QuotationChangeToDisabledStatusCommand quotationChangeToDisabledStatusCommand);

    void handle(QuotationChangeToRejectedStatusCommand quotationChangeToRejectedStatusCommand);

    void handle(QuotationChangeToRequireInformationStatusCommand quotationChangeToRequireInformationStatusCommand);

    void handle(QuotationCreateNewVersionCommand command);
}
