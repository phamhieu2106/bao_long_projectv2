package org.example.quotationquery.service;

import org.example.sharedlibrary.model.UserModel;
import org.springframework.web.bind.annotation.PathVariable;

public interface QuotationService {

    String getQuotationCode(String productCode);

    boolean exitsById(@PathVariable String quotationId);

    boolean exitsByUserModel(UserModel userModel, String quotationId);
}
