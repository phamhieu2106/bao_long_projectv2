package org.example.policycommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-query", path = "/internal/api/users")
public interface UserQueryClient {
    @GetMapping("/isExitSByUsername/{username}")
    boolean isExitSByUsername(@PathVariable String username);

    @GetMapping("/isHaveEmployeePermission")
    boolean isHaveEmployeePermission(@RequestParam String username);

    @GetMapping("/isHaveDirectorPermission")
    boolean isHaveDirectorPermission(@RequestParam String username);
}
