package org.example.quotationcommand.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "policy-command", path = "/internal/api/policies")
public interface PolicyCommandClient {

    @PostMapping("/releasePolicy")
    String releasePolicy(@RequestParam String quotationId);
}
