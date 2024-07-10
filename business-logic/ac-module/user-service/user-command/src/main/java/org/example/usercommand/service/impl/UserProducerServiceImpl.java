package org.example.usercommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.base_prod_cons.BaseProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducerServiceImpl implements BaseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
