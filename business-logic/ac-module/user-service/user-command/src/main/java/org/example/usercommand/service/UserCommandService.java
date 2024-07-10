package org.example.usercommand.service;

import org.example.sharedlibrary.base_handler.BaseCommandService;
import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;

public interface UserCommandService extends BaseCommandService
        <UserCreateCommand, UserUpdateCommand, UserDeleteCommand> {

}
