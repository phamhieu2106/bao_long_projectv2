package org.example.quotationquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.example.quotationquery.service.QuotationService;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final QuotationEntityRepository quotationEntityRepository;

    @Override
    public String getQuotationCode(String productCode) {
        StringBuilder code = new StringBuilder("Q-");
        long count = quotationEntityRepository.count() + 1;
        do {
            code.append(productCode);
            code.append(getYearSuffix());
            code.append(String.format("%06d", count));
            if (quotationEntityRepository.existsByQuotationCode(String.valueOf(code))) {
                code = new StringBuilder("Q-")
                        .append(productCode)
                        .append(getYearSuffix())
                        .append(String.format("%06d", ++count));
            }
        } while (quotationEntityRepository.existsByQuotationCode(String.valueOf(code)));
        return String.valueOf(code);
    }


    @Override
    public boolean isCreateNewVersionAble(String quotationId) {
        QuotationEntity quotationEntity = quotationEntityRepository.findById(quotationId).orElse(null);
        if (quotationEntity == null) {
            return false;
        }

        List<QuotationEntity> quotationEntities = quotationEntityRepository.getAllByQuotationCode(quotationEntity.getQuotationCode());
        if (!quotationEntities.isEmpty()) {
            try {
                quotationEntities.forEach(q -> {
                            if (QuotationStatus.APPROVED.equals(quotationEntity.getQuotationStatus())
                                    && quotationEntity.getPolicyCode() != null) {
                                throw new RuntimeException();
                            }
                        }
                );
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public QuotationEntity getQuotationByQuotationId(String quotationId) {
        return quotationEntityRepository.findById(quotationId).orElse(null);
    }

    @Override
    public boolean exitsById(@PathVariable String quotationId) {
        return quotationEntityRepository.existsById(quotationId);
    }

    @Override
    public List<String> findAllIdsByQuotationStatus(QuotationScheduleStatusCommand command) {
        return quotationEntityRepository.findIdsByQuotationStatus(command.getQuotationStatus(), command.getNumberOfDaysExpired());
    }

    @Override
    public List<String> findIdsByCustomerId(String customerId) {
        return quotationEntityRepository.findIdsByCustomerId(customerId);
    }

    @Override
    public boolean exitsByUserModel(UserModel userModel, String quotationId) {
        QuotationEntity quotationEntity = quotationEntityRepository.findById(quotationId).orElse(null);
        if (quotationEntity == null) {
            return true;
        }
        for (UserModel user : quotationEntity.getUserModels()) {
            if (user.getUsername().equals(userModel.getUsername())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> findAllQuotationsNotApproveByCustomerId(String customerId) {
        return quotationEntityRepository.findAllQuotationIdsNotApproveByCustomerId(customerId);
    }

    @Override
    public boolean isApproved(String quotationId) {
        QuotationEntity quotationEntity = quotationEntityRepository.findById(quotationId).orElse(null);
        if (quotationEntity == null) {
            return false;
        }
        return QuotationStatus.APPROVED.equals(quotationEntity.getQuotationStatus());
    }

    @Override
    public int getQuotationVersion(String quotationCode) {
        return (int) (quotationEntityRepository.countByQuotationCode(quotationCode) + 1);
    }

    @Override
    public List<String> getAllQuotationIdsOtherVersionNotApproved(String quotationCode, String quotationId) {
        return quotationEntityRepository.getAllByQuotationCodeAndIdNot(quotationCode, quotationId).stream().map(QuotationEntity::getId).toList();
    }

    private String getYearSuffix() {
        return Integer.toString(LocalDate.now().getYear() % 100);
    }
}
