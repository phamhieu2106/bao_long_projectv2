package org.example.policydomain.repository;

import org.example.policydomain.entity.AdditionalModificationEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalModificationEventEntityRepository extends JpaRepository<AdditionalModificationEventEntity, String> {
    long countByAggregateId(String aggregateId);
}
