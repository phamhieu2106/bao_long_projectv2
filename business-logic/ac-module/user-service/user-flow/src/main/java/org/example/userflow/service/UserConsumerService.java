package org.example.userflow.service;

import org.example.sharedlibrary.base_prod_cons.BaseConsumer;
import org.example.userdomain.event.UserCreateEvent;
import org.example.userdomain.event.UserDeleteEvent;
import org.example.userdomain.event.UserUpdateEvent;

public interface UserConsumerService extends BaseConsumer<
        UserCreateEvent, UserUpdateEvent, UserDeleteEvent> {
}
