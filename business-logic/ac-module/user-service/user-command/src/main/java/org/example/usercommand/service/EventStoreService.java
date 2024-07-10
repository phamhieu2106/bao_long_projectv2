package org.example.usercommand.service;

import org.example.userdomain.aggregate.UserAggregate;

public interface EventStoreService {
    void storeEvent(String eventName, UserAggregate aggregate);
}
