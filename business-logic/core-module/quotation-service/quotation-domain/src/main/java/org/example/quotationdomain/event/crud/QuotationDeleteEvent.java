package org.example.quotationdomain.event.crud;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationDeleteEvent extends BaseEvent {
    String quotationId;

    public QuotationDeleteEvent(Date timestamp, String createdBy, String quotationId) {
        super(timestamp, createdBy);
        this.quotationId = quotationId;
    }
}
