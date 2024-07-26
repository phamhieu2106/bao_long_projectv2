package org.example.policydomain.event.policy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.sharedlibrary.base_class.BaseEvent;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PolicyUpdateInternalAMEvent extends BaseEvent {
    String policyId;
    List<Map<String, Object>> product;
    String quotationDistributionName;
    String insuranceCompanyName;

    public PolicyUpdateInternalAMEvent(Date timestamp, String createdBy, String policyId, List<Map<String, Object>> product, String quotationDistributionName, String insuranceCompanyName) {
        super(timestamp, createdBy);
        this.policyId = policyId;
        this.product = product;
        this.quotationDistributionName = quotationDistributionName;
        this.insuranceCompanyName = insuranceCompanyName;
    }
}
