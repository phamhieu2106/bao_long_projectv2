package org.example.policydomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.converter.CustomerModelConverter;
import org.example.policydomain.converter.UserCreatedModelConverter;
import org.example.policydomain.converter.UserModelConverter;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.converter.ProductMapConverter;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.example.sharedlibrary.enumeration.ac.Role;
import org.example.sharedlibrary.model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Table
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PolicyEntity {
    @Id
    String id;
    @Column(unique = true)
    String quotationCode;
    String policyCode;
    @Enumerated(EnumType.STRING)
    ProductType productType;
    String productName;
    String productCode;
    @Column(length = 10000)
    @Convert(converter = ProductMapConverter.class)
    List<Map<String, Object>> product;
    Boolean isCoinsurance;
    @Enumerated(EnumType.STRING)
    QuotationStatus quotationStatus;
    @Enumerated(EnumType.STRING)
    QuotationTypeStatus quotationTypeStatus;
    String currency;
    Double rate;
    @Convert(converter = ProductMapConverter.class)
    @Column(length = 10000)
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;
    Date createdAt;

    String approveBy;
    Date approvedAt;

    String quotationDistributionName;
    String quotationManagerName;
    String insuranceCompanyName;
    Date effectiveDate;
    Date maturityDate;
    @Convert(converter = CustomerModelConverter.class)
    CustomerModel customer;
    @Convert(converter = CustomerModelConverter.class)
    CustomerModel beneficiary;
    @Convert(converter = UserCreatedModelConverter.class)
    UserCreatedModel userCreatedModel;

    @Convert(converter = UserModelConverter.class)
    @Column(length = 1000)
    List<UserModel> userModels;

    Role lastUserRoleUpdate;

    public PolicyEntity(String id, String productName, ProductType productType, String productCode, String quotationDistributionName, String quotationManagerName, String insuranceCompanyName,
                        Date effectiveDate, Date maturityDate, CustomerModel customer, CustomerModel beneficiary, String quotationCode,
                        String policyCode, List<Map<String, Object>> product, Boolean isCoinsurance
            , QuotationStatus quotationStatus, QuotationTypeStatus quotationTypeStatus, String currency, Double rate, Date createdAt, UserCreatedModel userCreatedModel,
                        String approveBy, Date approvedAt, Double totalFeeAfterTax, List<Map<String, Object>> insuranceTypeModel) {
        this.id = id;
        this.quotationDistributionName = quotationDistributionName;
        this.quotationManagerName = quotationManagerName;
        this.insuranceCompanyName = insuranceCompanyName;
        this.effectiveDate = effectiveDate;
        this.maturityDate = maturityDate;
        this.customer = customer;
        this.beneficiary = beneficiary;
        this.userCreatedModel = userCreatedModel;
        this.quotationCode = quotationCode;
        this.policyCode = policyCode;
        this.product = product;
        this.productName = productName;
        this.productCode = productCode;
        this.productType = productType;
        this.isCoinsurance = isCoinsurance;
        this.quotationStatus = quotationStatus;
        this.quotationTypeStatus = quotationTypeStatus;
        this.currency = currency;
        this.rate = rate;
        this.createdAt = createdAt;
        this.approveBy = approveBy;
        this.approvedAt = approvedAt;
        this.totalFeeAfterTax = totalFeeAfterTax;
        this.insuranceTypeModel = insuranceTypeModel;
    }
}
