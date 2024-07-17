package org.example.policycommand.service;

import org.example.sharedlibrary.base_class.BaseEvent;

public interface PolicyEventService {

    void publish(BaseEvent event);
}
