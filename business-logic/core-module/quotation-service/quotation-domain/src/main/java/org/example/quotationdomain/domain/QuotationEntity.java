package org.example.quotationdomain.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_quo_poli.BaseQuoPoli;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(unique = true)
    String quotationCode;
    String policyCode;

    //product
    @Enumerated(EnumType.STRING)
    ProductType productType;
    String productName;
    String productCode;
    @Column(length = 10000)
    @Convert(converter = ProductMapConverter.class)
    List<Map<String, Object>> product;

    //
    Boolean isCoinsurance;
    @Enumerated(EnumType.STRING)
    QuotationStatus quotationStatus;
    @Enumerated(EnumType.STRING)
    QuotationTypeStatus quotationTypeStatus;

    //information
    String quotationDistributionName;
    String insuranceCompanyName;

    //date
    Date effectiveDate;
    Date maturityDate;

    //id
    String quotationManagerName;
    String customerId;
    String beneficiaryId;

    //money
    String currency;
    Double rate;

    //payment fee
    @Convert(converter = ProductMapConverter.class)
    @Column(length = 10000)
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;

    String createdBy;
    Date createdAt;

    public QuotationEntity(String quotationDistributionName, String quotationManagerName, String insuranceCompanyName,
                           Date effectiveDate, Date maturityDate, CustomerModel customer, CustomerModel beneficiary, String quotationCode,
                           String policyCode, List<Map<String, Object>> product, Boolean isCoinsurance
            , QuotationStatus quotationStatus, String customerId, String beneficiaryId, String currency, Double rate, Date createdAt) {
        super(quotationDistributionName, quotationManagerName, insuranceCompanyName, effectiveDate, maturityDate, customer, beneficiary);
        this.quotationCode = quotationCode;
        this.policyCode = policyCode;
        this.product = product;
        this.isCoinsurance = isCoinsurance;
        this.quotationStatus = quotationStatus;
        this.customerId = customerId;
        this.beneficiaryId = beneficiaryId;
        this.currency = currency;
        this.rate = rate;
        this.createdAt = createdAt;
    }
}
