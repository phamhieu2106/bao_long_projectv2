package org.example.policycommand.service;

import org.example.sharedlibrary.base_class.BaseEvent;

public interface PolicyProducerService {

    void publish(String topic, BaseEvent event);
}
