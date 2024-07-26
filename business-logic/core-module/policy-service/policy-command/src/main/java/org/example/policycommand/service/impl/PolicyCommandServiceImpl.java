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
import org.example.policydomain.command.policy.PolicyUpdateInternalAMCommand;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.sharedlibrary.QuotationEntityResponse;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PolicyCommandServiceImpl implements PolicyCommandService {

    private static final String NOT_FOUND_USER_MESSAGE = "User not found!";
    private static final String NO_PERMISSION_TO_CREATE_AM_MESSAGE = "Can't create Additional Modification!";
    private static final String NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE = "No permission to update Status this AM!";
    private static final String NO_VALID_POLICY_ID_OR_EFFECT_DATE_MESSAGE = "Not valid Id Policy Or not valid effect date!";
    private static final String SERVER_ERROR_MESSAGE = "Somethings wrong when trying to create or update AM!";
    private static final String MATURITY_DATE = "maturityDate";
    private static final String QUOTATION_DISTRIBUTION_NAME = "quotationDistributionName";
    private static final String INSURANCE_COMPANY_NAME = "insuranceCompanyName";
    private static final String CUSTOMER_ID = "customerId";
    private static final String BENEFICIARY_ID = "beneficiaryId";
    private static final String PRODUCT = "product";
    private static final String INSURANCE_TYPE_MODEL = "insuranceTypeModel";

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
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isCreateAble(policyId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_CREATE_AM_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isValidEffectDate(policyId, command.getEffectiveDate())) {
            return WrapperResponse.fail(NO_VALID_POLICY_ID_OR_EFFECT_DATE_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        try {
            command.setPolicyId(policyId);
            command.setCreatedBy(username);
            handlerService.handle(command);
        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToAwaitApprove(String additionalModificationId, AdditionalModificationToAwaitApproveCommand command, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveEmployeePermission(username)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToAwaitApproveAble(additionalModificationId)) {
            return WrapperResponse.fail(NO_VALID_POLICY_ID_OR_EFFECT_DATE_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            command.setAdditionalModificationId(additionalModificationId);
            command.setCreatedBy(username);
            handlerService.handle(command);
        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToApproved(String additionalModificationId, AdditionalModificationToApprovedCommand additionalModificationToApprovedCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToApprovedAble(additionalModificationId)) {
            return WrapperResponse.fail(NO_VALID_POLICY_ID_OR_EFFECT_DATE_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToApprovedCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToApprovedCommand.setApprovedBy(username);
            handlerService.handle(additionalModificationToApprovedCommand);


        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToRejected(String additionalModificationId, AdditionalModificationToRejectedCommand additionalModificationToRejectedCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToRejectedAble(additionalModificationId)) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToRejectedCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToRejectedCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToRejectedCommand);
        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToRequireInformation(String additionalModificationId, AdditionalModificationToRequireInformationCommand additionalModificationToRequireInformationCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!policyQueryClient.isToRequireInformationAble(additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToRequireInformationCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToRequireInformationCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToRequireInformationCommand);
        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public WrapperResponse additionalModificationToUndone(String additionalModificationId, AdditionalModificationToUndoneCommand additionalModificationToUndoneCommand, String username) {
        if (!userQueryClient.isExitSByUsername(username)) {
            return WrapperResponse.fail(NOT_FOUND_USER_MESSAGE, HttpStatus.NOT_FOUND);
        }

        if (!policyQueryClient.isChangeAMStatusAble(username, additionalModificationId)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }

        if (!userQueryClient.isHaveDirectorPermission(username)) {
            return WrapperResponse.fail(NO_PERMISSION_TO_UPDATE_AM_STATUS_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        if (!policyQueryClient.isToUndoneAble(additionalModificationId)) {
            return WrapperResponse.fail(NO_VALID_POLICY_ID_OR_EFFECT_DATE_MESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            additionalModificationToUndoneCommand.setAdditionalModificationId(additionalModificationId);
            additionalModificationToUndoneCommand.setCreatedBy(username);
            handlerService.handle(additionalModificationToUndoneCommand);
        } catch (Exception e) {
            return WrapperResponse.fail(SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
    }

    @Override
    public void policyUpdateScheduled() {
        List<AdditionalModificationEntity> modificationEntities = policyQueryClient.findAllAMEffected();
        if (!modificationEntities.isEmpty()) {
            modificationEntities.forEach(e ->
            {
                if (ModificationType.INTERNAL_MODIFICATION.equals(e.getModificationType())
                        && ModificationTypeName.SPECIAL_ADDITIONAL_MODIFICATION.equals(e.getModificationTypeName())) {
                    processInternalModification(e);
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    private void processInternalModification(AdditionalModificationEntity additionalModificationEntity) {

        Map<String, Object> additionalDataMap = additionalModificationEntity.getAdditionalData().get(0);
        List<Map<String, Object>> product = (List<Map<String, Object>>) additionalDataMap.get(PRODUCT);
        String quotationDistributionName = (String) additionalDataMap.get(QUOTATION_DISTRIBUTION_NAME);
        String insuranceCompanyName = (String) additionalDataMap.get(INSURANCE_COMPANY_NAME);

        handlerService.handle(new PolicyUpdateInternalAMCommand(
                additionalModificationEntity.getPolicyId(),
                product,
                quotationDistributionName,
                insuranceCompanyName,
                "admin"
        ));
    }
}
