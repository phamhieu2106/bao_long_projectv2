package org.example.quotationcommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.client.CustomerQueryClient;
import org.example.quotationcommand.client.PolicyCommandClient;
import org.example.quotationcommand.client.QuotationQueryClient;
import org.example.quotationcommand.client.UserQueryClient;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationEventStoreService;
import org.example.quotationcommand.service.QuotationProducerService;
import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.quotationdomain.command.cud.QuotationCopyCommand;
import org.example.quotationdomain.command.cud.QuotationCreateCommand;
import org.example.quotationdomain.command.cud.QuotationCreateNewVersionCommand;
import org.example.quotationdomain.command.cud.QuotationUpdateCommand;
import org.example.quotationdomain.command.status.*;
import org.example.quotationdomain.domain.model.InsuranceDate;
import org.example.quotationdomain.event.crud.QuotationCreateEvent;
import org.example.quotationdomain.event.crud.QuotationCreateNewVersionEvent;
import org.example.quotationdomain.event.crud.QuotationUpdateEvent;
import org.example.quotationdomain.event.status.*;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.enumeration.ac.Permission;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class QuotationHandlerImpl implements QuotationHandler {

    private final QuotationEventStoreService storeService;
    private final QuotationProducerService producerService;
    private final QuotationQueryClient quotationQueryClient;
    private final CustomerQueryClient customerQueryClient;
    private final UserQueryClient userQueryClient;
    private final PolicyCommandClient policyCommandClient;

    @Override
    public void handle(QuotationCreateCommand command) {
        QuotationAggregate aggregate = new QuotationAggregate();

        InsuranceDate date = getQuotationValidAndVoidDate(command.getProduct());
        aggregate.setQuotationCode(quotationQueryClient.getQuotationCode(command.getProductCode()));
        aggregate.setEffectiveDate(date.getValidInsuranceDate());
        aggregate.setMaturityDate(date.getVoidInsuranceDate());
        aggregate.setQuotationStatus(QuotationStatus.DRAFTING);
        aggregate.setCustomerModel(customerQueryClient
                .getCustomerModelById(command.getCustomerId()));
        aggregate.setBeneficiaryModel(customerQueryClient
                .getCustomerModelById(command.getBeneficiaryId()));
        aggregate.setUserCreatedModel(userQueryClient.getUserModelById(command.getCreatedBy()));
        aggregate.setId(UUID.randomUUID().toString());
        QuotationCreateEvent event = aggregate.apply(command);

        storeService.save(aggregate, event);
        producerService.publish("quotation_create", event);
    }

    @Override
    public void handle(QuotationCreateNewVersionCommand command) {
        QuotationAggregate aggregate = storeService.load(command.getQuotationId());

        InsuranceDate date = getQuotationValidAndVoidDate(command.getProduct());
        aggregate.setEffectiveDate(date.getValidInsuranceDate());
        aggregate.setMaturityDate(date.getVoidInsuranceDate());
        aggregate.setCustomerModel(customerQueryClient
                .getCustomerModelById(command.getCustomerId()));
        aggregate.setBeneficiaryModel(customerQueryClient
                .getCustomerModelById(command.getBeneficiaryId()));
        aggregate.setUserCreatedModel(userQueryClient.getUserModelById(command.getCreatedBy()));
        aggregate.setQuotationVersion(quotationQueryClient.getQuotationVersion(aggregate.getQuotationCode()));
        aggregate.setProduct(command.getProduct());
        aggregate.setInsuranceTypeModel(command.getInsuranceTypeModel());

        QuotationCreateNewVersionEvent event = aggregate.apply(command);

        storeService.save(aggregate, event);
        producerService.publish("quotation_create_new_version", event);
    }

    @Override
    public void handle(QuotationCopyCommand copyCommand) {
        QuotationAggregate aggregate = storeService.load(copyCommand.getId());
        QuotationCreateEvent event = aggregate.apply(copyCommand);

        storeService.save(aggregate, event);
        producerService.publish("quotation_create", event);
    }

    @Override
    public void handle(QuotationUpdateCommand command) {
        QuotationAggregate aggregate = storeService.load(command.getQuotationId());

        InsuranceDate date = getQuotationValidAndVoidDate(command.getProduct());
        aggregate.setEffectiveDate(date.getValidInsuranceDate());
        aggregate.setMaturityDate(date.getVoidInsuranceDate());
        aggregate.setQuotationStatus(QuotationStatus.DRAFTING);
        aggregate.setCustomerModel(customerQueryClient
                .getCustomerModelById(command.getCustomerId()));
        aggregate.setBeneficiaryModel(customerQueryClient
                .getCustomerModelById(command.getBeneficiaryId()));
        QuotationUpdateEvent event = aggregate.apply(command);

        storeService.save(aggregate, event);
        producerService.publish("quotation_update", event);
    }

    @Override
    public void handle(QuotationScheduleStatusCommand command) {
        List<String> aggregates = quotationQueryClient.findAllIdsByQuotationStatus(command);
        aggregates.forEach(qa -> {
            QuotationChangeToDisabledStatusCommand cmd = new QuotationChangeToDisabledStatusCommand();
            cmd.setQuotationId(qa);
            cmd.setCreatedBy("admin");
            handle(cmd);
        });
    }

    @Override
    public void handle(String customerId) {
        List<String> aggregates = quotationQueryClient.findAllIdsByCustomerId(customerId);
        aggregates.forEach(qa -> {
            QuotationChangeToDisabledStatusCommand cmd = new QuotationChangeToDisabledStatusCommand();
            cmd.setQuotationId(qa);
            cmd.setCreatedBy("admin");
            handle(cmd);
        });
    }

    @Override
    public void handle(List<String> customerIds) {
        customerIds.forEach(id -> {
            List<String> quotationIds = quotationQueryClient.findAllQuotationsNotApproveByCustomerId(id);
            quotationIds.forEach(qa -> {
                QuotationChangeToDisabledStatusCommand cmd = new QuotationChangeToDisabledStatusCommand();
                cmd.setQuotationId(qa);
                cmd.setCreatedBy("admin");
                handle(cmd);
            });
        });
    }

    @Override
    public void releasePolicy(String quotationId) {
        QuotationAggregate aggregate = storeService.load(quotationId);
        String policyCode = policyCommandClient.releasePolicy(quotationId);
        aggregate.setPolicyCode(policyCode);

        QuotationUpdateEvent event = aggregate.apply();
        storeService.save(aggregate, event);
        producerService.publish("quotation_update", event);

        List<String> ids = quotationQueryClient.getAllQuotationIdsOtherVersionNotApprovedAndIsNot(
                aggregate.getQuotationCode(), aggregate.getId());
        ids.forEach(id -> {
            QuotationChangeToDisabledStatusCommand cmd = new QuotationChangeToDisabledStatusCommand();
            cmd.setQuotationId(id);
            cmd.setCreatedBy("admin");
            handle(cmd);
        });
    }

    @Override
    public void handle(QuotationChangeToAwaitApproveStatusCommand quotationChangeToAwaitApproveStatusCommand) {
        QuotationAggregate aggregate = storeService.load(quotationChangeToAwaitApproveStatusCommand.getQuotationId());
        isChangeStatusAble(aggregate);
        UserModel userModel = userQueryClient.getUserModelByUsername(quotationChangeToAwaitApproveStatusCommand.getCreatedBy());
        if (aggregate.getLastUserRoleUpdate() != null) {
            if (userModel.getRole().getValue() < aggregate.getLastUserRoleUpdate().getValue()) {
                throw new RuntimeException("Not have permission to change Quotation status!");
            }
        }
        if (aggregate.getUserModels() == null) {
            List<UserModel> list = new ArrayList<>();
            list.add(userModel);
            aggregate.setUserModels(list);
        } else if (quotationQueryClient.exitsByUserModel(userModel, quotationChangeToAwaitApproveStatusCommand.getQuotationId())) {
            throw new RuntimeException();
        } else {
            aggregate.getUserModels().add(userModel);
        }

        aggregate.setQuotationStatus(QuotationStatus.AWAIT_APPROVE);
        aggregate.setLastUserRoleUpdate(userModel.getRole());

        QuotationChangeToAwaitApproveStatusEvent event = aggregate.apply(quotationChangeToAwaitApproveStatusCommand);
        event.setLastUserRoleUpdate(userModel.getRole());
        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status_to_await_approve", event);
    }

    @Override
    public void handle(QuotationChangeToApprovedStatusCommand quotationChangeToApprovedStatusCommand) {
        QuotationAggregate aggregate = storeService.load(quotationChangeToApprovedStatusCommand.getQuotationId());
        isChangeStatusAble(aggregate);
        isApproveAble(aggregate);

        UserModel userModel = userQueryClient.getUserModelByUsername(quotationChangeToApprovedStatusCommand.getApprovedBy());
        if (userModel == null) {
            throw new RuntimeException();
        }
        validatePermission(userModel);

        aggregate.setApprovedAt(new Date());
        aggregate.setApprovedBy(quotationChangeToApprovedStatusCommand.getApprovedBy());

        aggregate.setQuotationStatus(QuotationStatus.APPROVED);
        aggregate.setLastUserRoleUpdate(userModel.getRole());

        QuotationChangeToApprovedStatusEvent event = aggregate.apply(quotationChangeToApprovedStatusCommand);
        event.setLastUserRoleUpdate(userModel.getRole());

        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status_to_approved", event);
    }

    @Override
    public void handle(QuotationChangeToDisabledStatusCommand quotationChangeToDisabledStatusCommand) {
        QuotationAggregate aggregate = storeService.load(quotationChangeToDisabledStatusCommand.getQuotationId());

        UserModel userModel = userQueryClient.getUserModelByUsername(quotationChangeToDisabledStatusCommand.getCreatedBy());
        if (userModel == null) {
            throw new RuntimeException();
        }
        validatePermission(userModel);

        aggregate.setQuotationStatus(QuotationStatus.DISABLED);
        aggregate.setLastUserRoleUpdate(userModel.getRole());

        QuotationChangeToDisabledStatusEvent event = aggregate.apply(quotationChangeToDisabledStatusCommand);
        event.setLastUserRoleUpdate(userModel.getRole());
        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status_to_disabled", event);
    }

    @Override
    public void handle(QuotationChangeToRejectedStatusCommand quotationChangeToRejectedStatusCommand) {
        QuotationAggregate aggregate = storeService.load(quotationChangeToRejectedStatusCommand.getQuotationId());
        isChangeStatusAble(aggregate);
        isRejectedAble(aggregate);

        UserModel userModel = userQueryClient.getUserModelByUsername(quotationChangeToRejectedStatusCommand.getCreatedBy());
        if (userModel == null) {
            throw new RuntimeException();
        }
        validatePermission(userModel);

        aggregate.setQuotationStatus(QuotationStatus.REJECTED);
        aggregate.setLastUserRoleUpdate(userModel.getRole());

        QuotationChangeToRejectedStatusEvent event = aggregate.apply(quotationChangeToRejectedStatusCommand);
        event.setLastUserRoleUpdate(userModel.getRole());
        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status_to_rejected", event);
    }

    @Override
    public void handle(QuotationChangeToRequireInformationStatusCommand quotationChangeToRequireInformationStatusCommand) {
        QuotationAggregate aggregate = storeService.load(quotationChangeToRequireInformationStatusCommand.getQuotationId());
        isChangeStatusAble(aggregate);
        isRequestedAble(aggregate);

        UserModel userModel = userQueryClient.getUserModelByUsername(quotationChangeToRequireInformationStatusCommand.getCreatedBy());
        if (userModel == null) {
            throw new RuntimeException();
        }
        validatePermission(userModel);

        aggregate.setUserModels(null);
        aggregate.setQuotationStatus(QuotationStatus.REQUIRE_INFORMATION);
        aggregate.setLastUserRoleUpdate(userModel.getRole());

        QuotationChangeToRequireInformationStatusEvent event = aggregate.apply(quotationChangeToRequireInformationStatusCommand);
        event.setLastUserRoleUpdate(userModel.getRole());
        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status_to_require_information", event);
    }

    private void isApproveAble(QuotationAggregate aggregate) {
        if (!QuotationStatus.AWAIT_APPROVE.equals(aggregate.getQuotationStatus())) {
            throw new RuntimeException("Invalid quotation status: " + aggregate.getQuotationStatus());
        }
    }

    private void isRejectedAble(QuotationAggregate aggregate) {
        if (!QuotationStatus.AWAIT_APPROVE.equals(aggregate.getQuotationStatus())) {
            throw new RuntimeException("Invalid quotation status: " + aggregate.getQuotationStatus());
        }
    }

    private void isRequestedAble(QuotationAggregate aggregate) {
        if (!QuotationStatus.AWAIT_APPROVE.equals(aggregate.getQuotationStatus())) {
            throw new RuntimeException("Invalid quotation status: " + aggregate.getQuotationStatus());
        }
    }

    private void isChangeStatusAble(QuotationAggregate aggregate) {
        if (QuotationStatus.REJECTED.equals(aggregate.getQuotationStatus())
                || QuotationStatus.DISABLED.equals(aggregate.getQuotationStatus())
                || QuotationStatus.APPROVED.equals(aggregate.getQuotationStatus())) {
            throw new RuntimeException("Invalid quotation status: " + aggregate.getQuotationStatus());
        }
    }

    private void validatePermission(UserModel userModel) {
        if (!userModel.getPermissions().contains(Permission.APPROVE)) {
            throw new RuntimeException("Invalid permission: " + userModel.getPermissions());
        } else if (!userModel.getPermissions().contains(Permission.DENIED)) {
            throw new RuntimeException("Invalid permission: " + userModel.getPermissions());
        } else if (!userModel.getPermissions().contains(Permission.ADDITIONAL_REQUEST)) {
            throw new RuntimeException("Invalid permission: " + userModel.getPermissions());
        }
    }

    private InsuranceDate getQuotationValidAndVoidDate(Object productListObj) {
        List<InsuranceDate> dateList = new ArrayList<>();

        Optional.ofNullable(productListObj)
                .filter(List.class::isInstance).map(List.class::cast)
                .filter(list -> !list.isEmpty())
                .map(list -> (Map<?, ?>) list.get(0))
                .map(product -> (List<?>) product.get("listInsuranceModel"))
                .ifPresent(listInsuranceModel -> {
                    listInsuranceModel.stream()
                            .filter(Map.class::isInstance).map(Map.class::cast)
                            .forEach(insuranceModel -> {
                                String validDateString = (String) insuranceModel.get("validInsuranceDate");
                                String voidDateString = (String) insuranceModel.get("voidInsuranceDate");

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                try {
                                    Date validDate = dateFormat.parse(validDateString);
                                    Date voidDate = dateFormat.parse(voidDateString);
                                    dateList.add(new InsuranceDate(validDate, voidDate));
                                } catch (ParseException e) {
                                    throw new RuntimeException("Error parsing date");
                                }
                            });
                });

        return dateList.stream()
                .reduce((first, second) -> {
                    Date startDate = first.getValidInsuranceDate().before(second.getValidInsuranceDate())
                            ? first.getValidInsuranceDate() : second.getValidInsuranceDate();
                    Date endDate = first.getVoidInsuranceDate().after(second.getVoidInsuranceDate())
                            ? first.getVoidInsuranceDate() : second.getVoidInsuranceDate();
                    return new InsuranceDate(startDate, endDate);
                })
                .orElseThrow(() -> new RuntimeException("No valid dates found"));
    }

}
