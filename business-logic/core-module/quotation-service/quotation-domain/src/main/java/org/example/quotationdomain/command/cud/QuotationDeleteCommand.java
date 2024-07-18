package org.example.quotationdomain.command.cud;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationDeleteCommand extends BaseCommand {
    String quotationId;
    String createdBy;
}
