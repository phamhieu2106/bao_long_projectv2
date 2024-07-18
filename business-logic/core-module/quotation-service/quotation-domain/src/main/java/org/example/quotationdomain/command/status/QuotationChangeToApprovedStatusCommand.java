package org.example.quotationdomain.command.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.QuotationStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationChangeToApprovedStatusCommand extends BaseCommand {
    private final QuotationStatus quotationStatus = QuotationStatus.APPROVED;
    private String quotationId;
    private String approvedBy;
    private Date approvedAt;


}
