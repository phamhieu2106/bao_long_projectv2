package org.example.sharedlibrary.model.motor;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.enumeration.MotorInsuranceType;
import org.example.sharedlibrary.model.BaseInsuranceTypeModel;

import java.util.Map;

@Getter
@Setter
public class MotorInsuranceTypeModel extends BaseInsuranceTypeModel {
    Map<MotorInsuranceType, MotorFeeModel> compulsory;
    Map<MotorInsuranceType, MotorFeeModel> otherCompulsory;

    public MotorInsuranceTypeModel(Map<MotorInsuranceType,
            MotorFeeModel> compulsory, Map<MotorInsuranceType, MotorFeeModel> otherCompulsory) {
        this.compulsory = compulsory;
        this.otherCompulsory = otherCompulsory;
    }
}
