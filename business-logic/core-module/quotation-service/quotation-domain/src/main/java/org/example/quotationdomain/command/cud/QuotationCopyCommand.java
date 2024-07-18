package org.example.quotationdomain.command.cud;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationCopyCommand {
    String id;
    String createdBy;

    public QuotationCopyCommand(String quotationId, String username) {
        this.id = quotationId;
        this.createdBy = username;
    }
}
