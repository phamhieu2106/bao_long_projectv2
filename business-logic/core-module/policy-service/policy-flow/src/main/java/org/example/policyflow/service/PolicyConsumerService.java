package org.example.policyflow.service;

import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;

public interface PolicyConsumerService {

    void subscribe(PolicyCreateEvent event);

    void subscribe(AdditionalModificationCreateEvent event);
}
