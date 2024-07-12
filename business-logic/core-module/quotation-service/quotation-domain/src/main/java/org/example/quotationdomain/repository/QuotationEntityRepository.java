package org.example.quotationdomain.repository;

import org.example.quotationdomain.domain.QuotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationEntityRepository extends JpaRepository<QuotationEntity, String> {
    boolean existsByQuotationCode(String quotationCode);

}
