package org.example.quotationdomain.repository;

import org.example.quotationdomain.domain.QuotationEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuotationEventStoreRepository extends JpaRepository<QuotationEventEntity, String> {

    long countByAggregateId(String aggregateId);

    Optional<QuotationEventEntity> findFirstByAggregateIdOrderByVersionDesc(String aggregateId);

}
