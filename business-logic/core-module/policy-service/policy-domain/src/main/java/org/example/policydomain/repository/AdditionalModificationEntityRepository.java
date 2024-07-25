package org.example.policydomain.repository;

import org.example.policydomain.entity.AdditionalModificationEntity;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface AdditionalModificationEntityRepository extends JpaRepository<AdditionalModificationEntity, String> {

    boolean existsByAdditionalModificationCode(String additionalModificationCode);

    List<AdditionalModificationEntity> findAllByPolicyIdAndAdditionalModificationStatusOrderByCreatedAtDesc(String policyId
            , AdditionalModificationStatus additionalModificationStatus);

    List<AdditionalModificationEntity> findAllByPolicyIdOrderByCreatedAtDesc(String policyId);

    long countByPolicyIdAndModificationTypeIsAndAdditionalModificationStatusIs(String policyId, ModificationType modificationType, AdditionalModificationStatus additionalModificationStatus);

    @Query(value = """
                    SELECT * FROM public.additional_modification_entity am
                    WHERE am.additional_modification_status = 'APPROVED'
                      AND am.created_at < (SELECT am.created_at
                                        FROM public.additional_modification_entity am
                                        WHERE id = :id)
                      AND am.modification_type =(SELECT am.modification_type
                                        FROM public.additional_modification_entity am
                                        WHERE id = :id)
                    ORDER BY am.created_at DESC
                    LIMIT 1;
            """, nativeQuery = true)
    Optional<AdditionalModificationEntity> findLastAMApprovedByAmId(@Param("id") String id);
}
