package org.example.policyquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.policydomain.repository.PolicyEntityRepository;
import org.example.policyquery.service.PolicyInternalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PolicyInternalServiceImpl implements PolicyInternalService {

    private final PolicyEntityRepository policyEntityRepository;

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

    private String getYearSuffix() {
        return Integer.toString(LocalDate.now().getYear() % 100);
    }
}
