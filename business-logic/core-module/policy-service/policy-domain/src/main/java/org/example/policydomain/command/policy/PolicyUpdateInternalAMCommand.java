package org.example.policydomain.command.policy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PolicyUpdateInternalAMCommand extends BaseCommand {
    String policyId;
    List<Map<String, Object>> product;
    String quotationDistributionName;
    String insuranceCompanyName;
    String createdBy;
}
