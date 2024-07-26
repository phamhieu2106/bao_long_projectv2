package org.example.policydomain.command.policy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.QuotationStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyUpdateCustomerTerminalAMCommand extends BaseCommand {
    static final QuotationStatus quotationStatus = QuotationStatus.DISABLED;
    String policyId;
    String createdBy;
}
