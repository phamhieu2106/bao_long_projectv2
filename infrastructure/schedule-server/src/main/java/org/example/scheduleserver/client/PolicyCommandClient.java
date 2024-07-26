package org.example.scheduleserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "policy-command", path = "/internal.api/policies")
public interface PolicyCommandClient {

    @PostMapping("/policyUpdateScheduled")
    void policyUpdateScheduled();
}
