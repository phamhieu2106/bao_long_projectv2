package org.example.policydomain.repository;

import org.example.policydomain.entity.PolicyEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyEventEntityRepository extends JpaRepository<PolicyEventEntity, String> {
    long countByAggregateId(String aggregateId);
}
