package org.example.quotationdomain.event.crud;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.example.sharedlibrary.model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationCreateNewVersionEvent extends BaseEvent {
    String id;
    String quotationCode;
    String policyCode;
    ProductType productType;
    String productName;
    String productCode;
    List<Map<String, Object>> product;
    Boolean isCoinsurance;
    QuotationStatus quotationStatus;
    QuotationTypeStatus quotationTypeStatus;
    String quotationDistributionName;
    String quotationManagerName;
    String insuranceCompanyName;
    Date effectiveDate;
    Date maturityDate;
    CustomerModel customerModel;
    CustomerModel beneficiaryModel;
    String currency;
    Double rate;
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;
    UserCreatedModel userCreatedModel;
    String approvedBy;
    Date approveAt;
    List<UserModel> userModels;
    int quotationVersion = 1;

    public QuotationCreateNewVersionEvent(Date timestamp, String createdBy, String quotationId, String quotationCode,
                                          String policyCode, ProductType productType, String productName, String productCode, List<Map<String, Object>> product,
                                          Boolean isCoinsurance, QuotationStatus quotationStatus,
                                          String quotationDistributionName, String quotationManagerName,
                                          String insuranceCompanyName, Date effectiveDate, Date maturityDate,
                                          CustomerModel customerId, CustomerModel beneficiaryId, String currency, Double rate, List<Map<String, Object>> insuranceTypeModel,
                                          Double totalFeeAfterTax, UserCreatedModel userCreatedModel, QuotationTypeStatus quotationTypeStatus,
                                          String approvedBy, Date approveAt, int quotationVersion, List<UserModel> userModels) {
        super(timestamp, createdBy);
        this.id = quotationId;
        this.quotationCode = quotationCode;
        this.policyCode = policyCode;
        this.productType = productType;
        this.productName = productName;
        this.productCode = productCode;
        this.product = product;
        this.isCoinsurance = isCoinsurance;
        this.quotationStatus = quotationStatus;
        this.quotationDistributionName = quotationDistributionName;
        this.quotationManagerName = quotationManagerName;
        this.insuranceCompanyName = insuranceCompanyName;
        this.effectiveDate = effectiveDate;
        this.maturityDate = maturityDate;
        this.customerModel = customerId;
        this.beneficiaryModel = beneficiaryId;
        this.currency = currency;
        this.rate = rate;
        this.insuranceTypeModel = insuranceTypeModel;
        this.totalFeeAfterTax = totalFeeAfterTax;
        this.userCreatedModel = userCreatedModel;
        this.quotationTypeStatus = quotationTypeStatus;
        this.approvedBy = approvedBy;
        this.approveAt = approveAt;
        this.quotationVersion = quotationVersion;
        this.userModels = userModels;
    }
}
