package org.example.quotationdomain.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuotationScheduleStatusEvent {
    List<String> idsQuotation;
    String createdBy;
}
