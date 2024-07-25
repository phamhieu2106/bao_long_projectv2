package org.example.policycommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policycommand.client.PolicyQueryClient;
import org.example.policycommand.client.QuotationQueryClient;
import org.example.policycommand.client.UserQueryClient;
import org.example.policycommand.handler.PolicyHandlerService;
import org.example.policycommand.service.PolicyCommandService;
import org.example.policydomain.command.PolicyCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationCreateCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToApprovedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToAwaitApproveCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRejectedCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToRequireInformationCommand;
import org.example.policydomain.command.additional_modification.AdditionalModificationToUndoneCommand;
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

        if (!policyQueryClient.isCreateAble(policyId)) {
            return WrapperResponse.fail("Can't create Additional Modification!", HttpStatus.BAD_REQUEST);
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
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToAwaitApprove(String additionalModificationId, AdditionalModificationToAwaitApproveCommand command, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveEmployeePermission(username)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToAwaitApproveAble(additionalModificationId)) {
            return WrapperResponse.fail("Not found AM or AM can't change status!", HttpStatus.BAD_REQUEST);
        }
        try {
            command.setAdditionalModificationId(additionalModificationId);
            command.setCreatedBy(username);
            handlerService.handle(command);
        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying update AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToApproved(String additionalModificationId, AdditionalModificationToApprovedCommand additionalModificationToApprovedCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(username + ": don't have permission!", HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToApprovedAble(additionalModificationId)) {
            return WrapperResponse.fail("Not found AM or AM can't change status!", HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToApprovedCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToApprovedCommand.setApprovedBy(username);
            handlerService.handle(additionalModificationToApprovedCommand);


        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying update AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToRejected(String additionalModificationId, AdditionalModificationToRejectedCommand additionalModificationToRejectedCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(username + ": don't have permission!", HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToRejectedAble(additionalModificationId)) {
            return WrapperResponse.fail("Not found AM or AM can't change status!", HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToRejectedCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToRejectedCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToRejectedCommand);
        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying update AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToRequireInformation(String additionalModificationId, AdditionalModificationToRequireInformationCommand additionalModificationToRequireInformationCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(username + ": don't have permission!", HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToRequireInformationAble(additionalModificationId)) {
            return WrapperResponse.fail("Not found AM or AM can't change status!", HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToRequireInformationCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToRequireInformationCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToRequireInformationCommand);
        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying update AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToUndone(String additionalModificationId, AdditionalModificationToUndoneCommand additionalModificationToUndoneCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail("Not found User!", HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail("You don't have permission to change Status this AM!", HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(username + ": don't have permission!", HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToUndoneAble(additionalModificationId)) {
            return WrapperResponse.fail("Not found AM or AM can't change status!", HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToUndoneCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToUndoneCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToUndoneCommand);
        } catch (Exception e) {
            return WrapperResponse.fail("Somethings wrong when trying update AM!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }
}
