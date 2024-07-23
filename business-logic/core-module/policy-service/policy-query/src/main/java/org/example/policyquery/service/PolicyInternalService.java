package org.example.policyquery.service;

import java.util.Date;

public interface PolicyInternalService {

    String getPolicyCode(String productCode);

    boolean isExitsById(String policyId);

    boolean isValidEffectDate(String policyId, Date effectiveDate);

    boolean isCreateAble(String policyId);
}
