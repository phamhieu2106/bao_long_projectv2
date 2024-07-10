package org.example.sharedlibrary.model.health;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.HealthInsuranceType;
import org.example.sharedlibrary.model.BaseInsuranceTypeModel;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealBenefitModel extends BaseInsuranceTypeModel {
    List<Map<HealthInsuranceType, HealthInsuranceTypeModel>> benefits;
}
