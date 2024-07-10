package org.example.sharedlibrary.utils;

import org.example.sharedlibrary.base_constant.RegexConstant;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;

public class CustomerValidate {
    
    public static boolean isValidProof(HealthIdentityModel identityType) {
        switch (identityType.getIdentityType()) {
            case IDENTITY_CARD -> {
                return !RegexConstant.REGEX_IDENTITY_CARD.matcher(identityType.getIdentityNumber()).matches();
            }
            case CITIZEN_IDENTITY_CARD -> {
                return !RegexConstant.REGEX_CITIZEN_IDENTITY_CARD.matcher(identityType.getIdentityNumber()).matches();
            }
            case PASSPORT -> {
                return !RegexConstant.REGEX_PASSPORT.matcher(identityType.getIdentityNumber()).matches();
            }
        }
        return true;
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !RegexConstant.REGEX_PHONE_NUMBER.matcher(phoneNumber).matches();
    }

    public static boolean isValidEmail(String email) {
        return !RegexConstant.REGEX_EMAIL.matcher(email).matches();
    }

    public static boolean isValidAddress(AddressModel addressModel) {
        if (addressModel == null || addressModel.getNational() == null
                || addressModel.getNational().isBlank() || addressModel.getNational().isEmpty()) {
            return true;
        }
        String VIETNAM_CODE = "VN";
        if (VIETNAM_CODE.equals(addressModel.getNational())) {
            if (addressModel.getHouseNumber() == null
                    || addressModel.getHouseNumber().isBlank() || addressModel.getHouseNumber().isEmpty()) {
                return true;
            }
            if (addressModel.getStreetName() == null
                    || addressModel.getStreetName().isBlank() || addressModel.getStreetName().isEmpty()) {
                return true;
            }
            if (addressModel.getWardName() == null
                    || addressModel.getWardName().isBlank() || addressModel.getWardName().isEmpty()) {
                return true;
            }
            if (addressModel.getDistrictName() == null
                    || addressModel.getDistrictName().isBlank() || addressModel.getDistrictName().isEmpty()) {
                return true;
            }
            return addressModel.getCity() == null
                    || addressModel.getCity().isBlank() || addressModel.getCity().isEmpty();
        }
        return false;
    }


}
