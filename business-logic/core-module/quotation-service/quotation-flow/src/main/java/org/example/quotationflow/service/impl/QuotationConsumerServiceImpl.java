package org.example.quotationflow.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.domain.view.QuotationView;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
import org.example.quotationdomain.repository.QuotationESRepository;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
                null
        );

        repository.save(quotationEntity);

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
                .productName(quotationEntity.getProductName())
                .customerName(quotationEntity.getUserCreatedModel().getUsername())
                .productNameKeyword(quotationEntity.getProductName())
                .customerNameKeyword(quotationEntity.getUserCreatedModel().getUsername())
                .feeAfterTax(quotationEntity.getTotalFeeAfterTax())
                .timeStamp(quotationEntity.getCreatedAt())
                .approveAt(null).build());

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
}
