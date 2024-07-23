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
import org.example.policydomain.aggregate.PolicyAggregate;
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
public class PolicyEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    private Date timeStamp;
    private String aggregateId;
    private String aggregateType;
    private Long version;
    @Column(columnDefinition = ColumnConstant.JSONB_TYPE)
    @JdbcTypeCode(SqlTypes.JSON)
    private PolicyAggregate aggregateData;
    private String createdBy;

    public PolicyEventEntity(Date timeStamp, String aggregateId, String aggregateType, Long version,
                             PolicyAggregate aggregateData, String createdBy) {
        this.timeStamp = timeStamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.aggregateData = aggregateData;
        this.createdBy = createdBy;
    }
}
