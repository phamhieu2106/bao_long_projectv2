package org.example.sharedlibrary.model.health;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HealthInsuranceTypeModel {
    Double amountResponsibility;
    Double feeRate;
    Double fee;
    Double feeReductionRate;
    Double ratePayment;
    Double feePayment;
}
