package org.example.policycommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyEventService;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.entity.PolicyEventEntity;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.repository.PolicyEventEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PolicyHandlerServiceImpl implements PolicyHandlerService {

    private final PolicyEventEntityRepository eventEntityRepository;
    private final PolicyEventService policyEventService;

    @Override
    public String handle(PolicyCreateCommand command) {
        PolicyAggregate aggregate = new PolicyAggregate();
        PolicyCreateEvent event = aggregate.apply(command);
        policyEventService.publish(event);
        eventEntityRepository.save(new PolicyEventEntity(
                new Date(),
                aggregate.getId(),
                event.getClass().getSimpleName(),
                getEventVersion(aggregate.getId()),
                aggregate,
                aggregate.getUserCreatedModel().getUsername()
        ));
        return aggregate.getPolicyCode();
    }

    private long getEventVersion(String aggregateId) {
        if (eventEntityRepository.countByAggregateId(aggregateId) == 0) {
            return 1;
        }
        return eventEntityRepository.countByAggregateId(aggregateId) + 1;
    }
}
