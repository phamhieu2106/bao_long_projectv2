package org.example.quotationdomain.aggregate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.quotationdomain.command.cud.QuotationCopyCommand;
import org.example.quotationdomain.command.cud.QuotationCreateCommand;
import org.example.quotationdomain.command.cud.QuotationCreateNewVersionCommand;
import org.example.quotationdomain.command.cud.QuotationUpdateCommand;
import org.example.quotationdomain.command.status.*;
import org.example.quotationdomain.event.crud.QuotationCreateEvent;
import org.example.quotationdomain.event.crud.QuotationCreateNewVersionEvent;
import org.example.quotationdomain.event.crud.QuotationUpdateEvent;
import org.example.quotationdomain.event.status.*;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
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
    List<UserModel> userModels;
    String approvedBy;
    Date approvedAt;
    Role lastUserRoleUpdate;
    int quotationVersion;

    public QuotationCreateEvent apply(QuotationCreateCommand command) {

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
        this.quotationVersion = 1;

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

    public QuotationCreateEvent apply(QuotationCopyCommand command) {

        this.id = GenerateConstant.generateId();

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

    public QuotationUpdateEvent apply(QuotationUpdateCommand command) {

        this.product = command.getProduct();
        this.insuranceTypeModel = command.getInsuranceTypeModel();
        this.totalFeeAfterTax = command.getTotalFeeAfterTax();
        this.isCoinsurance = command.getIsCoinsurance();
        this.quotationDistributionName = command.getQuotationDistributionName();
        this.insuranceCompanyName = command.getInsuranceCompanyName();

        return new QuotationUpdateEvent(
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
                this.approvedBy,
                this.approvedAt,
                this.lastUserRoleUpdate,
                this.userModels
        );
    }

    public QuotationUpdateEvent apply() {

        return new QuotationUpdateEvent(
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
                this.approvedBy,
                this.approvedAt,
                this.lastUserRoleUpdate,
                this.userModels
        );
    }

    public QuotationChangeToAwaitApproveStatusEvent apply(QuotationChangeToAwaitApproveStatusCommand quotationChangeToAwaitApproveStatusCommand) {
        this.quotationStatus = quotationChangeToAwaitApproveStatusCommand.getQuotationStatus();

        return new QuotationChangeToAwaitApproveStatusEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.userModels,
                this.lastUserRoleUpdate
        );
    }

    public QuotationChangeToApprovedStatusEvent apply(QuotationChangeToApprovedStatusCommand quotationChangeToApprovedStatusCommand) {
        this.quotationStatus = quotationChangeToApprovedStatusCommand.getQuotationStatus();

        return new QuotationChangeToApprovedStatusEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.approvedBy,
                this.approvedAt,
                this.lastUserRoleUpdate
        );
    }

    public QuotationChangeToDisabledStatusEvent apply(QuotationChangeToDisabledStatusCommand quotationChangeToDisabledStatusCommand) {
        this.quotationStatus = quotationChangeToDisabledStatusCommand.getQuotationStatus();

        return new QuotationChangeToDisabledStatusEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.lastUserRoleUpdate
        );
    }

    public QuotationChangeToRejectedStatusEvent apply(QuotationChangeToRejectedStatusCommand quotationChangeToRejectedStatusCommand) {
        this.quotationStatus = quotationChangeToRejectedStatusCommand.getQuotationStatus();

        return new QuotationChangeToRejectedStatusEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.lastUserRoleUpdate
        );
    }

    public QuotationChangeToRequireInformationStatusEvent apply(QuotationChangeToRequireInformationStatusCommand quotationChangeToRequireInformationStatusCommand) {
        this.quotationStatus = quotationChangeToRequireInformationStatusCommand.getQuotationStatus();

        return new QuotationChangeToRequireInformationStatusEvent(
                new Date(),
                this.userCreatedModel.getUsername(),
                this.id,
                this.lastUserRoleUpdate
        );
    }

    public QuotationCreateNewVersionEvent apply(QuotationCreateNewVersionCommand command) {
        this.id = GenerateConstant.generateId();

        return new QuotationCreateNewVersionEvent(
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
                this.approvedBy,
                this.approvedAt,
                this.quotationVersion,
                this.userModels
        );
    }
}
