package org.example.quotationdomain.command.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.QuotationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationChangeToRejectedStatusCommand extends BaseCommand {
    private final QuotationStatus quotationStatus = QuotationStatus.REJECTED;
    private String quotationId;
    private String createdBy;
}
