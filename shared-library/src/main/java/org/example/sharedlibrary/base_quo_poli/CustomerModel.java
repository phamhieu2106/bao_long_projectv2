package org.example.sharedlibrary.base_quo_poli;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerModel {
    String customerId;
    String customerName;
}
