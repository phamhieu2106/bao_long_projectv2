package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.client.PolicyQueryClient;
import org.example.policycommand.client.QuotationQueryClient;
import org.example.policycommand.client.UserQueryClient;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyCommandService;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.sharedlibrary.QuotationEntityResponse;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyCommandServiceImpl implements PolicyCommandService {

    private final PolicyHandlerService handlerService;
    private final QuotationQueryClient quotationQueryClient;
    private final PolicyQueryClient policyQueryClient;
    private final UserQueryClient userQueryClient;
    private final ModelMapper modelMapper;

    @Override
    public String releasePolicy(String quotationId) {
        QuotationEntityResponse quotationResponse = quotationQueryClient.getQuotationByQuotationId(quotationId);
        PolicyCreateCommand command = modelMapper.map(quotationResponse, PolicyCreateCommand.class);
        command.setApprovedBy(quotationResponse.getApproveBy());
        command.setPolicyCode(policyQueryClient.generatePolicyCode(command.getProductCode()));
        return handlerService.handle(command);
    }

    @Override
    public WrapperResponse additionalModificationCreate(String policyId, AdditionalModificationCreateCommand command, String username) {

        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isValidEffectDate(policyId, command.getEffectiveDate())) {
            return WrapperResponse.fail("Not valid Id Policy Or not valid effect date!", HttpStatus.BAD_REQUEST);
        }

        try {
            command.setPolicyId(policyId);
            command.setCreatedBy(username);
            handlerService.handle(command);
        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying create AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
