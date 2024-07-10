package org.example.customerquery.service;

import org.example.customerquery.request.CustomerPageRequest;
import org.example.sharedlibrary.base_response.WrapperResponse;

public interface CustomerEsService {
    WrapperResponse getPageCustomer(CustomerPageRequest request);

    WrapperResponse getCustomer(String customerId);

    WrapperResponse searchCustomers(String keyword);
}
