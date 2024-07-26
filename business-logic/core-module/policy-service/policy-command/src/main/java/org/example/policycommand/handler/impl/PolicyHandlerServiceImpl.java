package org.example.policycommand.handler.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.policycommand.client.PolicyQueryClient;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyEventStoreService;
import org.example.policycommand.service.PolicyProducerService;
import org.example.policydomain.aggregate.AdditionalModificationAggregate;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToApprovedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToAwaitApproveCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRejectedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRequireInformationCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToUndoneCommand;
import org.example.policydomain.command.policy.PolicyUpdateInternalAMCommand;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToApprovedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToAwaitApproveEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRejectedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRequireInformationEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToUndoneEvent;
import org.example.policydomain.event.policy.PolicyUpdateInternalAMEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyHandlerServiceImpl implements PolicyHandlerService {

    private final PolicyProducerService policyProducerService;
    private final PolicyEventStoreService policyEventStoreService;
    private final PolicyQueryClient policyQueryClient;

    //Policy
    @Override
    public String handle(PolicyCreateCommand command) {
        PolicyAggregate aggregate = new PolicyAggregate();
        PolicyCreateEvent event = aggregate.apply(command);
        policyEventStoreService.storePolicyEvent(aggregate, event);
        policyProducerService.publish("policy_create", event);
        return aggregate.getPolicyCode();
    }


    //AM
    @Override
    public void handle(AdditionalModificationCreateCommand command) {
        AdditionalModificationAggregate aggregate = new AdditionalModificationAggregate();
        AdditionalModificationCreateEvent event = aggregate.apply(command);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_create", event);
    }

    @Override
    public void handle(AdditionalModificationToAwaitApproveCommand command) {
        AdditionalModificationAggregate aggregate = policyEventStoreService.loadAdditionalModificationAggregate(command.getAdditionalModificationId());
        if (aggregate == null) throw new EntityNotFoundException();
        AdditionalModificationToAwaitApproveEvent event = aggregate.apply(command);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_to_await_approve", event);
    }

    @Override
    public void handle(AdditionalModificationToApprovedCommand additionalModificationToApprovedCommand) {
        AdditionalModificationAggregate aggregate = policyEventStoreService.loadAdditionalModificationAggregate(additionalModificationToApprovedCommand.getAdditionalModificationId());
        if (aggregate == null) throw new EntityNotFoundException();
        aggregate.setAdditionalModificationCode(policyQueryClient.generateAMCode(
                aggregate.getPolicyId(), aggregate.getAdditionalModificationId(), aggregate.getModificationType()));
        AdditionalModificationToApprovedEvent event = aggregate.apply(additionalModificationToApprovedCommand);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_to_approved", event);
    }

    @Override
    public void handle(AdditionalModificationToRejectedCommand additionalModificationToRejectedCommand) {
        AdditionalModificationAggregate aggregate = policyEventStoreService.loadAdditionalModificationAggregate(additionalModificationToRejectedCommand.getAdditionalModificationId());
        if (aggregate == null) throw new EntityNotFoundException();
        AdditionalModificationToRejectedEvent event = aggregate.apply(additionalModificationToRejectedCommand);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_to_rejected", event);
    }

    @Override
    public void handle(AdditionalModificationToRequireInformationCommand additionalModificationToRequireInformationCommand) {
        AdditionalModificationAggregate aggregate = policyEventStoreService.loadAdditionalModificationAggregate(additionalModificationToRequireInformationCommand.getAdditionalModificationId());
        if (aggregate == null) throw new EntityNotFoundException();
        AdditionalModificationToRequireInformationEvent event = aggregate.apply(additionalModificationToRequireInformationCommand);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_to_require_information", event);
    }

    @Override
    public void handle(AdditionalModificationToUndoneCommand additionalModificationToUndoneCommand) {
        AdditionalModificationAggregate aggregate = policyEventStoreService.loadAdditionalModificationAggregate(additionalModificationToUndoneCommand.getAdditionalModificationId());
        if (aggregate == null) throw new EntityNotFoundException();
        AdditionalModificationToUndoneEvent event = aggregate.apply(additionalModificationToUndoneCommand);
        policyEventStoreService.storeAMEvent(aggregate, event);
        policyProducerService.publish("additional_modification_to_undone", event);
    }

    @Override
    public void handle(PolicyUpdateInternalAMCommand command) {
        PolicyAggregate aggregate = policyEventStoreService.loadPolicyAggregate(command.getPolicyId());
        if (aggregate == null) throw new EntityNotFoundException();
        PolicyUpdateInternalAMEvent event = aggregate.apply(command);
        policyEventStoreService.storePolicyEvent(aggregate, event);
        policyProducerService.publish("policy_internal_update", event);
    }
}
