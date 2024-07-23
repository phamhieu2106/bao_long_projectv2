package org.example.policycommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyEventStoreService;
import org.example.policycommand.service.PolicyProducerService;
import org.example.policydomain.aggregate.AdditionalModificationAggregate;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyHandlerServiceImpl implements PolicyHandlerService {

    private final PolicyProducerService policyProducerService;
    private final PolicyEventStoreService policyEventStoreService;

    @Override
    public String handle(PolicyCreateCommand command) {
        PolicyAggregate aggregate = new PolicyAggregate();
        PolicyCreateEvent event = aggregate.apply(command);
        policyEventStoreService.storePolicyEvent(aggregate, event);
        policyProducerService.publish("policy_create", event);
        return aggregate.getPolicyCode();
    }

    @Override
    public void handle(AdditionalModificationCreateCommand command) {
        AdditionalModificationAggregate aggregate = new AdditionalModificationAggregate();
        AdditionalModificationCreateEvent event = aggregate.apply(command);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_create", event);
    }
}
