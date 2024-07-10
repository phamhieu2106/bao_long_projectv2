package org.example.userflow.repository;

import org.example.userdomain.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserConsumerRepository extends JpaRepository<UserEntity, String> {
}
