package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.service.PolicyProducerService;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyProducerServiceImpl implements PolicyProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
