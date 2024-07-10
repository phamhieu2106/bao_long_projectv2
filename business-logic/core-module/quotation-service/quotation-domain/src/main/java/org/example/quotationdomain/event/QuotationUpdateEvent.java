package org.example.quotationdomain.event;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationUpdateEvent extends BaseEvent {
    String quotationId;
    String quotationCode;
    String policyCode;
    //product
    ProductType productType;
    String productCode;
    List<Map<String, Object>> product;
    //
    Boolean isCoinsurance;
    QuotationStatus quotationStatus;
    //information
    String quotationDistributionName;
    String quotationManagerName;
    String insuranceCompanyName;
    //date
    Date effectiveDate;
    Date maturityDate;
    //id
    String customerId;
    String beneficiaryId;
    //money
    String currency;
    Double rate;
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;
    String createdBy;

    public QuotationUpdateEvent(Date timestamp, String createdBy, String quotationId, String quotationCode,
                                String policyCode, ProductType productType, String productCode, List<Map<String, Object>> product,
                                Boolean isCoinsurance, QuotationStatus quotationStatus,
                                String quotationDistributionName, String quotationManagerName,
                                String insuranceCompanyName, Date effectiveDate, Date maturityDate,
                                String customerId, String beneficiaryId, String currency, Double rate, List<Map<String, Object>> insuranceTypeModel,
                                Double totalFeeAfterTax) {
        super(timestamp, createdBy);
        this.quotationId = quotationId;
        this.quotationCode = quotationCode;
        this.policyCode = policyCode;
        this.productType = productType;
        this.productCode = productCode;
        this.product = product;
        this.isCoinsurance = isCoinsurance;
        this.quotationStatus = quotationStatus;
        this.quotationDistributionName = quotationDistributionName;
        this.quotationManagerName = quotationManagerName;
        this.insuranceCompanyName = insuranceCompanyName;
        this.effectiveDate = effectiveDate;
        this.maturityDate = maturityDate;
        this.customerId = customerId;
        this.beneficiaryId = beneficiaryId;
        this.currency = currency;
        this.rate = rate;
        this.insuranceTypeModel = insuranceTypeModel;
        this.totalFeeAfterTax = totalFeeAfterTax;
    }
}
