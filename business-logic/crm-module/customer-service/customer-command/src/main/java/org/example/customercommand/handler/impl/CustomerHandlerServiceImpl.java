package org.example.customercommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.customercommand.client.CustomerQueryClient;
import org.example.customercommand.handler.CustomerHandlerService;
import org.example.customercommand.service.CustomerProducerService;
import org.example.customercommand.service.EventStoreService;
import org.example.customerdomain.aggregate.CustomerAggregate;
import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;
import org.example.customerdomain.event.CustomerCreateEvent;
import org.example.customerdomain.event.CustomerDeleteEvent;
import org.example.customerdomain.event.CustomerUpdateEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerHandlerServiceImpl implements CustomerHandlerService {

    private final CustomerProducerService producerService;
    private final EventStoreService eventStoreService;
    private final CustomerQueryClient queryClient;

    @Override
    public void handle(CustomerCreateCommand command) {
        CustomerAggregate aggregate = new CustomerAggregate();
        aggregate.setCustomerCode(queryClient.generateCustomerCode());
        CustomerCreateEvent event = aggregate.apply(command);
        eventStoreService.save(aggregate, event);
        producerService.publish("customer_create", event);
    }

    @Override
    public void handle(CustomerUpdateCommand command) {
        CustomerAggregate aggregate = eventStoreService.load(command.getCustomerId());
        CustomerUpdateEvent event = aggregate.apply(command);
        eventStoreService.save(aggregate, event);
        producerService.publish("customer_update", event);
    }

    @Override
    public void handle(CustomerDeleteCommand command) {
        CustomerAggregate aggregate = eventStoreService.load(command.getCustomerId());
        CustomerDeleteEvent event = aggregate.apply(command);
        eventStoreService.save(aggregate, event);
        producerService.publish("customer_delete", event);
    }
}
