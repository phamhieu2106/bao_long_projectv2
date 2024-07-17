package org.example.policycommand.handler;

import org.example.policydomain.command.PolicyCreateCommand;

public interface PolicyHandlerService {

    String handle(PolicyCreateCommand command);
}
