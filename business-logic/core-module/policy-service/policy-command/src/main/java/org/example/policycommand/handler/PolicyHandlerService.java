package org.example.policycommand.handler;

import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;

public interface PolicyHandlerService {

    String handle(PolicyCreateCommand command);

    void handle(AdditionalModificationCreateCommand command);
}
