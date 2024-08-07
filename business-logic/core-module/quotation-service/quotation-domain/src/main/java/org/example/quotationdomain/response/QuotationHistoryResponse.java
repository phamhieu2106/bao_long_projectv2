package org.example.quotationdomain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sharedlibrary.base_response.HistoryResponse;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "quotations_history")
public class QuotationHistoryResponse extends HistoryResponse {

    public QuotationHistoryResponse(String id, String aggregateType, String createdBy, String aggregateId, String timeStamp, Integer version) {
        super(id, aggregateType, createdBy, aggregateId, timeStamp, version);
    }
}
