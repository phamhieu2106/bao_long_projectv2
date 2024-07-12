package org.example.quotationcommand.client;

import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-query", path = "/internal/api/users")
public interface UserQueryClient {

    @GetMapping("/isExitSByUsername/{username}")
    boolean isExitSByUsername(@PathVariable String username);

    @GetMapping("/getUserModelById")
    UserCreatedModel getUserModelById(@RequestParam String username);

    @GetMapping("/getUserModelByUsername")
    UserModel getUserModelByUsername(@RequestParam String username);
}
