package org.example.usercommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.usercommand.client.AuthServerClient;
import org.example.usercommand.client.UserQueryClient;
import org.example.usercommand.service.UserCommandHandler;
import org.example.usercommand.service.UserCommandService;
import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {

    private final UserQueryClient client;
    private final AuthServerClient authServerClient;
    private final UserCommandHandler handler;

    @Override
    public WrapperResponse create(UserCreateCommand command) {
        if (client.isExitSByUsername(command.getUsername())) {
            return WrapperResponse.fail("Username exits", HttpStatus.BAD_REQUEST);
        }
        try {
            command.setPassword(authServerClient.encodePassword(command.getPassword()));
            handler.handleCreate(command);
            return WrapperResponse.success(HttpStatus.OK, null);
        } catch (Exception e) {
            return WrapperResponse.fail(
                    "Fail when trying create Event", HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public WrapperResponse update(UserUpdateCommand command) {
        try {
            if (!client.isExitSById(command.getUserId())) {
                return WrapperResponse.fail(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            authServerClient.encodePassword(command.getPassword());
            handler.handleUpdate(command);
            return WrapperResponse.success(HttpStatus.OK, null);
        } catch (Exception e) {
            return WrapperResponse.fail(
                    "Fail when trying update Event", HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public WrapperResponse delete(UserDeleteCommand command) {
        try {
            if (!client.isExitSById(command.getUserId())) {
                return WrapperResponse.fail(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
            }
            handler.handleDelete(command);
            return WrapperResponse.success(HttpStatus.OK, null);
        } catch (Exception e) {
            return WrapperResponse.fail(
                    "Fail when trying delete Event", HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
