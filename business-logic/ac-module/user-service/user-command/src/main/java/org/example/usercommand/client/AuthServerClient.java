package org.example.usercommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "auth-server", path = "/auth/v1/internal")
public interface AuthServerClient {
    @PostMapping("/encodePassword/{password}")
    String encodePassword(@PathVariable String password);
}
