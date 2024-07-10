package org.example.userdomain.converter;

import org.example.sharedlibrary.base_converter.BaseConverter;
import org.example.userdomain.aggregate.UserAggregate;

public class UserAggregateConverter extends BaseConverter<UserAggregate> {
    public UserAggregateConverter() {
        super(UserAggregate.class);
    }
}
