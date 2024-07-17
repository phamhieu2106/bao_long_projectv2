package org.example.quotationdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.QuotationStatus;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationChangeStatusCommand extends BaseCommand {
    String id;
    QuotationStatus quotationStatus;
    String createdBy;
}
