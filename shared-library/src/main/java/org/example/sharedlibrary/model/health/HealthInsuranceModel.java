package org.example.sharedlibrary.model.health;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.BaseInsuranceModel;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HealthInsuranceModel extends BaseInsuranceModel {
    String name;
    Date dateOfBirth;
    String age;
    Gender gender;
    String phoneNumber;
    String email;
    Date validInsuranceDate;
    Date voidInsuranceDate;
    HealthIdentityModel healthIdentityModel;
    AddressModel addressModel;
}
