package org.example.quotationdomain.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_quo_poli.BaseQuoPoli;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.converter.ProductMapConverter;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Table
@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuotationEntity extends BaseQuoPoli {

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

    public QuotationEntity(String id, String productName, ProductType productType, String productCode, String quotationDistributionName, String quotationManagerName, String insuranceCompanyName,
                           Date effectiveDate, Date maturityDate, CustomerModel customer, CustomerModel beneficiary, String quotationCode,
                           String policyCode, List<Map<String, Object>> product, Boolean isCoinsurance
            , QuotationStatus quotationStatus, QuotationTypeStatus quotationTypeStatus, String currency, Double rate, Date createdAt, UserCreatedModel userCreatedModel,
                           String approveBy, Date approvedAt) {
        super(quotationDistributionName, quotationManagerName, insuranceCompanyName, effectiveDate, maturityDate, customer, beneficiary, userCreatedModel);
        this.id = id;
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
        this.userCreatedModel = userCreatedModel;
        this.approveBy = approveBy;
        this.approvedAt = approvedAt;
    }
}
