package org.example.policydomain.command.policy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyUpdateCustomerFeeAndTermAMCommand extends BaseCommand {
    String policyId;
    Date maturityDate;
    String createdBy;
}
