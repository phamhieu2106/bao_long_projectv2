package org.example.policydomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.policy.PolicyUpdateInternalAMCommand;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.policy.PolicyUpdateInternalAMEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.example.sharedlibrary.base_quo_poli.UserCreatedModel;
import org.example.sharedlibrary.enumeration.ProductType;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.QuotationTypeStatus;
import org.example.sharedlibrary.enumeration.ac.Role;
import org.example.sharedlibrary.model.UserModel;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyAggregate extends BaseAggregate {
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
    List<UserModel> userModels;
    String approvedBy;
    Date approvedAt;
    Role lastUserRoleUpdate;

    public PolicyCreateEvent apply(PolicyCreateCommand command) {
        this.id = command.getId();
        this.quotationCode = command.getQuotationCode();
        this.policyCode = command.getPolicyCode();
        this.quotationStatus = command.getQuotationStatus();
        this.userModels = command.getUserModels();
        this.approvedBy = command.getApprovedBy();
        this.approvedAt = command.getApprovedAt();
        this.effectiveDate = command.getEffectiveDate();
        this.maturityDate = command.getMaturityDate();
        this.customerModel = command.getCustomerModel();
        this.beneficiaryModel = command.getBeneficiaryModel();
        this.productType = command.getProductType();
        this.productCode = command.getProductCode();
        this.product = command.getProduct();
        this.isCoinsurance = command.getIsCoinsurance();
        this.quotationDistributionName = command.getQuotationDistributionName();
        this.quotationManagerName = command.getQuotationManagerName();
        this.insuranceCompanyName = command.getInsuranceCompanyName();
        this.currency = command.getCurrency();
        this.rate = command.getRate();
        this.userCreatedModel = command.getUserCreatedModel();
        this.insuranceTypeModel = command.getInsuranceTypeModel();
        this.totalFeeAfterTax = command.getTotalFeeAfterTax();
        this.productName = command.getProductName();
        this.quotationTypeStatus = command.getQuotationTypeStatus();
        this.lastUserRoleUpdate = command.getLastUserRoleUpdate();

        return new PolicyCreateEvent(
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
                this.quotationTypeStatus,
                this.lastUserRoleUpdate,
                this.userModels,
                this.approvedBy,
                this.approvedAt
        );
    }

    public PolicyUpdateInternalAMEvent apply(PolicyUpdateInternalAMCommand command) {
        if (command.getProduct() != null) {
            this.product = command.getProduct();
        }
        if (command.getInsuranceCompanyName() != null) {
            this.insuranceCompanyName = command.getInsuranceCompanyName();
        }
        if (command.getQuotationDistributionName() != null) {
            this.quotationDistributionName = command.getQuotationDistributionName();
        }

        return new PolicyUpdateInternalAMEvent(
                new Date(),
                command.getCreatedBy(),
                this.id,
                this.product,
                this.insuranceCompanyName,
                this.quotationDistributionName
        );
    }
}
