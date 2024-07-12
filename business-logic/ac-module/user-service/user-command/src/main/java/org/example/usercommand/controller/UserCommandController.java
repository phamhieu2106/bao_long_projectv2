package org.example.usercommand.controller;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_constant.HeaderRequestConstant;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.usercommand.service.UserCommandService;
import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/create")
    public WrapperResponse create(@RequestBody UserCreateCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER,
                                          defaultValue = HeaderRequestConstant.ANONYMOUS) String username) {
        command.setCreatedBy(username);
        return userCommandService.create(command);
    }

    @PostMapping("/update/{id}")
    public WrapperResponse update(@RequestBody UserUpdateCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER,
                                          defaultValue = HeaderRequestConstant.ANONYMOUS) String username,
                                  @PathVariable String id) {
        command.setCreatedBy(username);
        command.setUserId(id);
        return userCommandService.update(command);
    }

    @PostMapping("/delete/{id}")
    public WrapperResponse delete(@RequestBody UserDeleteCommand command,
                                  @RequestHeader(value = HeaderRequestConstant.REQUEST_HEADER,
                                          defaultValue = HeaderRequestConstant.ANONYMOUS) String username,
                                  @PathVariable String id) {
        command.setCreatedBy(username);
        command.setUserId(id);
        return userCommandService.delete(command);
    }
}
