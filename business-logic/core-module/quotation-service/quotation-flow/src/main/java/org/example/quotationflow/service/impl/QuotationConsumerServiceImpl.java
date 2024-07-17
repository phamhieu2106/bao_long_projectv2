package org.example.quotationflow.service.impl;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.domain.model_view.HealthIdentityVIewModel;
import org.example.quotationdomain.domain.model_view.MotorIdentityViewModel;
import org.example.quotationdomain.domain.view.QuotationView;
import org.example.quotationdomain.event.QuotationChangeStatusEvent;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
import org.example.quotationdomain.repository.QuotationESRepository;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuotationConsumerServiceImpl {

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
                event.getInsuranceTypeModel()
        );

        saveQuotationView(quotationEntity);

        repository.save(quotationEntity);

    }

    @KafkaListener(topics = "quotation_update", groupId = "quotation_group")
    public void handleUpdateEvent(QuotationUpdateEvent event) {
//        QuotationEntity quotationEntity = QuotationEntity.builder()
//                .id(event.getQuotationId())
//                .quotationCode(event.getQuotationCode())
//                .policyCode(event.getPolicyCode())
//                .productType(event.getProductType())
//                .productCode(event.getProductCode())
//                .product(event.getProduct())
//                .isCoinsurance(event.getIsCoinsurance())
//                .quotationStatus(event.getQuotationStatus())
//                .quotationDistributionName(event.getQuotationDistributionName())
//                .quotationManagerName(event.getQuotationManagerName())
//                .insuranceCompanyName(event.getInsuranceCompanyName())
//                .effectiveDate(event.getEffectiveDate())
//                .maturityDate(event.getMaturityDate())
//                .customerId(event.getCustomerId())
//                .beneficiaryId(event.getBeneficiaryId())
//                .currency(event.getCurrency())
//                .rate(event.getRate())
//                .insuranceTypeModel(event.getInsuranceTypeModel())
//                .totalFeeAfterTax(event.getTotalFeeAfterTax())
//                .createdAt(event.getTimestamp())
//                .createdBy(event.getCreatedBy())
//                .build();
//        repository.save(quotationEntity);
//
//        esRepository.save(new QuotationView(
//                quotationEntity.getId(),
//                quotationEntity.getQuotationCode(),
//                quotationEntity.getPolicyCode(),
//                quotationEntity.getQuotationStatus(),
//                quotationEntity.getQuotationTypeStatus(),
//                quotationEntity.getProductCode(),
//                quotationEntity.getProductName(),
//                quotationEntity.getCustomerId(),
//                quotationEntity.getTotalFeeAfterTax(),
//                quotationEntity.getCreatedAt(),
//                quotationEntity.getCreatedBy()
//        ));
    }

    @KafkaListener(topics = "quotation_change_status", groupId = "quotation_group")
    public void handleChangeStatusEvent(QuotationChangeStatusEvent event) {
        Optional<QuotationEntity> optional = repository.findById(event.getId());
        if (optional.isEmpty()) {
            throw new NotFoundException();
        }

        QuotationEntity quotationEntity = optional.get();
        quotationEntity.setQuotationStatus(event.getQuotationStatus());
        quotationEntity.setUserModels(event.getUserModels());
        quotationEntity.setLastUserRoleUpdate(event.getLastUserRoleUpdate());
        if (QuotationStatus.APPROVED.equals(event.getQuotationStatus())) {
            quotationEntity.setApproveBy(event.getApprovedBy());
            quotationEntity.setApprovedAt(event.getApprovedAt());
        }


        repository.save(quotationEntity);
        saveQuotationView(quotationEntity);
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
                if (productMap.containsKey("listInsuranceModel")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> listInsuranceModels = (List<Map<String, Object>>) productMap.get("listInsuranceModel");

                    for (Map<String, Object> insuranceModel : listInsuranceModels) {
                        if (insuranceModel.containsKey("motorIdentityModel")) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> motorIdentityModelMap = (Map<String, Object>) insuranceModel.get("motorIdentityModel");
                            if (motorIdentityModelMap.containsKey("identityNumber") && motorIdentityModelMap.containsKey("frameNumber")) {
                                motorIdentityModels.add(new MotorIdentityViewModel(
                                        motorIdentityModelMap.get("identityNumber").toString(), motorIdentityModelMap.get("frameNumber").toString()
                                ));
                            }
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return motorIdentityModels;
    }

    private List<HealthIdentityVIewModel> getHealthIdentityModels(List<Map<String, Object>> maps) {
        List<HealthIdentityVIewModel> healthIdentityVIewModels = new ArrayList<>();
        try {

            for (Map<String, Object> productMap : maps) {
                if (productMap.containsKey("listInsuranceModel")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> listInsuranceModels = (List<Map<String, Object>>) productMap.get("listInsuranceModel");

                    for (Map<String, Object> insuranceModel : listInsuranceModels) {
                        if (insuranceModel.containsKey("healthIdentityModel")) {
                            @SuppressWarnings("unchecked")
                            Map<String, Object> healthIdentityModelMap = (Map<String, Object>) insuranceModel.get("healthIdentityModel");
                            if (healthIdentityModelMap.containsKey("identityNumber")) {
                                healthIdentityVIewModels.add(new HealthIdentityVIewModel(
                                                insuranceModel.get("name").toString(), healthIdentityModelMap.get("identityNumber").toString()
                                        )
                                );
                            }
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return healthIdentityVIewModels;
    }
}
