package org.example.quotationdomain.converter;

import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.sharedlibrary.base_converter.BaseConverter;

public class QuotationAggregateConverter extends BaseConverter<QuotationAggregate> {
    public QuotationAggregateConverter() {
        super(QuotationAggregate.class);
    }
}
