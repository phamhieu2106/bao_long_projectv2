package org.example.customerquery.service;

import org.example.sharedlibrary.base_quo_poli.CustomerModel;

import java.util.List;

public interface CustomerQueryService {

    String generateCode();

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumberAndIdIsNot(String phoneNumber, String id);

    boolean existsByEmailAndIdIsNot(String email, String id);

    boolean exitsById(String customerId);

    CustomerModel getCustomerModelById(String customerId);

    List<String> findAllCustomerIdWithUserUsername(String username);
}
