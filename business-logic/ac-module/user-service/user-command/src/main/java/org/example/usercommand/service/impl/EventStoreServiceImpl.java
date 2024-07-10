package org.example.usercommand.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.usercommand.service.UserEventStoreService;
import org.example.userdomain.aggregate.UserAggregate;
import org.example.userdomain.domain.UserEventStoreEntity;
import org.example.userdomain.repository.UserEventStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventStoreServiceImpl implements UserEventStoreService {

    @Autowired
    private UserEventStoreRepository eventStoreRepository;

    @Override
    public void storeEvent(String eventName, UserAggregate aggregate) {
        eventStoreRepository.save(new UserEventStoreEntity(
                new Date(),
                aggregate.getUserId(),
                eventName,
                getEventVersion(aggregate.getUserId()),
                aggregate,
                aggregate.getCreatedBy()
        ));
    }

    @Override
    public UserAggregate getAggregate(String aggregateId) {
        UserEventStoreEntity eventStoreEntity = eventStoreRepository.findFirstByAggregateId(aggregateId).orElse(null);
        if (eventStoreEntity == null) {
            throw new EntityNotFoundException("Not Found User Aggregate");
        }
        return eventStoreEntity.getAggregateData();
    }

    private long getEventVersion(String aggregateId) {
        if (eventStoreRepository.countByAggregateId(aggregateId) == 0) {
            return 1;
        }
        return eventStoreRepository.countByAggregateId(aggregateId) + 1;
    }
}
