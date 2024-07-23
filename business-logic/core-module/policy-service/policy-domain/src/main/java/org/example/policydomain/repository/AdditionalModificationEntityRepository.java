package org.example.policydomain.repository;

import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdditionalModificationEntityRepository extends JpaRepository<AdditionalModificationEntity, String> {

    List<AdditionalModificationEntity> findAllByPolicyIdAndAdditionalModificationStatusOrderByCreatedAtDesc(String policyId
            , AdditionalModificationStatus additionalModificationStatus);
}
