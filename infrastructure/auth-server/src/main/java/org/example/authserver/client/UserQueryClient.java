package org.example.authserver.client;

import org.example.authserver.domain.UserDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-query", path = "/internal/api/users")
public interface UserQueryClient {

    @GetMapping("/getUserBy/{username}")
    UserDetail getUserDetailsByUsername(@PathVariable String username);

    @GetMapping("/isExitSByUsername/{username}")
    ResponseEntity<Boolean> isExitSByUsername(@PathVariable String username);
}
