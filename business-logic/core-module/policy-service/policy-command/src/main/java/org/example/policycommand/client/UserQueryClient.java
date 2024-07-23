package org.example.policycommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-query", path = "/internal/api/users")
public interface UserQueryClient {
    @GetMapping("/isExitSByUsername/{username}")
    boolean isExitSByUsername(@PathVariable String username);
}
