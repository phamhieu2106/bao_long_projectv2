package org.example.customerdomain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDeleteEvent extends BaseEvent {
    String customerId;
    Boolean isDelete = true;

    public CustomerDeleteEvent(Date timestamp, String createdBy, String customerId) {
        super(timestamp, createdBy);
        this.customerId = customerId;
    }
}
