package org.example.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.authserver.service.AuthenticateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/v1/internal")
@RequiredArgsConstructor
public class InternalAuthenticateController {

    private final AuthenticateService authenticateService;

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        return authenticateService.validateToken(token);
    }

    @PostMapping("/encodePassword/{password}")
    public String encodePassword(@PathVariable String password) {
        return authenticateService.encodePassword(password);
    }

}
