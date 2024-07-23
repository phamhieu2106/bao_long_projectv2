package org.example.policydomain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.aggregate.AdditionalModificationAggregate;
import org.example.sharedlibrary.base_constant.ColumnConstant;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Immutable
public class AdditionalModificationEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Date timeStamp;
    String aggregateId;
    String aggregateType;
    Long version;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    AdditionalModificationAggregate aggregateData;
    String createdBy;
    boolean isSuccess;

    public AdditionalModificationEventEntity(Date timeStamp, String aggregateId, String aggregateType, Long version,
                                             AdditionalModificationAggregate aggregateData, String createdBy) {
        this.timeStamp = timeStamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.aggregateData = aggregateData;
        this.createdBy = createdBy;
    }
}
