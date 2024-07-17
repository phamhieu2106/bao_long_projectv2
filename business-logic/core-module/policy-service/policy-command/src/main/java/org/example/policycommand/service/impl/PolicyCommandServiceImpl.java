package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.client.PolicyQueryClient;
import org.example.policycommand.client.QuotationQueryClient;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyCommandService;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.sharedlibrary.QuotationEntityResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyCommandServiceImpl implements PolicyCommandService {

    private final PolicyHandlerService handlerService;
    private final QuotationQueryClient quotationQueryClient;
    private final PolicyQueryClient policyQueryClient;
    private final ModelMapper modelMapper;

    @Override
    public String releasePolicy(String quotationId) {
        QuotationEntityResponse quotationResponse = quotationQueryClient.getQuotationByQuotationId(quotationId);
        PolicyCreateCommand command = modelMapper.map(quotationResponse, PolicyCreateCommand.class);
        command.setPolicyCode(policyQueryClient.generatePolicyCode(command.getProductCode()));
        return handlerService.handle(command);
    }


}
