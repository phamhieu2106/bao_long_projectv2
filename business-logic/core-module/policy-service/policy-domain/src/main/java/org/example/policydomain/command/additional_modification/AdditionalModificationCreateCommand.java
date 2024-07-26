package org.example.policydomain.command.additional_modification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTerminalTypeName;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalModificationCreateCommand extends BaseCommand {
    String policyId;
    ModificationType modificationType;
    ModificationTypeName modificationTypeName;
    ModificationTerminalTypeName modificationTerminalTypeName;
    List<Map<String, Object>> additionalData;
    Date effectiveDate;
    String createdBy;
}
