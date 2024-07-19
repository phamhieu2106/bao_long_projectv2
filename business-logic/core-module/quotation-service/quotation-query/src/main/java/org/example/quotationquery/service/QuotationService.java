package org.example.quotationquery.service;

import org.example.quotationdomain.command.QuotationScheduleStatusCommand;
import org.example.quotationdomain.domain.QuotationEntity;
import org.example.sharedlibrary.model.UserModel;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface QuotationService {

    String getQuotationCode(String productCode);

    boolean exitsById(@PathVariable String quotationId);

    boolean exitsByUserModel(UserModel userModel, String quotationId);

    List<String> findAllIdsByQuotationStatus(QuotationScheduleStatusCommand command);

    List<String> findIdsByCustomerId(String customerId);

    List<String> findAllQuotationsNotApproveByCustomerId(String customerId);

    QuotationEntity getQuotationByQuotationId(String quotationId);

    boolean isApproved(String quotationId);

    boolean isCreateNewVersionAble(String quotationId);

    int getQuotationVersion(String quotationCode);
}
