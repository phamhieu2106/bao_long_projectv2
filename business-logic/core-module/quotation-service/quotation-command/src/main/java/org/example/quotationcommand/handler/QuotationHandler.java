package org.example.quotationcommand.handler;

import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;

public interface QuotationHandler {
    void handle(QuotationCreateCommand command);

    void handle(QuotationUpdateCommand command);

}
