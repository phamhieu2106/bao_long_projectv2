package org.example.userquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_request.BaseRequest;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.userquery.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public WrapperResponse getAllUsers(@RequestBody BaseRequest request) {
        try {
            return WrapperResponse.success(HttpStatus.OK, userService.findAll(request));
        } catch (Exception e) {
            return WrapperResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{userId}")
    public WrapperResponse getUser(@PathVariable String userId) {
        try {
            return WrapperResponse.success(HttpStatus.OK, userService.findById(userId));
        } catch (Exception e) {
            return WrapperResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
