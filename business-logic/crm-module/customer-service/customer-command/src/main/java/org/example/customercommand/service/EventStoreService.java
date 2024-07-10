package org.example.customercommand.service;

import org.example.customerdomain.aggregate.CustomerAggregate;
import org.example.sharedlibrary.base_class.BaseEvent;

public interface EventStoreService {

    void save(CustomerAggregate aggregate, BaseEvent event);

    CustomerAggregate load(String id);

}
