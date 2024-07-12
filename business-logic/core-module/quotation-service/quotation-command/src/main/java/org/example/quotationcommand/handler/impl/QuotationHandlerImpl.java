package org.example.quotationcommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.client.CustomerQueryClient;
import org.example.quotationcommand.client.QuotationQueryClient;
import org.example.quotationcommand.client.UserQueryClient;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationEventStoreService;
import org.example.quotationcommand.service.QuotationProducerService;
import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.quotationdomain.command.QuotationChangeStatusCommand;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.quotationdomain.domain.model.InsuranceDate;
import org.example.quotationdomain.event.QuotationChangeStatusEvent;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
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
        QuotationCreateEvent event = aggregate.apply(command);

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
    public void handle(QuotationChangeStatusCommand command) {
        QuotationAggregate aggregate = storeService.load(command.getId());

        UserModel userModel = userQueryClient.getUserModelByUsername(command.getCreatedBy());

        switch (command.getQuotationStatus()) {
            case AWAIT_APPROVE -> {
                isChangeStatusAble(aggregate);

                if (aggregate.getUserModels() == null) {
                    List<UserModel> list = new ArrayList<>();
                    list.add(userModel);
                    aggregate.setUserModels(list);
                } else if (quotationQueryClient.exitsByUserModel(userModel, command.getId())) {
                    throw new RuntimeException();
                } else {
                    aggregate.getUserModels().add(userModel);
                }
            }
            case APPROVED -> {
                validatePermission(userModel);
                isChangeStatusAble(aggregate);
                isApproveAble(aggregate, command);

                aggregate.setApprovedAt(new Date());
                aggregate.setApprovedBy(command.getCreatedBy());
            }
            case REQUIRE_INFORMATION -> {
                validatePermission(userModel);
                isChangeStatusAble(aggregate);
                isRequestedAble(aggregate);

                aggregate.setUserModels(null);
            }
            case REJECTED -> {
                validatePermission(userModel);
                isChangeStatusAble(aggregate);
                isRejectedAble(aggregate);

                aggregate.setQuotationStatus(QuotationStatus.REJECTED);
            }
            case DISABLED -> aggregate.setQuotationStatus(QuotationStatus.DISABLED);
            default -> throw new RuntimeException("Unexpected quotation status: " + command.getQuotationStatus());
        }


        QuotationChangeStatusEvent event = aggregate.apply(command);
        storeService.save(aggregate, event);
        producerService.publish("quotation_change_status", event);
    }


    private void isApproveAble(QuotationAggregate aggregate, QuotationChangeStatusCommand command) {
        if (QuotationStatus.DRAFTING.equals(aggregate.getQuotationStatus())
                || QuotationStatus.REQUIRE_INFORMATION.equals(aggregate.getQuotationStatus())
                && QuotationStatus.APPROVED.equals(command.getQuotationStatus())) {
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

//    private void validateUserRole(UserModel userModelCreated, UserModel userModelUpdated) {
//
//    }

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
