package org.example.customercommand.handler;

import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;

public interface CustomerHandlerService {

    void handle(CustomerCreateCommand command);

    void handle(CustomerUpdateCommand command);

    void handle(CustomerDeleteCommand command);
}
