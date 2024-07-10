package org.example.authserver.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticateRequest {
    String username;
    String password;
}
