package org.example.quotationdomain.command;

import lombok.Getter;
import lombok.Setter;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.QuotationStatus;

@Getter
@Setter
public class QuotationChangeStatusCommand extends BaseCommand {
    String id;
    QuotationStatus quotationStatus;
    String createdBy;
}
