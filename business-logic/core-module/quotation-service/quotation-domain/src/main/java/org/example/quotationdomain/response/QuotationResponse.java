package org.example.quotationdomain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.quotationdomain.domain.QuotationEntity;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class QuotationResponse {
    QuotationEntity quotationEntity;
    List<QuotationHistoryResponse> historyResponses;
}
