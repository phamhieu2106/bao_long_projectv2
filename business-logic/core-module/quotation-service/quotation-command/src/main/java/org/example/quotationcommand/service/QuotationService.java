package org.example.quotationcommand.service;

import org.example.quotationdomain.command.cud.*;
import org.example.quotationdomain.command.status.*;
import org.example.sharedlibrary.base_handler.BaseCommandService;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface QuotationService extends BaseCommandService<QuotationCreateCommand, QuotationUpdateCommand,
        QuotationDeleteCommand> {

    WrapperResponse copy(QuotationCopyCommand quotationCopyCommand);

    WrapperResponse policyRelease(String quotationId);

    WrapperResponse toAwaitApprove(QuotationChangeToAwaitApproveStatusCommand quotationChangeToAwaitApproveStatusCommand);

    WrapperResponse toApproved(QuotationChangeToApprovedStatusCommand quotationChangeToApprovedStatusCommand);

    WrapperResponse toDisabled(QuotationChangeToDisabledStatusCommand quotationChangeToDisabledStatusCommand);

    WrapperResponse toRejected(QuotationChangeToRejectedStatusCommand quotationChangeToRejectedStatusCommand);

    WrapperResponse toRequireInformation(QuotationChangeToRequireInformationStatusCommand quotationChangeToRequireInformationStatusCommand);

    WrapperResponse copyNewQuotationVersion(QuotationCreateNewVersionCommand command);
}
