package org.example.policydomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_constant.ColumnConstant;
import org.example.sharedlibrary.enumeration.additional_modification.AdditionalModificationStatus;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;
import org.example.sharedlibrary.enumeration.additional_modification.ModificationTypeName;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalModificationEntity {
    @Id
    String id;
    String additionalModificationCode;
    ModificationType modificationType;
    ModificationTypeName modificationTypeName;
    Date effectiveDate;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    List<Map<String, Object>> additionalData;
    String policyId;
    @Enumerated(EnumType.STRING)
    AdditionalModificationStatus additionalModificationStatus;
    Date createdAt;
    String createdBy;

    String modifiedBy;
    Date modifiedAt;

    String approvedBy;
    Date approvedAt;
}
