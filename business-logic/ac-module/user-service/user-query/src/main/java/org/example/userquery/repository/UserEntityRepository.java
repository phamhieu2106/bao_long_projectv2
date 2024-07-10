package org.example.userquery.repository;

import org.example.userdomain.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    boolean existsByUserCode(String code);

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
