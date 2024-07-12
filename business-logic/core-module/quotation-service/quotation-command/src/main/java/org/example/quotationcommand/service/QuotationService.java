package org.example.quotationcommand.service;

import org.example.quotationdomain.command.QuotationChangeStatusCommand;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationDeleteCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.sharedlibrary.base_handler.BaseCommandService;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface QuotationService extends BaseCommandService<QuotationCreateCommand, QuotationUpdateCommand,
        QuotationDeleteCommand> {

    WrapperResponse changeStatus(QuotationChangeStatusCommand command);
}
