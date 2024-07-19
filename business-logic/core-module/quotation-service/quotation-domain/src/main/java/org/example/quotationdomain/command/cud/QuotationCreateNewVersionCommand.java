package org.example.quotationdomain.command.cud;

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
public class QuotationCreateNewVersionCommand extends BaseCommand {
    String quotationId;
    List<Map<String, Object>> product;
    List<Map<String, Object>> insuranceTypeModel;
    String customerId;
    String beneficiaryId;
    String createdBy;
}
