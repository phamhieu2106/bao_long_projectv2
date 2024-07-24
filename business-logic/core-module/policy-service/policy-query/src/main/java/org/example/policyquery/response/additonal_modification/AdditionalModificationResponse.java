package org.example.policyquery.response.additonal_modification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalModificationResponse {
    AdditionalModificationEntity additionalModificationEntity;
    PolicyEntity policyEntity;
}
