package org.example.policyquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.repository.AdditionalModificationEntityRepository;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policyquery.service.PolicyInternalService;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyInternalServiceImpl implements PolicyInternalService {

    private final PolicyEntityRepository policyEntityRepository;
    private final AdditionalModificationEntityRepository additionalModificationEntityRepository;

    @Override
    public String getPolicyCode(String productCode) {
        StringBuilder code = new StringBuilder("P-");
        long count = policyEntityRepository.count() + 1;
        do {
            code.append(productCode);
            code.append(getYearSuffix());
            code.append(String.format("%06d", count));
            if (policyEntityRepository.existsByQuotationCode(String.valueOf(code))) {
                code = new StringBuilder("P-")
                        .append(productCode)
                        .append(getYearSuffix())
                        .append(String.format("%06d", ++count));
            }
        } while (policyEntityRepository.existsByQuotationCode(String.valueOf(code)));
        return String.valueOf(code);
    }

    @Override
    public boolean isExitsById(String policyId) {
        return policyEntityRepository.existsById(policyId);
    }

    @Override
    public boolean isCreateAble(String policyId) {
        List<AdditionalModificationEntity> additionalModificationEntities = additionalModificationEntityRepository.findAllByPolicyIdOrderByCreatedAtDesc(policyId);
        if (additionalModificationEntities.isEmpty()) {
            return true;
        } else
            return AdditionalModificationStatus.APPROVED.equals(additionalModificationEntities.get(0).getAdditionalModificationStatus())
                    || AdditionalModificationStatus.REJECTED.equals(additionalModificationEntities.get(0).getAdditionalModificationStatus())
                    || AdditionalModificationStatus.UNDONE.equals(additionalModificationEntities.get(0).getAdditionalModificationStatus());
    }

    @Override
    public boolean isValidEffectDate(String policyId, Date effectiveDate) {
        PolicyEntity policyEntity = policyEntityRepository.findById(policyId).orElse(null);
        if (policyEntity == null) return false;
        else if (!effectiveDate.after(policyEntity.getEffectiveDate())) return false;
        List<AdditionalModificationEntity> additionalModificationEntities = additionalModificationEntityRepository.
                findAllByPolicyIdAndAdditionalModificationStatusOrderByCreatedAtDesc(policyId, AdditionalModificationStatus.APPROVED);
        if (!additionalModificationEntities.isEmpty()) {
            return !effectiveDate.before(additionalModificationEntities.get(0).getEffectiveDate());
        }

        return true;
    }

    private String getYearSuffix() {
        return Integer.toString(LocalDate.now().getYear() % 100);
    }

}
