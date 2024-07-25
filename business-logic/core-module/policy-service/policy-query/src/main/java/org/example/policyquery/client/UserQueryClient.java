package org.example.policyquery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-query", path = "/internal/api/users")
public interface UserQueryClient {

    @GetMapping("/isFirstUsernameHavePermissionEqualsOrGatherThanSecondUsername")
    boolean isFirstUsernameHavePermissionEqualsOrGatherThanSecondUsername(@RequestParam String username, @RequestParam String modifiedBy);
}
