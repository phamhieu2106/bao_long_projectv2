package org.example.quotationcommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.client.CustomerQueryClient;
import org.example.quotationcommand.client.QuotationQueryClient;
import org.example.quotationcommand.client.UserQueryClient;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationService;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationDeleteCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuotationServiceImpl implements QuotationService {

    private final CustomerQueryClient customerQueryClient;
    private final UserQueryClient userQueryClient;
    private final QuotationQueryClient quotationQueryClient;
    private final QuotationHandler handler;

    @Override
    public WrapperResponse create(QuotationCreateCommand command) {
        try {
            if (!customerQueryClient.exitsById(command.getCustomerId())) {
                return WrapperResponse.fail(
                        "Not Found Customer!", HttpStatus.NOT_FOUND
                );
            }
            if (!userQueryClient.isExitSByUsername(command.getCreatedBy())) {
                return WrapperResponse.fail(
                        "Not Found User!", HttpStatus.NOT_FOUND
                );
            }
            handler.handle(command);
            return WrapperResponse.success(
                    HttpStatus.OK, HttpStatus.OK
            );
        } catch (Exception e) {
            return WrapperResponse.fail(
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public WrapperResponse update(QuotationUpdateCommand command) {
        try {
            if (customerQueryClient.exitsById(command.getCustomerId())) {
                return WrapperResponse.fail(
                        "Not Found Customer!", HttpStatus.NOT_FOUND
                );
            }
            if (!quotationQueryClient.exitsById(command.getQuotationId())) {
                return WrapperResponse.fail(
                        "Not Found Quotation!", HttpStatus.NOT_FOUND
                );
            }
            if (userQueryClient.isExitSByUsername(command.getCreatedBy())) {
                return WrapperResponse.fail(
                        "Not Found User!", HttpStatus.NOT_FOUND
                );
            }
            handler.handle(command);
            return WrapperResponse.success(
                    HttpStatus.OK, HttpStatus.OK
            );
        } catch (Exception e) {
            return WrapperResponse.fail(
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public WrapperResponse delete(QuotationDeleteCommand command) {
        return null;
    }
}
