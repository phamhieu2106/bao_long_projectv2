package org.example.policyflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.event.PolicyCreateEvent;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policyflow.service.PolicyConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PolicyConsumerServiceImpl implements PolicyConsumerService {

    private final PolicyEntityRepository policyEntityRepository;

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
}
