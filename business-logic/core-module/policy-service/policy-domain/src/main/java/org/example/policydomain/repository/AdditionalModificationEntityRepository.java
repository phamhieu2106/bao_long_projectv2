package org.example.policydomain.repository;

import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdditionalModificationEntityRepository extends JpaRepository<AdditionalModificationEntity, String> {

    boolean existsByAdditionalModificationCode(String additionalModificationCode);

    List<AdditionalModificationEntity> findAllByPolicyIdAndAdditionalModificationStatusOrderByCreatedAtDesc(String policyId
            , AdditionalModificationStatus additionalModificationStatus);

    List<AdditionalModificationEntity> findAllByPolicyIdOrderByCreatedAtDesc(String policyId);

    long countByPolicyIdAndModificationTypeIsAndAdditionalModificationStatusIs(String policyId, ModificationType modificationType, AdditionalModificationStatus additionalModificationStatus);
}
