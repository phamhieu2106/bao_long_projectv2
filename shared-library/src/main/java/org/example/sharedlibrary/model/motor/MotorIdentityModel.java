package org.example.sharedlibrary.model.motor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MotorIdentityModel {
    String motorBrand;
    String motorModel;
    String motorColor;
    Integer seatQuality;
    Integer productionYear;
    String identityNumber;
    String frameNumber;
}
