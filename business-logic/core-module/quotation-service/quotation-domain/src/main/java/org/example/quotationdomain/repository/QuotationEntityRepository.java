package org.example.quotationdomain.repository;

import org.example.quotationdomain.domain.QuotationEntity;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationEntityRepository extends JpaRepository<QuotationEntity, String> {
    boolean existsByQuotationCode(String quotationCode);

    long countByQuotationCode(String quotationCode);
    
    @Query(value = """
            SELECT q.id
            FROM quotation_entity q
            WHERE q.quotation_status = 'APPROVED'
            AND q.policy_code is null
            AND q.approved_at < NOW() - INTERVAL '1 day'
            """, nativeQuery = true)
    List<String> findIdsByQuotationStatus(@Param("quotationStatus") QuotationStatus quotationStatus,
                                          @Param("day") int day);

    @Query(value = """
            SELECT q.id
            FROM quotation_entity q
            WHERE (q.customer::jsonb ->> 'customerId') = :customerId
            AND q.quotation_status = 'DRAFTING'
            OR q.quotation_status = 'AWAIT_APPROVE'
            OR q.quotation_status = 'REQUIRE_INFORMATION'
            """, nativeQuery = true)
    List<String> findIdsByCustomerId(@Param("customerId") String customerId);

    @Query(value = """
            SELECT q.id FROM quotation_entity q
            WHERE (q.customer::jsonb ->> 'customerId') = :customerId
            AND (q.quotation_status = 'AWAIT_APPROVE'
            OR q.quotation_status = 'DRAFTING'
            OR q.quotation_status = 'REQUIRE_INFORMATION')
            """, nativeQuery = true)
    List<String> findAllQuotationIdsNotApproveByCustomerId(@Param("customerId") String customerId);
}
