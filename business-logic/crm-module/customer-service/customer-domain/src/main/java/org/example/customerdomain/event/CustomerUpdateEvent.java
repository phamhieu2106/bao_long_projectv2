package org.example.customerdomain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.customerdomain.enumeration.StatusCustomer;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUpdateEvent extends BaseEvent {
    String id;
    String customerCode;
    String customerName;
    Gender gender;
    String phoneNumber;
    String email;
    Date dateOfBirth;
    List<AddressModel> addressModels;
    String jobName;
    HealthIdentityModel identityModel;
    StatusCustomer statusCustomer;
    CustomerType customerType;
    Boolean isDeleted;
    Date createdAt;
    String inChargeBy;

    public CustomerUpdateEvent(Date timestamp, String createdBy, String id, String customerCode, String customerName, Gender gender,
                               String phoneNumber, String email, Date dateOfBirth, List<AddressModel> addressModels,
                               String jobName, HealthIdentityModel identityModel, StatusCustomer statusCustomer,
                               CustomerType customerType, Boolean isDeleted, String inChargeBy) {
        super(timestamp, createdBy);
        this.id = id;
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.addressModels = addressModels;
        this.jobName = jobName;
        this.identityModel = identityModel;
        this.statusCustomer = statusCustomer;
        this.customerType = customerType;
        this.isDeleted = isDeleted;
        this.inChargeBy = inChargeBy;
    }
}
