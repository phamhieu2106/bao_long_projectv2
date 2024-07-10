package org.example.customercommand.service;

import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;
import org.example.sharedlibrary.base_handler.BaseCommandService;

public interface CustomerCommandService extends BaseCommandService
        <CustomerCreateCommand, CustomerUpdateCommand, CustomerDeleteCommand> {
}
