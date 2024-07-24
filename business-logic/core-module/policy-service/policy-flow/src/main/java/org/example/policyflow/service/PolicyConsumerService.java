package org.example.policyflow.service;

import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToApprovedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToAwaitApproveEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRejectedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRequireInformationEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToUndoneEvent;

public interface PolicyConsumerService {

    void subscribe(PolicyCreateEvent event);

    void subscribe(AdditionalModificationCreateEvent event);

    void subscribe(AdditionalModificationToAwaitApproveEvent event);

    void subscribe(AdditionalModificationToApprovedEvent event);

    void subscribe(AdditionalModificationToRequireInformationEvent event);

    void subscribe(AdditionalModificationToRejectedEvent event);

    void subscribe(AdditionalModificationToUndoneEvent event);
}
