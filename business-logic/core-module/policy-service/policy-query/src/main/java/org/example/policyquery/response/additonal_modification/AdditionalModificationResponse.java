package org.example.policyquery.response.additonal_modification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.view.AdditionalModificationHistoryView;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalModificationResponse {
    AdditionalModificationEntity additionalModificationEntity;
    PolicyEntity policyEntity;
    Map<String, Object> mapChanged;
    List<AdditionalModificationHistoryView> historyViews;
}
