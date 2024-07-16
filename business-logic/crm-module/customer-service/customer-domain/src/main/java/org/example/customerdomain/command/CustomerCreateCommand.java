package org.example.customerdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.UserInChargeModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCreateCommand extends BaseCommand {
    String customerName;
    Gender gender;
    CustomerType customerType;
    String phoneNumber;
    String email;
    Date dateOfBirth;
    List<AddressModel> addressModels;
    String jobName;
    HealthIdentityModel identityModel;
    List<UserInChargeModel> inChargeBy;
    String createdBy;
}
