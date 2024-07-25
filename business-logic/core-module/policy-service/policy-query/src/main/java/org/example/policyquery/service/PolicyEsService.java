package org.example.policyquery.service;

import org.example.policyquery.request.AdditionalModificationPageRequest;
import org.example.policyquery.request.PolicyPageRequest;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface PolicyEsService {

    WrapperResponse getPolicyPage(PolicyPageRequest request);

    WrapperResponse getPolicyDetail(String policyId);

    WrapperResponse getAdditionalModificationPage(AdditionalModificationPageRequest request);

    WrapperResponse getAdditionalModificationDetail(String additionalModificationId);
}
