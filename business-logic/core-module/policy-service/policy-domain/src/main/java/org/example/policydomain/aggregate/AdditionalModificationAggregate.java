package org.example.policydomain.aggregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToApprovedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToAwaitApproveCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRejectedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRequireInformationCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToUndoneCommand;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToApprovedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToAwaitApproveEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRejectedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRequireInformationEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToUndoneEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalModificationAggregate extends BaseAggregate {
    String additionalModificationId;
    String additionalModificationCode;
    ModificationType modificationType;
    ModificationTypeName modificationTypeName;
    AdditionalModificationStatus additionalModificationStatus;
    Date effectiveDate;
    List<Map<String, Object>> additionalData;
    String policyId;
    Date createdAt;
    String createdBy;
    String approvedBy;
    Date approvedAt;
    String modifiedBy;
    Date modifiedAt;

    public AdditionalModificationCreateEvent apply(AdditionalModificationCreateCommand command) {
        this.additionalModificationId = GenerateConstant.generateId();
        this.modificationType = command.getModificationType();
        this.modificationTypeName = command.getModificationTypeName();
        this.effectiveDate = command.getEffectiveDate();
        this.additionalData = command.getAdditionalData();
        this.policyId = command.getPolicyId();
        this.createdBy = command.getCreatedBy();
        this.createdAt = new Date();
        this.additionalModificationStatus = AdditionalModificationStatus.DRAFTING;

        return new AdditionalModificationCreateEvent(
                this.additionalModificationId,
                this.additionalModificationCode,
                this.policyId,
                this.modificationType,
                this.modificationTypeName,
                this.additionalData,
                this.effectiveDate,
                this.createdBy,
                this.createdAt,
                null,
                null
        );
    }

    public AdditionalModificationToAwaitApproveEvent apply(AdditionalModificationToAwaitApproveCommand command) {
        this.modifiedBy = command.getCreatedBy();
        this.modifiedAt = new Date();
        this.additionalModificationStatus = command.getAdditionalModificationStatus();

        return new AdditionalModificationToAwaitApproveEvent(
                this.modifiedAt,
                this.modifiedBy,
                this.additionalModificationId
        );
    }

    public AdditionalModificationToRejectedEvent apply(AdditionalModificationToRejectedCommand additionalModificationToRejectedCommand) {
        this.modifiedBy = additionalModificationToRejectedCommand.getCreatedBy();
        this.modifiedAt = new Date();
        this.additionalModificationStatus = additionalModificationToRejectedCommand.getAdditionalModificationStatus();

        return new AdditionalModificationToRejectedEvent(
                this.modifiedAt,
                this.modifiedBy,
                this.additionalModificationId
        );
    }

    public AdditionalModificationToRequireInformationEvent apply(AdditionalModificationToRequireInformationCommand additionalModificationToRequireInformationCommand) {
        this.modifiedBy = additionalModificationToRequireInformationCommand.getCreatedBy();
        this.modifiedAt = new Date();
        this.additionalModificationStatus = additionalModificationToRequireInformationCommand.getAdditionalModificationStatus();

        return new AdditionalModificationToRequireInformationEvent(
                this.modifiedAt,
                this.modifiedBy,
                this.additionalModificationId
        );
    }

    public AdditionalModificationToUndoneEvent apply(AdditionalModificationToUndoneCommand additionalModificationToUndoneCommand) {
        this.modifiedBy = additionalModificationToUndoneCommand.getCreatedBy();
        this.modifiedAt = new Date();
        this.additionalModificationStatus = additionalModificationToUndoneCommand.getAdditionalModificationStatus();

        return new AdditionalModificationToUndoneEvent(
                this.modifiedAt,
                this.modifiedBy,
                this.additionalModificationId
        );
    }

    public AdditionalModificationToApprovedEvent apply(AdditionalModificationToApprovedCommand additionalModificationToApprovedCommand) {
        this.approvedBy = additionalModificationToApprovedCommand.getApprovedBy();
        this.createdBy = additionalModificationToApprovedCommand.getApprovedBy();
        this.approvedAt = new Date();
        this.modifiedBy = additionalModificationToApprovedCommand.getApprovedBy();
        this.modifiedAt = this.approvedAt;
        this.additionalModificationStatus = additionalModificationToApprovedCommand.getAdditionalModificationStatus();

        return new AdditionalModificationToApprovedEvent(
                this.approvedAt,
                this.approvedBy,
                this.additionalModificationId,
                this.additionalModificationCode,
                this.approvedBy
        );
    }
}
