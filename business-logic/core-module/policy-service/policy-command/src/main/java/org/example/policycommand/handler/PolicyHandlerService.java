package org.example.policycommand.handler;

import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToApprovedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToAwaitApproveCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRejectedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRequireInformationCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToUndoneCommand;
import org.example.policydomain.command.policy.PolicyUpdateInternalAMCommand;

public interface PolicyHandlerService {

    String handle(PolicyCreateCommand command);

    void handle(AdditionalModificationCreateCommand command);

    void handle(AdditionalModificationToAwaitApproveCommand command);

    void handle(AdditionalModificationToApprovedCommand additionalModificationToApprovedCommand);

    void handle(AdditionalModificationToRejectedCommand additionalModificationToRejectedCommand);

    void handle(AdditionalModificationToRequireInformationCommand additionalModificationToRequireInformationCommand);

    void handle(AdditionalModificationToUndoneCommand additionalModificationToUndoneCommand);

    void handle(PolicyUpdateInternalAMCommand command);
}
