package org.example.quotationdomain.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
public class InsuranceDate {
    Date validInsuranceDate;
    Date voidInsuranceDate;
}
