package org.example.policydomain.aggregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.example.sharedlibrary.base_class.BaseAggregate;
import org.example.sharedlibrary.base_constant.GenerateConstant;
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
    Date effectiveDate;
    List<Map<String, Object>> additionalData;
    String policyId;
    Date createdAt;
    String createdBy;
    String approvedBy;

    public AdditionalModificationCreateEvent apply(AdditionalModificationCreateCommand command) {
        this.additionalModificationId = GenerateConstant.generateId();
        this.modificationType = command.getModificationType();
        this.modificationTypeName = command.getModificationTypeName();
        this.effectiveDate = command.getEffectiveDate();
        this.additionalData = command.getAdditionalData();
        this.policyId = command.getPolicyId();
        this.createdAt = new Date();

        return new AdditionalModificationCreateEvent(
                this.additionalModificationId,
                this.additionalModificationCode,
                this.policyId,
                this.modificationType,
                this.modificationTypeName,
                this.additionalData,
                this.effectiveDate,
                this.createdBy,
                this.createdAt
        );
    }
}
