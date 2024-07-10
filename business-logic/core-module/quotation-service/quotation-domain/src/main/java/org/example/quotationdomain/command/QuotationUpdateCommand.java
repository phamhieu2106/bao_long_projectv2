package org.example.quotationdomain.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseCommand;
import org.example.sharedlibrary.enumeration.ProductType;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationUpdateCommand extends BaseCommand {
    String quotationId;
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
    String insuranceCompanyName;
    String currency;
    Double rate;
    String createdBy;
    String approveBy;
}
