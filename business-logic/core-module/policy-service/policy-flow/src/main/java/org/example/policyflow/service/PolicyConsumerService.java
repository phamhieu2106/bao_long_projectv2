package org.example.policyflow.service;

import org.example.policydomain.event.PolicyCreateEvent;

public interface PolicyConsumerService {

    void subscribe(PolicyCreateEvent event);
}
