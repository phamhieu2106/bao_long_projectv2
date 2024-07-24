package org.example.policydomain.command.additional_modification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdditionalModificationToUndoneCommand extends BaseCommand {
    final AdditionalModificationStatus additionalModificationStatus = AdditionalModificationStatus.UNDONE;
    String additionalModificationId;
    String createdBy;
}
