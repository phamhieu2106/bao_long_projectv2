package org.example.sharedlibrary;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.model.BaseInsuranceModel;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class InsuranceObject {
    String insuranceObjectName;
    String insuranceTypeCode;
    List<BaseInsuranceModel> listInsuranceModel;
}
