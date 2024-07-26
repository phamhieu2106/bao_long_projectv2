package org.example.policydomain.command.policy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyUpdateCustomerInformationAMCommand {
    String policyId;
    String customerId;
    String beneficiaryId;
    String createdBy;
}
