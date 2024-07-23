package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyEventStoreService;
import org.example.policydomain.aggregate.AdditionalModificationAggregate;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.policydomain.entity.AdditionalModificationEventEntity;
import org.example.policydomain.entity.PolicyEventEntity;
import org.example.policydomain.repository.AdditionalModificationEventEntityRepository;
import org.example.policydomain.repository.PolicyEventEntityRepository;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PolicyEventStoreServiceImpl implements PolicyEventStoreService {

    private final PolicyEventEntityRepository policyEventEntityRepository;
    private final AdditionalModificationEventEntityRepository additionalModificationEventEntityRepository;

    @Override
    public void storePolicyEvent(PolicyAggregate policyAggregate, BaseEvent policyEventEntity) {
        policyEventEntityRepository.save(new PolicyEventEntity(
                new Date(),
                policyAggregate.getId(),
                policyEventEntity.getClass().getSimpleName(),
                getPolicyEventVersion(policyAggregate.getId()),
                policyAggregate,
                policyAggregate.getUserCreatedModel().getUsername()
        ));
    }


    @Override
    public void storeAMEvent(AdditionalModificationAggregate aggregate, BaseEvent modificationEventEntity) {
        additionalModificationEventEntityRepository.save(new AdditionalModificationEventEntity(
                new Date(),
                aggregate.getAdditionalModificationId(),
                modificationEventEntity.getClass().getSimpleName(),
                getAMEventVersion(aggregate.getAdditionalModificationId()),
                aggregate,
                aggregate.getCreatedBy()
        ));
    }

    private long getPolicyEventVersion(String aggregateId) {
        if (policyEventEntityRepository.countByAggregateId(aggregateId) == 0) {
            return 1;
        }
        return policyEventEntityRepository.countByAggregateId(aggregateId) + 1;
    }

    private long getAMEventVersion(String aggregateId) {
        if (additionalModificationEventEntityRepository.countByAggregateId(aggregateId) == 0) {
            return 1;
        }
        return additionalModificationEventEntityRepository.countByAggregateId(aggregateId) + 1;
    }

}
