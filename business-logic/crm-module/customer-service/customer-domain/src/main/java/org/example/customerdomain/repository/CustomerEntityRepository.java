package org.example.customerdomain.repository;

import org.example.customerdomain.entity.CustomerEntity;
import org.example.sharedlibrary.model.health.HealthIdentityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
    boolean existsByCustomerCode(String customerCode);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByIdentityModel(HealthIdentityModel identityModel);

    boolean existsByPhoneNumberAndIdIsNot(String phoneNumber, String id);

    boolean existsByEmailAndIdIsNot(String email, String id);
}
