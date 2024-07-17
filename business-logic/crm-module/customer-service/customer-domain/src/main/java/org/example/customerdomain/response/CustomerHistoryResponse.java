package org.example.customerdomain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sharedlibrary.base_response.HistoryResponse;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "customers_history")
public class CustomerHistoryResponse extends HistoryResponse {

    public CustomerHistoryResponse(String id, String aggregateType, String createdBy, String aggregateId, String timeStamp, Integer version) {
        super(id, aggregateType, createdBy, aggregateId, timeStamp, version);
    }
}
