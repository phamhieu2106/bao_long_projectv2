package org.example.quotationflow.service.impl;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.domain.model_view.HealthIdentityVIewModel;
import org.example.quotationdomain.domain.model_view.MotorIdentityViewModel;
import org.example.quotationdomain.domain.view.QuotationView;
import org.example.quotationdomain.event.crud.QuotationCreateEvent;
import org.example.quotationdomain.event.crud.QuotationCreateNewVersionEvent;
import org.example.quotationdomain.event.crud.QuotationUpdateEvent;
import org.example.quotationdomain.event.status.QuotationChangeToApprovedStatusEvent;
import org.example.quotationdomain.event.status.QuotationChangeToAwaitApproveStatusEvent;
import org.example.quotationdomain.event.status.QuotationChangeToDisabledStatusEvent;
import org.example.quotationdomain.event.status.QuotationChangeToRejectedStatusEvent;
import org.example.quotationdomain.event.status.QuotationChangeToRequireInformationStatusEvent;
import org.example.quotationdomain.repository.QuotationESRepository;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuotationConsumerServiceImpl {

    private static final String NAME = "name";
    private static final String LIST_INSURANCE_MODEL = "listInsuranceModel";
    private static final String MOTOR_IDENTITY_MODEL = "motorIdentityModel";
    private static final String HEAL_IDENTITY_MODEL = "healthIdentityModel";
    private static final String IDENTITY_NUMBER = "identityNumber";
    private static final String FRAME_NUMBER = "frameNumber";
    private final QuotationEntityRepository repository;
    private final QuotationESRepository esRepository;

    @KafkaListener(topics = "quotation_create", groupId = "quotation_group")
    public void handleCreateEvent(QuotationCreateEvent event) {
        QuotationEntity quotationEntity = new QuotationEntity(
                event.getId(),
                event.getProductName(),
                event.getProductType(),
                event.getProductCode(),
                event.getQuotationDistributionName(),
                event.getQuotationManagerName(),
                event.getInsuranceCompanyName(),
                event.getEffectiveDate(),
                event.getMaturityDate(),
                event.getCustomerModel(),
                event.getBeneficiaryModel(),
                event.getQuotationCode(),
                event.getPolicyCode(),
                event.getProduct(),
                event.getIsCoinsurance(),
                event.getQuotationStatus(),
                event.getQuotationTypeStatus(),
                event.getCurrency(),
                event.getRate(),
                event.getTimestamp(),
                event.getUserCreatedModel(),
                null,
                null,
                event.getTotalFeeAfterTax(),
                event.getInsuranceTypeModel(),
                1
        );

        saveQuotationView(quotationEntity);
        repository.save(quotationEntity);

    }

    @KafkaListener(topics = "quotation_create_new_version", groupId = "quotation_group")
    public void handleCreateNewVersionEvent(QuotationCreateNewVersionEvent event) {
        QuotationEntity quotationEntity = new QuotationEntity(
                event.getId(),
                event.getProductName(),
                event.getProductType(),
                event.getProductCode(),
                event.getQuotationDistributionName(),
                event.getQuotationManagerName(),
                event.getInsuranceCompanyName(),
                event.getEffectiveDate(),
                event.getMaturityDate(),
                event.getCustomerModel(),
                event.getBeneficiaryModel(),
                event.getQuotationCode(),
                event.getPolicyCode(),
                event.getProduct(),
                event.getIsCoinsurance(),
                event.getQuotationStatus(),
                event.getQuotationTypeStatus(),
                event.getCurrency(),
                event.getRate(),
                event.getTimestamp(),
                event.getUserCreatedModel(),
                event.getApprovedBy(),
                event.getApproveAt(),
                event.getTotalFeeAfterTax(),
                event.getInsuranceTypeModel(),
                event.getQuotationVersion()
        );
        quotationEntity.setUserModels(event.getUserModels());
        saveQuotationView(quotationEntity);
        repository.save(quotationEntity);

    }

    @KafkaListener(topics = "quotation_update", groupId = "quotation_group")
    public void handleUpdateEvent(QuotationUpdateEvent event) {
        QuotationEntity quotationEntity = isQuotationExits(event.getId());
        quotationEntity.setPolicyCode(event.getPolicyCode());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);

    }

    @KafkaListener(topics = "quotation_change_status_to_await_approve", groupId = "quotation_group")
    public void subscribe(QuotationChangeToAwaitApproveStatusEvent event) {
        QuotationEntity quotationEntity = isQuotationExits(event.getQuotationId());
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setUserModels(event.getUserModels());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
    }

    @KafkaListener(topics = "quotation_change_status_to_approved", groupId = "quotation_group")
    public void subscribe(QuotationChangeToApprovedStatusEvent event) {
        QuotationEntity quotationEntity = isQuotationExits(event.getQuotationId());
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());
        quotationEntity.setApprovedAt(event.getApprovedAt());
        quotationEntity.setApproveBy(event.getApprovedBy());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
    }

    @KafkaListener(topics = "quotation_change_status_to_disabled", groupId = "quotation_group")
    public void subscribe(QuotationChangeToDisabledStatusEvent event) {
        QuotationEntity quotationEntity = isQuotationExits(event.getQuotationId());
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
    }

    @KafkaListener(topics = "quotation_change_status_to_rejected", groupId = "quotation_group")
    public void subscribe(QuotationChangeToRejectedStatusEvent event) {
        QuotationEntity quotationEntity = isQuotationExits(event.getQuotationId());
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
    }

    @KafkaListener(topics = "quotation_change_status_to_require_information", groupId = "quotation_group")
    public void subscribe(QuotationChangeToRequireInformationStatusEvent event) {

        QuotationEntity quotationEntity = isQuotationExits(event.getQuotationId());
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());

        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
    }

    private QuotationEntity isQuotationExits(String quotationId) {
        Optional<QuotationEntity> optional = repository.findById(quotationId);
        if (optional.isEmpty()) {
            throw new NotFoundException();
        }
        return optional.get();
    }

    private void saveQuotationView(QuotationEntity quotationEntity) {
        esRepository.save(QuotationView.builder()
                .id(quotationEntity.getId())
                .quotationCode(quotationEntity.getQuotationCode())
                .policyCode(quotationEntity.getPolicyCode())
                .quotationStatus(quotationEntity.getQuotationStatus())
                .quotationTypeStatus(quotationEntity.getQuotationTypeStatus())
                .productCode(quotationEntity.getProductCode())
                .createdBy(quotationEntity.getUserCreatedModel().getUsername())
                .userOffice(quotationEntity.getUserCreatedModel().getOffice())
                .apartment(quotationEntity.getUserCreatedModel().getApartment())
                .productType(quotationEntity.getProductType())
                .customerName(quotationEntity.getCustomer().getCustomerName())
                .customerNameKeyword(quotationEntity.getCustomer().getCustomerName())
                .feeAfterTax(quotationEntity.getTotalFeeAfterTax())
                .createdAt(quotationEntity.getCreatedAt())
                .approvedAt(quotationEntity.getApprovedAt())
                .motorIdentityViewModel(getMotorIdentityModels(quotationEntity.getProduct()))
                .healthIdentityVIewModel(getHealthIdentityModels(quotationEntity.getProduct()))
                .build());
    }

    private List<MotorIdentityViewModel> getMotorIdentityModels(List<Map<String, Object>> maps) {
        List<MotorIdentityViewModel> motorIdentityModels = new ArrayList<>();
        try {
            for (Map<String, Object> productMap : maps) {
                if (productMap.containsKey(LIST_INSURANCE_MODEL)) {
                    handleMotorData(motorIdentityModels, productMap);
                }
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return motorIdentityModels;
    }

    private void handleMotorData(List<MotorIdentityViewModel> motorIdentityModels, Map<String, Object> productMap) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listInsuranceModels = (List<Map<String, Object>>) productMap.get(LIST_INSURANCE_MODEL);
        for (Map<String, Object> insuranceModel : listInsuranceModels) {
            if (insuranceModel.containsKey(MOTOR_IDENTITY_MODEL)) {
                @SuppressWarnings("unchecked")
                Map<String, Object> motorIdentityModelMap = (Map<String, Object>) insuranceModel.get(MOTOR_IDENTITY_MODEL);
                if (motorIdentityModelMap.containsKey(IDENTITY_NUMBER) && motorIdentityModelMap.containsKey(FRAME_NUMBER)) {
                    motorIdentityModels.add(new MotorIdentityViewModel(
                            motorIdentityModelMap.get(IDENTITY_NUMBER).toString(), motorIdentityModelMap.get(FRAME_NUMBER).toString()
                    ));
                }
            }
        }
    }

    private List<HealthIdentityVIewModel> getHealthIdentityModels(List<Map<String, Object>> maps) {
        List<HealthIdentityVIewModel> healthIdentityVIewModels = new ArrayList<>();
        try {
            for (Map<String, Object> productMap : maps) {
                if (productMap.containsKey(LIST_INSURANCE_MODEL)) {
                    handleHealthData(healthIdentityVIewModels, productMap);
                }
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }

        return healthIdentityVIewModels;
    }

    private void handleHealthData(List<HealthIdentityVIewModel> healthIdentityVIewModels, Map<String, Object> productMap) {
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> listInsuranceModels = (List<Map<String, Object>>) productMap.get(LIST_INSURANCE_MODEL);

        for (Map<String, Object> insuranceModel : listInsuranceModels) {
            if (insuranceModel.containsKey(HEAL_IDENTITY_MODEL)) {
                @SuppressWarnings("unchecked")
                Map<String, Object> healthIdentityModelMap = (Map<String, Object>) insuranceModel.get(HEAL_IDENTITY_MODEL);
                if (healthIdentityModelMap.containsKey(IDENTITY_NUMBER)) {
                    healthIdentityVIewModels.add(new HealthIdentityVIewModel(
                                    insuranceModel.get(NAME).toString(), healthIdentityModelMap.get(IDENTITY_NUMBER).toString()
                            )
                    );
                }
            }
        }
    }

}
