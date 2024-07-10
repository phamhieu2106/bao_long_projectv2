package org.example.customerflow.service;

import org.example.customerdomain.event.CustomerCreateEvent;
import org.example.customerdomain.event.CustomerDeleteEvent;
import org.example.customerdomain.event.CustomerUpdateEvent;

public interface CustomerConsumerService {

    void subscribe(CustomerCreateEvent event);

    void subscribe(CustomerUpdateEvent event);

    void subscribe(CustomerDeleteEvent event);
}
