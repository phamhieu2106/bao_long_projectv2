package org.example.policydomain.event.policy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyInternalModificationEvent extends BaseEvent {
    String policyId;
    List<Map<String, Object>> additionalData;

    public PolicyInternalModificationEvent(Date timestamp, String createdBy, String policyId, List<Map<String, Object>> additionalData) {
        super(timestamp, createdBy);
        this.policyId = policyId;
        this.additionalData = additionalData;
    }
}
