package org.example.usercommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_prod_cons.BaseProducer;
import org.example.usercommand.client.CustomerQueryClient;
import org.example.usercommand.client.QuotationCommandClient;
import org.example.usercommand.client.UserQueryClient;
import org.example.usercommand.service.UserCommandHandler;
import org.example.usercommand.service.UserEventStoreService;
import org.example.userdomain.aggregate.UserAggregate;
import org.example.userdomain.command.UserCreateCommand;
import org.example.userdomain.command.UserDeleteCommand;
import org.example.userdomain.command.UserUpdateCommand;
import org.example.userdomain.event.UserCreateEvent;
import org.example.userdomain.event.UserDeleteEvent;
import org.example.userdomain.event.UserUpdateEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandHandlerService implements UserCommandHandler {

    private final String TOPIC = "user_service";
    private final UserQueryClient client;
    private final CustomerQueryClient customerQueryClient;
    private final UserEventStoreService userEventStoreService;
    private final BaseProducer producer;
    private final QuotationCommandClient quotationCommandClient;

    @Override
    public void handleCreate(UserCreateCommand command) {
        UserAggregate aggregate = new UserAggregate();
        aggregate.setUserCode(client.generateUserCode());
        UserCreateEvent event = aggregate.applyCreate(command);
        userEventStoreService.storeEvent(UserCreateEvent.class.getSimpleName(), aggregate);
        producer.publish(TOPIC, event);
    }

    @Override
    public void handleUpdate(UserUpdateCommand command) {
        UserAggregate aggregate = userEventStoreService.getAggregate(command.getUserId());
        UserUpdateEvent event = aggregate.applyUpdate(command);
        handleInChargeCustomer(aggregate.getUsername());
        userEventStoreService.storeEvent(UserCreateEvent.class.getSimpleName(), aggregate);
        producer.publish(TOPIC, event);
    }

    @Override
    public void handleDelete(UserDeleteCommand command) {
        UserAggregate aggregate = userEventStoreService.getAggregate(command.getUserId());
        UserDeleteEvent event = aggregate.applyDelete(command);
        userEventStoreService.storeEvent(UserCreateEvent.class.getSimpleName(), aggregate);
        producer.publish(TOPIC, event);
    }

    private void handleInChargeCustomer(String username) {
        List<String> customerIds = customerQueryClient.findAllCustomerIdWithUserUsername(username);
        quotationCommandClient.updateQuotationsStatusByCustomerIds(customerIds);
    }
}
