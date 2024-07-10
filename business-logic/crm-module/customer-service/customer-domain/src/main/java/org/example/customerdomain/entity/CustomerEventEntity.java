package org.example.customerdomain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.aggregate.CustomerAggregate;
import org.example.customerdomain.converter.CustomerAggregateConverter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    Date timeStamp;
    String aggregateId;
    String aggregateType;
    Long version;
    @Column(length = 1000)
    @Convert(converter = CustomerAggregateConverter.class)
    CustomerAggregate aggregateData;
    String createdBy;
}
