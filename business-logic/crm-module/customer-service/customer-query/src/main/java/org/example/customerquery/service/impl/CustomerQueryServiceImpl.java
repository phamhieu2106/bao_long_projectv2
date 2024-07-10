package org.example.customerquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customerdomain.repository.CustomerEntityRepository;
import org.example.customerquery.service.CustomerQueryService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerEntityRepository customerEntityRepository;


    @Override
    public String generateCode() {
        String code;
        long count = customerEntityRepository.count();
        do {
            code = String.format("C%03d", ++count);
            if (customerEntityRepository.existsByCustomerCode(code)) {
                code = String.format("C%03d", ++count);
            }
        } while (customerEntityRepository.existsByCustomerCode(code));
        return code;
    }

    @Override
    public boolean exitsById(String customerId) {
        return customerEntityRepository.existsById(customerId);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return customerEntityRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerEntityRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumberAndIdIsNot(String phoneNumber, String id) {
        return customerEntityRepository.existsByPhoneNumberAndIdIsNot(phoneNumber, id);
    }

    @Override
    public boolean existsByEmailAndIdIsNot(String email, String id) {
        return customerEntityRepository.existsByEmailAndIdIsNot(email, id);
    }
}
