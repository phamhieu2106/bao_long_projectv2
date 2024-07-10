package org.example.quotationdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationAggregate extends BaseAggregate {

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

    //payment fee
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;

    String createdBy;
    Date createdAt;

    public QuotationCreateEvent apply(QuotationCreateCommand command) {

        this.quotationId = GenerateConstant.generateId();
        this.productType = command.getProductType();
        this.productCode = command.getProductCode();
        this.product = command.getProduct();
        this.customerId = command.getCustomerId();
        this.beneficiaryId = command.getBeneficiaryId();
        this.isCoinsurance = command.getIsCoinsurance();
        this.quotationDistributionName = command.getQuotationDistributionName();
        this.quotationManagerName = command.getQuotationManagerName();
        this.insuranceCompanyName = command.getInsuranceCompanyName();
        this.currency = command.getCurrency();
        this.rate = command.getRate();
        this.createdBy = command.getCreatedBy();
        this.insuranceTypeModel = command.getInsuranceTypeModel();
        this.totalFeeAfterTax = command.getTotalFeeAfterTax();

        return new QuotationCreateEvent(
                new Date(),
                this.createdBy,
                this.quotationId,
                this.quotationCode,
                this.policyCode,
                this.productType,
                this.productCode,
                this.product,
                this.isCoinsurance,
                this.quotationStatus,
                this.quotationDistributionName,
                this.quotationManagerName,
                this.insuranceCompanyName,
                this.effectiveDate,
                this.maturityDate,
                this.customerId,
                this.beneficiaryId,
                this.currency,
                this.rate,
                this.insuranceTypeModel,
                this.totalFeeAfterTax
        );
    }

    public QuotationUpdateEvent apply(QuotationUpdateCommand command) {

        this.productType = command.getProductType();
        this.productCode = command.getProductCode();
        this.product = command.getProduct();
        this.isCoinsurance = command.getIsCoinsurance();
        this.quotationDistributionName = command.getQuotationDistributionName();
        this.insuranceCompanyName = command.getInsuranceCompanyName();
        this.currency = command.getCurrency();
        this.rate = command.getRate();
        this.createdBy = command.getCreatedBy();
        this.insuranceTypeModel = command.getInsuranceTypeModel();
        this.totalFeeAfterTax = command.getTotalFeeAfterTax();

        return new QuotationUpdateEvent(
                new Date(),
                this.createdBy,
                this.quotationId,
                this.quotationCode,
                this.policyCode,
                this.productType,
                this.productCode,
                this.product,
                this.isCoinsurance,
                this.quotationStatus,
                this.quotationDistributionName,
                this.quotationManagerName,
                this.insuranceCompanyName,
                this.effectiveDate,
                this.maturityDate,
                this.customerId,
                this.beneficiaryId,
                this.currency,
                this.rate,
                this.insuranceTypeModel,
                this.totalFeeAfterTax
        );
    }

}
