package org.example.sharedlibrary.base_quo_poli;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerModel {
    String customerId;
    String customerName;
}
