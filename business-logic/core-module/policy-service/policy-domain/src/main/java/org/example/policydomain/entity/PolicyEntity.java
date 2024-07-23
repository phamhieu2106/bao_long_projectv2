package org.example.policydomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_constant.ColumnConstant;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.example.sharedlibrary.enumeration.ac.Role;
import org.example.sharedlibrary.model.UserModel;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    List<Map<String, Object>> product;
    Boolean isCoinsurance;
    @Enumerated(EnumType.STRING)
    QuotationStatus quotationStatus;
    @Enumerated(EnumType.STRING)
    QuotationTypeStatus quotationTypeStatus;
    String currency;
    Double rate;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
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
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    CustomerModel customer;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    CustomerModel beneficiary;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    UserCreatedModel userCreatedModel;

    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
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
