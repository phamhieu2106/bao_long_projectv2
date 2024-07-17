package org.example.quotationcommand.service;

import org.example.quotationdomain.command.*;
import org.example.sharedlibrary.base_handler.BaseCommandService;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface QuotationService extends BaseCommandService<QuotationCreateCommand, QuotationUpdateCommand,
        QuotationDeleteCommand> {

    WrapperResponse changeStatus(QuotationChangeStatusCommand command);

    WrapperResponse copy(QuotationCopyCommand quotationCopyCommand);
}
