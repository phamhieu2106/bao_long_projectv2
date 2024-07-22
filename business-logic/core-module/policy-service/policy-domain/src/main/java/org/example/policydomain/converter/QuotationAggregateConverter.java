package org.example.policydomain.converter;

import org.example.policydomain.response.PolicyResponse;
import org.example.sharedlibrary.base_converter.BaseConverter;

public class QuotationAggregateConverter extends BaseConverter<PolicyResponse> {
    public QuotationAggregateConverter() {
        super(PolicyResponse.class);
    }
}
