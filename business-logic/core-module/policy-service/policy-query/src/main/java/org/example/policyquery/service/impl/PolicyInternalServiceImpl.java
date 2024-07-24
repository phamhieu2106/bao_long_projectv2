package org.example.policyquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.policydomain.entity.PolicyEntity;
import org.example.policydomain.repository.AdditionalModificationEntityRepository;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policyquery.service.PolicyInternalService;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean isToAwaitApproveAble(String additionalModificationId) {
        AdditionalModificationEntity additionalModificationEntity = additionalModificationEntityRepository.findById(additionalModificationId).orElse(null);
        if (additionalModificationEntity == null) return false;
        else if (AdditionalModificationStatus.DRAFTING.equals(additionalModificationEntity.getAdditionalModificationStatus()))
            return true;
        else
            return AdditionalModificationStatus.REQUIRE_INFORMATION.equals(additionalModificationEntity.getAdditionalModificationStatus());
    }

    @Override
    public boolean isToRequireInformationAble(String additionalModificationId) {
        AdditionalModificationEntity additionalModificationEntity = additionalModificationEntityRepository.findById(additionalModificationId).orElse(null);
        if (additionalModificationEntity == null) return false;
        else
            return AdditionalModificationStatus.AWAIT_APPROVE.equals(additionalModificationEntity.getAdditionalModificationStatus());
    }

    @Override
    public boolean isToApprovedAble(String additionalModificationId) {
        AdditionalModificationEntity additionalModificationEntity = additionalModificationEntityRepository.findById(additionalModificationId).orElse(null);
        if (additionalModificationEntity == null) return false;
        else
            return AdditionalModificationStatus.AWAIT_APPROVE.equals(additionalModificationEntity.getAdditionalModificationStatus());
    }

    @Override
    public boolean isToRejectedAble(String additionalModificationId) {
        AdditionalModificationEntity additionalModificationEntity = additionalModificationEntityRepository.findById(additionalModificationId).orElse(null);
        if (additionalModificationEntity == null) return false;
        else
            return AdditionalModificationStatus.AWAIT_APPROVE.equals(additionalModificationEntity.getAdditionalModificationStatus());
    }

    @Override
    public boolean isToUndoneAble(String additionalModificationId) {
        AdditionalModificationEntity additionalModificationEntity = additionalModificationEntityRepository.findById(additionalModificationId).orElse(null);
        if (additionalModificationEntity == null) return false;
        else
            return AdditionalModificationStatus.APPROVED.equals(additionalModificationEntity.getAdditionalModificationStatus());
    }

    @Override
    public String generateAMCode(String policyId, String additionalModificationId, ModificationType modificationType) {
        Optional<PolicyEntity> policyEntity = policyEntityRepository.findById(policyId);
        if (policyEntity.isEmpty()) return null;
        String policyCode = policyEntity.get().getPolicyCode();
        long aMCount = additionalModificationEntityRepository
                .countByPolicyIdAndModificationTypeIsAndAdditionalModificationStatusIs(policyId, modificationType, AdditionalModificationStatus.APPROVED);

        StringBuilder aMCode = new StringBuilder(policyCode).append("-");
        if (ModificationType.INTERNAL_MODIFICATION.equals(modificationType)) {
            do {
                aMCode.append(String.format("%03d", ++aMCount)).append("N");
                if (additionalModificationEntityRepository.existsByAdditionalModificationCode(aMCode.toString())) {
                    aMCode = new StringBuilder(policyCode)
                            .append("-")
                            .append(String.format("%03d", ++aMCount))
                            .append("N");
                }
                ;
            } while (additionalModificationEntityRepository.existsByAdditionalModificationCode(aMCode.toString()));
        } else {
            do {
                aMCode.append(String.format("%03d", ++aMCount));
                if (additionalModificationEntityRepository.existsByAdditionalModificationCode(aMCode.toString())) {
                    aMCode = new StringBuilder(policyCode)
                            .append("-")
                            .append(String.format("%03d", ++aMCount));
                }
                ;
            } while (additionalModificationEntityRepository.existsByAdditionalModificationCode(aMCode.toString()));
        }
        return aMCode.toString();
    }

    private String getYearSuffix() {
        return Integer.toString(LocalDate.now().getYear() % 100);
    }

}
