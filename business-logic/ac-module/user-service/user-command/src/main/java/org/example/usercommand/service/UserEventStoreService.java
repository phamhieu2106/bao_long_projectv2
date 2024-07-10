package org.example.usercommand.service;

import org.example.userdomain.aggregate.UserAggregate;

public interface UserEventStoreService extends EventStoreService {
    UserAggregate getAggregate(String aggregateId);
}
