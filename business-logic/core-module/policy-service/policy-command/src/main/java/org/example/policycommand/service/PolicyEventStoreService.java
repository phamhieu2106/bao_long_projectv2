package org.example.policycommand.service;

import org.example.policydomain.aggregate.AdditionalModificationAggregate;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.sharedlibrary.base_class.BaseEvent;

public interface PolicyEventStoreService {
    void storePolicyEvent(PolicyAggregate policyAggregate, BaseEvent policyCreateEvent);

    void storeAMEvent(AdditionalModificationAggregate aggregate, BaseEvent modificationEventEntity);

    AdditionalModificationAggregate loadAdditionalModificationAggregate(String additionalModificationAggregateId);

    PolicyAggregate loadPolicyAggregate(String policyId);
}
