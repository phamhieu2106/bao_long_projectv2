package org.example.policydomain.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.policydomain.entity.PolicyEntity;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PolicyResponse {

    PolicyEntity policyEntity;
    List<PolicyHistoryResponse> policyHistoryResponse;

}
