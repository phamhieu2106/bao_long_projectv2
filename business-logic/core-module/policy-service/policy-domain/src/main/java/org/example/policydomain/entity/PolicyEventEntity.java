package org.example.policydomain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.aggregate.PolicyAggregate;
import org.example.policydomain.converter.QuotationAggregateConverter;
import org.hibernate.annotations.Immutable;

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
    @Convert(converter = QuotationAggregateConverter.class)
    @Column(length = 10000)
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
