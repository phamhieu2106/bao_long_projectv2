package org.example.customerdomain.converter;

import org.example.customerdomain.aggregate.CustomerAggregate;
import org.example.sharedlibrary.base_converter.BaseConverter;

public class CustomerAggregateConverter extends BaseConverter<CustomerAggregate> {
    public CustomerAggregateConverter() {
        super(CustomerAggregate.class);
    }
}
