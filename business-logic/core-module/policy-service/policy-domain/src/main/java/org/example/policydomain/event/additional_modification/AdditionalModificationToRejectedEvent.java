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
public class AdditionalModificationToRejectedEvent extends BaseEvent {
    final AdditionalModificationStatus additionalModificationStatus = AdditionalModificationStatus.REJECTED;
    String additionalModificationId;

    public AdditionalModificationToRejectedEvent(Date timestamp, String createdBy, String additionalModificationId) {
        super(timestamp, createdBy);
        this.additionalModificationId = additionalModificationId;
    }
}
