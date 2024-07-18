package org.example.quotationflow.service;

import org.example.quotationdomain.event.crud.QuotationCreateEvent;
import org.example.quotationdomain.event.crud.QuotationDeleteEvent;
import org.example.quotationdomain.event.crud.QuotationUpdateEvent;
import org.example.sharedlibrary.base_prod_cons.BaseConsumer;

public interface QuotationConsumerService extends BaseConsumer<QuotationCreateEvent, QuotationUpdateEvent, QuotationDeleteEvent> {


}
