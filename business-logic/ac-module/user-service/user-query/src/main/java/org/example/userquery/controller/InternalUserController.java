package org.example.userquery.controller;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.userdomain.domain.UserEntity;
import org.example.userquery.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/api/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/getUserBy/{username}")
    public UserEntity getUserDetailsByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/getUserModelById")
    public UserCreatedModel getUserModelById(@RequestParam String username) {
        return userService.getUserModelById(username);
    }

    @GetMapping("/generateCode")
    public ResponseEntity<String> generateUserCode() {
        return ResponseEntity.ok(userService.generateUserCode());
    }

    @GetMapping("/isExitSByUsername/{username}")
    public ResponseEntity<Boolean> isExitSByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.isExitSByUsername(username));
    }

    @GetMapping("/isExitSById/{userId}")
    public ResponseEntity<Boolean> isExitSById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.isExitsById(userId));
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserEntity> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }
}
