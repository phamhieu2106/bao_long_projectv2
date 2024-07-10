package org.example.quotationdomain.command;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.ProductType;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class QuotationCreateCommand extends BaseCommand {
    ProductType productType;
    String productCode;
    String productName;
    List<Map<String, Object>> product;
    List<Map<String, Object>> insuranceTypeModel;
    Double totalFeeAfterTax;
    String customerId;
    String beneficiaryId;
    Boolean isCoinsurance;
    String quotationDistributionName;
    String quotationManagerName;
    String insuranceCompanyName;
    String currency;
    Double rate;
    String createdBy;
    String approveBy;
}
