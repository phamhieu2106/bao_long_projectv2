package org.example.quotationquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.quotationdomain.repository.QuotationEntityRepository;
import org.example.quotationquery.service.QuotationService;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;

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
    public boolean exitsById(@PathVariable String quotationId) {
        return quotationEntityRepository.existsById(quotationId);
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

    private String getYearSuffix() {
        return Integer.toString(LocalDate.now().getYear() % 100);
    }
}
