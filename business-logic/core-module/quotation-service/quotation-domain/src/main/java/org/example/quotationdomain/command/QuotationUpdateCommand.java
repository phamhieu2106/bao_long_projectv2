package org.example.quotationdomain.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class QuotationUpdateCommand extends BaseCommand {
    String quotationId;
    List<Map<String, Object>> product;
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;
    Boolean isCoinsurance;
    String quotationDistributionName;
    String insuranceCompanyName;
    String customerId;
    String beneficiaryId;
    String createdBy;
}
