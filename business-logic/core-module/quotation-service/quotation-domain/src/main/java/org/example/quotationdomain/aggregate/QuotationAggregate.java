package org.example.quotationdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class QuotationAggregate extends BaseAggregate {

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

    public QuotationCreateEvent apply(QuotationCreateCommand command) {

        this.id = GenerateConstant.generateId();
        this.productType = command.getProductType();
        this.productCode = command.getProductCode();
        this.product = command.getProduct();
        this.isCoinsurance = command.getIsCoinsurance();
        this.quotationDistributionName = command.getQuotationDistributionName();
        this.quotationManagerName = command.getQuotationManagerName();
        this.insuranceCompanyName = command.getInsuranceCompanyName();
        this.currency = command.getCurrency();
        this.rate = command.getRate();
        this.userCreatedModel.setUsername(command.getCreatedBy());
        this.insuranceTypeModel = command.getInsuranceTypeModel();
        this.totalFeeAfterTax = command.getTotalFeeAfterTax();
        this.productName = command.getProductName();
        this.quotationTypeStatus = QuotationTypeStatus.NEW;

        return new QuotationCreateEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.quotationCode,
                this.policyCode,
                this.productType,
                this.productName,
                this.productCode,
                this.product,
                this.isCoinsurance,
                this.quotationStatus,
                this.quotationDistributionName,
                this.quotationManagerName,
                this.insuranceCompanyName,
                this.effectiveDate,
                this.maturityDate,
                this.customerModel,
                this.beneficiaryModel,
                this.currency,
                this.rate,
                this.insuranceTypeModel,
                this.totalFeeAfterTax,
                this.userCreatedModel,
                this.quotationTypeStatus
        );
    }

//    public QuotationUpdateEvent apply(QuotationUpdateCommand command) {
//
//        this.productType = command.getProductType();
//        this.productCode = command.getProductCode();
//        this.product = command.getProduct();
//        this.isCoinsurance = command.getIsCoinsurance();
//        this.quotationDistributionName = command.getQuotationDistributionName();
//        this.insuranceCompanyName = command.getInsuranceCompanyName();
//        this.currency = command.getCurrency();
//        this.rate = command.getRate();
//        this.createdBy = command.getCreatedBy();
//        this.insuranceTypeModel = command.getInsuranceTypeModel();
//        this.totalFeeAfterTax = command.getTotalFeeAfterTax();
//
//        return new QuotationUpdateEvent(
//                new Date(),
//                this.createdBy,
//                this.quotationId,
//                this.quotationCode,
//                this.policyCode,
//                this.productType,
//                this.productCode,
//                this.product,
//                this.isCoinsurance,
//                this.quotationStatus,
//                this.quotationDistributionName,
//                this.quotationManagerName,
//                this.insuranceCompanyName,
//                this.effectiveDate,
//                this.maturityDate,
//                this.customerId,
//                this.beneficiaryId,
//                this.currency,
//                this.rate,
//                this.insuranceTypeModel,
//                this.totalFeeAfterTax
//        );
//    }

}
