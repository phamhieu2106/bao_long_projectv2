package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyEventService;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyEventServiceImpl implements PolicyEventService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(BaseEvent event) {
        kafkaTemplate.send("policy-create", event);
    }
}
