package org.example.customerdomain.repository;

import org.example.customerdomain.entity.CustomerEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEventEntityRepository extends JpaRepository<CustomerEventEntity, String> {

    long countByAggregateId(String aggregateId);

    Optional<CustomerEventEntity> findFirstByAggregateId(String aggregateId);
    
}
