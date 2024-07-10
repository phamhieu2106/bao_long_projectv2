package org.example.sharedlibrary.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {
    String addressType;
    String houseNumber;
    String streetName;
    String wardName;
    String districtName;
    String city;
    String national;
}
