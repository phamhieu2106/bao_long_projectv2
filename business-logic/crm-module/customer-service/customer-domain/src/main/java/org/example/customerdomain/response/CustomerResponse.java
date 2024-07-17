package org.example.customerdomain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.customerdomain.entity.CustomerEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    CustomerEntity customerEntity;
    List<CustomerHistoryResponse> historyResponses;
}
