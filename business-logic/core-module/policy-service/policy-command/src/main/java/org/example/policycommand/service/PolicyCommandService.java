package org.example.policycommand.service;

import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface PolicyCommandService {
    String releasePolicy(String quotationId);

    WrapperResponse additionalModificationCreate(String policyId, AdditionalModificationCreateCommand command, String username);
}
