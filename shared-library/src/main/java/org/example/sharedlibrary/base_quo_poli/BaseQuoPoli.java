package org.example.sharedlibrary.base_quo_poli;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class BaseQuoPoli {

    //thong tin chung
    String quotationDistributionName;
    String quotationManagerName;
    String insuranceCompanyName;

    Date effectiveDate;
    Date maturityDate;

    CustomerModel customer;
    CustomerModel beneficiary;
    UserCreatedModel userCreatedModel;
}
