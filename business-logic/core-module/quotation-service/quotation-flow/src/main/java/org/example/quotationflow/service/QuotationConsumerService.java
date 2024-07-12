package org.example.quotationflow.service;

import org.example.quotationdomain.event.QuotationChangeStatusEvent;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationDeleteEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
import org.example.sharedlibrary.base_prod_cons.BaseConsumer;

public interface QuotationConsumerService extends BaseConsumer<QuotationCreateEvent, QuotationUpdateEvent, QuotationDeleteEvent> {

    void handleChangeStatusEvent(QuotationChangeStatusEvent event);

}
