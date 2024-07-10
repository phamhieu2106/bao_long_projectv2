package org.example.quotationcommand.service;

import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationDeleteCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.sharedlibrary.base_handler.BaseCommandService;

public interface QuotationService extends BaseCommandService<QuotationCreateCommand, QuotationUpdateCommand,
        QuotationDeleteCommand> {
}
