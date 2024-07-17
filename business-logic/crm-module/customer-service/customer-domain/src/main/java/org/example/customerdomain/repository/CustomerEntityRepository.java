package org.example.customerdomain.repository;

import org.example.customerdomain.entity.CustomerEntity;
import org.example.sharedlibrary.model.health.HealthIdentityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, String> {
    boolean existsByCustomerCode(String customerCode);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    boolean existsByIdentityModel(HealthIdentityModel identityModel);

    boolean existsByPhoneNumberAndIdIsNot(String phoneNumber, String id);

    boolean existsByEmailAndIdIsNot(String email, String id);

    @Query(value = """
            SELECT ce.id FROM customer_entity ce
            WHERE EXISTS (
                SELECT 1
                FROM unnest(ce."in_charge_by") AS elem_string
                WHERE (elem_string::jsonb ->> 'username') = :username
                AND (elem_string::jsonb ->> 'inChargeRole') = 'MAIN'
            ) """, nativeQuery = true)
    List<String> findAllCustomerIdWithUserUsername(@Param("username") String username);
}
