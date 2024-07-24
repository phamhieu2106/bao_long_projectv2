package org.example.policyflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationCreateEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToApprovedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToAwaitApproveEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRejectedEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToRequireInformationEvent;
import org.example.policydomain.event.additional_modification.AdditionalModificationToUndoneEvent;
import org.example.policydomain.repository.AdditionalModificationEntityRepository;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policyflow.service.PolicyConsumerService;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyConsumerServiceImpl implements PolicyConsumerService {

    private final PolicyEntityRepository policyEntityRepository;
    private final AdditionalModificationEntityRepository additionalModificationEntityRepository;

    @Override
    @KafkaListener(topics = "policy_create", groupId = "policy_group")
    public void subscribe(PolicyCreateEvent event) {
        PolicyEntity policyEntity = PolicyEntity.builder()
                .id(event.getId())
                .quotationCode(event.getQuotationCode())
                .policyCode(event.getPolicyCode())
                .productType(event.getProductType())
                .productCode(event.getProductCode())
                .productName(event.getProductName())
                .product(event.getProduct())
                .isCoinsurance(event.getIsCoinsurance())
                .quotationStatus(event.getQuotationStatus())
                .quotationTypeStatus(event.getQuotationTypeStatus())
                .currency(event.getCurrency())
                .rate(event.getRate())
                .insuranceTypeModel(event.getInsuranceTypeModel())
                .totalFeeAfterTax(event.getTotalFeeAfterTax())
                .createdAt(event.getTimestamp())
                .approveBy(event.getApprovedBy())
                .approvedAt(event.getApproveAt())
                .quotationDistributionName(event.getQuotationDistributionName())
                .quotationManagerName(event.getQuotationManagerName())
                .insuranceCompanyName(event.getInsuranceCompanyName())
                .effectiveDate(event.getEffectiveDate())
                .maturityDate(event.getMaturityDate())
                .customer(event.getCustomerModel())
                .beneficiary(event.getBeneficiaryModel())
                .userCreatedModel(event.getUserCreatedModel())
                .lastUserRoleUpdate(event.getLastUserRoleUpdate())
                .userModels(event.getUserModels())
                .build();
        policyEntityRepository.save(policyEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_create", groupId = "am-group")
    public void subscribe(AdditionalModificationCreateEvent event) {
        AdditionalModificationEntity modificationEntity = new AdditionalModificationEntity(
                event.getAdditionalModificationId(),
                event.getAdditionalModificationCode(),
                event.getModificationType(),
                event.getModificationTypeName(),
                event.getEffectiveDate(),
                event.getAdditionalData(),
                event.getPolicyId(),
                AdditionalModificationStatus.DRAFTING,
                event.getCreatedAt(),
                event.getCreatedBy(),
                null,
                null,
                null,
                null
        );
        additionalModificationEntityRepository.save(modificationEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_to_await_approve", groupId = "am-group")
    public void subscribe(AdditionalModificationToAwaitApproveEvent event) {
        AdditionalModificationEntity modificationEntity = additionalModificationEntityRepository
                .findById(event.getAdditionalModificationId()).orElse(null);
        if (modificationEntity == null) throw new EntityNotFoundException();

        modificationEntity.setAdditionalModificationStatus(event.getAdditionalModificationStatus());
        modificationEntity.setModifiedBy(event.getCreatedBy());
        modificationEntity.setModifiedAt(event.getTimestamp());
        additionalModificationEntityRepository.save(modificationEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_to_approved", groupId = "am-group")
    public void subscribe(AdditionalModificationToApprovedEvent event) {
        AdditionalModificationEntity modificationEntity = additionalModificationEntityRepository
                .findById(event.getAdditionalModificationId()).orElse(null);
        if (modificationEntity == null) throw new EntityNotFoundException();
        modificationEntity.setAdditionalModificationCode(event.getAdditionalModificationCode());
        modificationEntity.setAdditionalModificationStatus(event.getAdditionalModificationStatus());
        modificationEntity.setApprovedBy(event.getApprovedBy());
        modificationEntity.setApprovedAt(event.getTimestamp());
        modificationEntity.setModifiedBy(event.getApprovedBy());
        modificationEntity.setModifiedAt(event.getTimestamp());
        additionalModificationEntityRepository.save(modificationEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_to_rejected", groupId = "am-group")
    public void subscribe(AdditionalModificationToRejectedEvent event) {
        AdditionalModificationEntity modificationEntity = additionalModificationEntityRepository
                .findById(event.getAdditionalModificationId()).orElse(null);
        if (modificationEntity == null) throw new EntityNotFoundException();

        modificationEntity.setAdditionalModificationStatus(event.getAdditionalModificationStatus());
        modificationEntity.setModifiedBy(event.getCreatedBy());
        modificationEntity.setModifiedAt(event.getTimestamp());
        additionalModificationEntityRepository.save(modificationEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_to_require_information", groupId = "am-group")
    public void subscribe(AdditionalModificationToRequireInformationEvent event) {
        AdditionalModificationEntity modificationEntity = additionalModificationEntityRepository
                .findById(event.getAdditionalModificationId()).orElse(null);
        if (modificationEntity == null) throw new EntityNotFoundException();

        modificationEntity.setAdditionalModificationStatus(event.getAdditionalModificationStatus());
        modificationEntity.setModifiedBy(event.getCreatedBy());
        modificationEntity.setModifiedAt(event.getTimestamp());
        additionalModificationEntityRepository.save(modificationEntity);
    }

    @Override
    @KafkaListener(topics = "additional_modification_to_undone", groupId = "am-group")
    public void subscribe(AdditionalModificationToUndoneEvent event) {
        AdditionalModificationEntity modificationEntity = additionalModificationEntityRepository
                .findById(event.getAdditionalModificationId()).orElse(null);
        if (modificationEntity == null) throw new EntityNotFoundException();

        modificationEntity.setAdditionalModificationStatus(event.getAdditionalModificationStatus());
        modificationEntity.setModifiedBy(event.getCreatedBy());
        modificationEntity.setModifiedAt(event.getTimestamp());
        additionalModificationEntityRepository.save(modificationEntity);
    }
}
