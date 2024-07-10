package org.example.userdomain.repository;

import org.example.userdomain.domain.UserEventStoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEventStoreRepository extends JpaRepository<UserEventStoreEntity, String> {
    long countByAggregateId(String aggregateId);

    Optional<UserEventStoreEntity> findFirstByAggregateId(String aggregateId);
}
