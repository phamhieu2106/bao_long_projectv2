package org.example.policydomain.repository;

import org.example.policydomain.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyEntityRepository extends JpaRepository<PolicyEntity, String> {
    boolean existsByQuotationCode(String s);
    
}
