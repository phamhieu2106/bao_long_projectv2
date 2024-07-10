package org.example.sharedlibrary.base_prod_cons;

import org.example.sharedlibrary.base_class.BaseEvent;

public interface BaseProducer {

    void publish(String topic, BaseEvent event);
}
