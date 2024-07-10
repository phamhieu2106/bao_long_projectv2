package org.example.quotationcommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationProducerService;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuotationProducerServiceImpl implements QuotationProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publish(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
