package org.example.quotationcommand.service;

import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.sharedlibrary.base_class.BaseEvent;

public interface QuotationEventStoreService {

    void save(QuotationAggregate aggregate, BaseEvent event);

    QuotationAggregate load(String id);

}
