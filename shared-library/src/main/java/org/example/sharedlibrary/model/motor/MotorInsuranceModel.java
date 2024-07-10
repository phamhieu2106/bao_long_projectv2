package org.example.sharedlibrary.model.motor;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.model.BaseInsuranceModel;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotorInsuranceModel extends BaseInsuranceModel {
    Date validInsuranceDate;
    Date voidInsuranceDate;
    //dinh danh
    MotorIdentityModel motorIdentityModel;
    // loai hinh bao hiem
    MotorInsuranceTypeModel motorInsuranceType;
}
