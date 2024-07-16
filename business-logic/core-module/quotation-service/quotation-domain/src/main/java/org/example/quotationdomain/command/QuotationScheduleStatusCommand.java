package org.example.quotationdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.enumeration.QuotationStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationScheduleStatusCommand {

    final QuotationStatus quotationStatus = QuotationStatus.APPROVED;

    final Integer numberOfDaysExpired = 1;
}
