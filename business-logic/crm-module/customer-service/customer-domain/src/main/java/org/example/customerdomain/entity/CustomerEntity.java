package org.example.customerdomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.converter.UserInChargeModelConverter;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.customerdomain.enumeration.StatusCustomer;
import org.example.sharedlibrary.converter.AddressModelConverter;
import org.example.sharedlibrary.converter.HealthIdentityModelConverter;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.UserInChargeModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    @Id
    String id;
    String customerCode;
    String customerName;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNumber;
    String email;
    Date dateOfBirth;
    @Convert(converter = AddressModelConverter.class)
    @Column(length = 10000)
    List<AddressModel> addressModels;
    String jobName;
    @Convert(converter = HealthIdentityModelConverter.class)
    HealthIdentityModel identityModel;
    @Enumerated(EnumType.STRING)
    StatusCustomer statusCustomer;
    @Enumerated(EnumType.STRING)
    CustomerType customerType;
    @Convert(converter = UserInChargeModelConverter.class)
    List<UserInChargeModel> inChargeBy;
    Boolean isDeleted = false;
    Date createdAt;
    String createdBy;
}
