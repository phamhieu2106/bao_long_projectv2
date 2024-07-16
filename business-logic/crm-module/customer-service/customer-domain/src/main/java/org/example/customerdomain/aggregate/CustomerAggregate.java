package org.example.customerdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.customerdomain.enumeration.StatusCustomer;
import org.example.customerdomain.event.CustomerCreateEvent;
import org.example.customerdomain.event.CustomerDeleteEvent;
import org.example.customerdomain.event.CustomerUpdateEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.UserInChargeModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@NoArgsConstructor
public class CustomerAggregate extends BaseAggregate {


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
    List<UserInChargeModel> inChargeBy;
    Boolean isDeleted = false;
    Date createdAt;
    String createdBy;

    public CustomerCreateEvent apply(CustomerCreateCommand command) {
        this.id = GenerateConstant.generateId();
        this.customerName = command.getCustomerName();
        this.gender = command.getGender();
        this.phoneNumber = command.getPhoneNumber();
        this.email = command.getEmail();
        this.dateOfBirth = command.getDateOfBirth();
        this.addressModels = command.getAddressModels();
        this.jobName = command.getJobName();
        this.identityModel = command.getIdentityModel();
        this.statusCustomer = StatusCustomer.POTENTIAL;
        this.customerType = command.getCustomerType();
        this.createdAt = new Date();
        this.createdBy = command.getCreatedBy();
        return new CustomerCreateEvent(
                this.createdAt,
                this.createdBy,
                this.id,
                this.customerCode,
                this.customerName,
                this.gender,
                this.phoneNumber,
                this.dateOfBirth,
                this.email,
                this.addressModels,
                this.jobName,
                this.identityModel,
                this.statusCustomer,
                this.customerType,
                this.isDeleted,
                this.inChargeBy
        );
    }

    public CustomerUpdateEvent apply(CustomerUpdateCommand command) {
        this.customerName = command.getCustomerName();
        this.gender = command.getGender();
        this.phoneNumber = command.getPhoneNumber();
        this.email = command.getEmail();
        this.dateOfBirth = command.getDateOfBirth();
        this.addressModels = command.getAddressModels();
        this.jobName = command.getJobName();
        this.identityModel = command.getIdentityModel();
        this.customerType = command.getCustomerType();
        this.inChargeBy = command.getInChargeBy();

        return new CustomerUpdateEvent(
                this.createdAt,
                this.createdBy,
                this.id,
                this.customerCode,
                this.customerName,
                this.gender,
                this.phoneNumber,
                this.email,
                this.dateOfBirth,
                this.addressModels,
                this.jobName,
                this.identityModel,
                this.statusCustomer,
                this.customerType,
                this.isDeleted,
                this.inChargeBy
        );
    }

    public CustomerDeleteEvent apply(CustomerDeleteCommand command) {
        this.id = command.getCustomerId();
        this.isDeleted = true;
        return new CustomerDeleteEvent(
                new Date(),
                command.getCreatedBy(),
                this.id
        );
    }


}
