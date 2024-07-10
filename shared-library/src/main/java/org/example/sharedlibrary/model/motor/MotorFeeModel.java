package org.example.sharedlibrary.model.motor;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MotorFeeModel {
    Date paymentTerm;
    Double taxPrice;
    String extraInformation;
    Double sTBMtn;
    Double rateAfterTax;
    Double feeAfterTax;
    Double feeReductionRate;
    Double ratePaymentAfterTax;
    Double feePaymentAfterTax;
    Double deductible;
}
