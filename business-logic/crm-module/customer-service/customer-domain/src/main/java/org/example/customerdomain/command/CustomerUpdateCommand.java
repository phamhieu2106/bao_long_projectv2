package org.example.customerdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateCommand extends BaseCommand {
    String customerId;
    String customerName;
    Gender gender;
    CustomerType customerType;
    String phoneNumber;
    String email;
    Date dateOfBirth;
    List<AddressModel> addressModels;
    String jobName;
    HealthIdentityModel identityModel;
    String inChargeBy;
    String createdBy;
}
