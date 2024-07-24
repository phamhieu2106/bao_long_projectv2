package org.example.policydomain.command.policy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PolicyInternalModificationCommand extends BaseCommand {
    String policyId;
    List<Map<String, Object>> additionalData;
    String createdBy;
}
