package org.example.customerquery.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customerdomain.entity.CustomerEntity;
import org.example.customerdomain.repository.CustomerEntityRepository;
import org.example.customerquery.service.CustomerQueryService;
import org.example.sharedlibrary.base_quo_poli.CustomerModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public CustomerModel getCustomerModelById(String customerId) {
        Optional<CustomerEntity> optional = customerEntityRepository.findById(customerId);
        if (optional.isEmpty()) {
            return null;
        }
        CustomerEntity customerEntity = optional.get();
        return new CustomerModel(customerEntity.getId(), customerEntity.getCustomerName());
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

    @Override
    public List<String> findAllCustomerIdWithUserUsername(String username) {
        return customerEntityRepository.findAllCustomerIdWithUserUsername(username);
    }
}
