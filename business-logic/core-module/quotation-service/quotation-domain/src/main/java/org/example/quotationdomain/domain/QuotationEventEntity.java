package org.example.quotationdomain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.quotationdomain.converter.QuotationAggregateConverter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    private Date timeStamp;
    private String aggregateId;
    private String aggregateType;
    private Long version;
    @Convert(converter = QuotationAggregateConverter.class)
    @Column(length = 10000)
    private QuotationAggregate aggregateData;
    private String createdBy;

    public QuotationEventEntity(Date timeStamp, String aggregateId, String aggregateType, Long version,
                                QuotationAggregate aggregateData, String createdBy) {
        this.timeStamp = timeStamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.aggregateData = aggregateData;
        this.createdBy = createdBy;
    }
}
