package org.example.policydomain.event.additional_modification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalModificationCreateEvent extends BaseEvent {
    final AdditionalModificationStatus modificationStatus = AdditionalModificationStatus.DRAFTING;
    String additionalModificationId;
    String additionalModificationCode;
    String policyId;
    ModificationType modificationType;
    ModificationTypeName modificationTypeName;
    List<Map<String, Object>> additionalData;
    Date effectiveDate;

    String createdBy;
    Date createdAt;
}
