package org.example.sharedlibrary.base_response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryResponse {
    String id;
    String aggregateType;
    String createdBy;
    String aggregateId;
    String timeStamp;
    Integer version;
}
