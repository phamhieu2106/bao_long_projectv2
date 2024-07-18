package org.example.quotationdomain.event.status;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.ac.Role;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationChangeToRejectedStatusEvent extends BaseEvent {
    final QuotationStatus quotationStatus = QuotationStatus.REJECTED;
    String quotationId;
    String createdBy;
    Role lastUserRoleUpdate;

    public QuotationChangeToRejectedStatusEvent(Date timestamp, String createdBy, String quotationId, Role lastUserRoleUpdate) {
        super(timestamp, createdBy);
        this.quotationId = quotationId;
        this.lastUserRoleUpdate = lastUserRoleUpdate;
    }
}
