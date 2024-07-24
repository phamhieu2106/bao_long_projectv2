package org.example.policyquery.service;

import org.example.sharedlibrary.enumeration.additional_modification.ModificationType;

import java.util.Date;

public interface PolicyInternalService {

    String getPolicyCode(String productCode);

    boolean isExitsById(String policyId);

    boolean isValidEffectDate(String policyId, Date effectiveDate);

    boolean isCreateAble(String policyId);

    boolean isToAwaitApproveAble(String additionalModificationId);

    boolean isToRequireInformationAble(String additionalModificationId);

    boolean isToApprovedAble(String additionalModificationId);

    boolean isToRejectedAble(String additionalModificationId);

    boolean isToUndoneAble(String additionalModificationId);

    String generateAMCode(String policyId, String additionalModificationId, ModificationType modificationType);
}
