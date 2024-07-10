package org.example.usercommand.service;

import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;

public interface UserCommandHandler {

    void handleCreate(UserCreateCommand command);

    void handleUpdate(UserUpdateCommand command);

    void handleDelete(UserDeleteCommand command);

}
