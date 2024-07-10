package org.example.customercommand.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.customercommand.service.EventStoreService;
import org.example.customerdomain.aggregate.CustomerAggregate;
import org.example.customerdomain.entity.CustomerEventEntity;
import org.example.customerdomain.repository.CustomerEventEntityRepository;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventStoreServiceImpl implements EventStoreService {

    private final CustomerEventEntityRepository eventEntityRepository;

    @Override
    public void save(CustomerAggregate aggregate, BaseEvent event) {
        eventEntityRepository.save(
                new CustomerEventEntity(
                        UUID.randomUUID().toString(),
                        event.getTimestamp(),
                        aggregate.getId(),
                        event.getClass().getSimpleName(),
                        getEventVersion(aggregate.getId()),
                        aggregate,
                        aggregate.getCreatedBy()
                )
        );
    }

    @Override
    public CustomerAggregate load(String id) {
        CustomerEventEntity eventStoreEntity = eventEntityRepository
                .findFirstByAggregateId(id).orElse(null);
        if (eventStoreEntity == null) {
            throw new EntityNotFoundException("Not Found Customer Aggregate");
        }
        return eventStoreEntity.getAggregateData();
    }

    private long getEventVersion(String aggregateId) {
        if (eventEntityRepository.countByAggregateId(aggregateId) == 0) return 1;
        return eventEntityRepository.countByAggregateId(aggregateId) + 1;
    }
}
