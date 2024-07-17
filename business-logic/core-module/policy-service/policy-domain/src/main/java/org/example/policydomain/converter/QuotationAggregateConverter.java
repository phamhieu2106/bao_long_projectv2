package org.example.policydomain.converter;

import org.example.policydomain.response.QuotationResponse;
import org.example.sharedlibrary.base_converter.BaseConverter;

public class QuotationAggregateConverter extends BaseConverter<QuotationResponse> {
    public QuotationAggregateConverter() {
        super(QuotationResponse.class);
    }
}
