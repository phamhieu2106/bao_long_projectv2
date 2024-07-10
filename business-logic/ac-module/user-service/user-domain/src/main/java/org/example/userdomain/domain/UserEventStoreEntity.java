package org.example.userdomain.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.userdomain.aggregate.UserAggregate;
import org.example.userdomain.converter.UserAggregateConverter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEventStoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    private Date timeStamp;
    private String aggregateId;
    private String aggregateType;
    private Long version;
    @Convert(converter = UserAggregateConverter.class)
    @Column(length = 1000)
    private UserAggregate aggregateData;
    private String createdBy;

    public UserEventStoreEntity(Date timeStamp, String aggregateId, String aggregateType, Long version,
                                UserAggregate aggregateData, String createdBy) {
        this.timeStamp = timeStamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.aggregateData = aggregateData;
        this.createdBy = createdBy;
    }
}
