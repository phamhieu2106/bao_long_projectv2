package org.example.policydomain.event.additional_modification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AdditionalModificationToApprovedEvent extends BaseEvent {
    final AdditionalModificationStatus additionalModificationStatus = AdditionalModificationStatus.APPROVED;
    String additionalModificationId;
    Date approvedAt;
    String approvedBy;
    String additionalModificationCode;

    public AdditionalModificationToApprovedEvent(Date timestamp, String createdBy, String additionalModificationId, String additionalModificationCode, String approvedBy) {
        super(timestamp, createdBy);
        this.additionalModificationId = additionalModificationId;
        this.additionalModificationCode = additionalModificationCode;
        this.approvedBy = approvedBy;
    }
}
