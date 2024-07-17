package org.example.quotationcommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.client.CustomerQueryClient;
import org.example.quotationcommand.client.QuotationQueryClient;
import org.example.quotationcommand.client.UserQueryClient;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationService;
import org.example.quotationdomain.command.*;
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
            if (command.getProduct() == null) {
                return WrapperResponse.fail(
                        "At least 1 product!", HttpStatus.BAD_REQUEST
                );
            }
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
    public WrapperResponse copy(QuotationCopyCommand quotationCopyCommand) {
        try {
            if (!quotationQueryClient.exitsById(quotationCopyCommand.getId())) {
                return WrapperResponse.fail(
                        "Not Found Quotation!", HttpStatus.NOT_FOUND
                );
            }
            handler.handle(quotationCopyCommand);
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
            if (command.getProduct() == null) {
                return WrapperResponse.fail(
                        "At least 1 product!", HttpStatus.BAD_REQUEST
                );
            }
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
    public WrapperResponse changeStatus(QuotationChangeStatusCommand command) {
        try {
            if (!quotationQueryClient.exitsById(command.getId())) {
                return WrapperResponse.fail(
                        "Not Found Quotation!", HttpStatus.NOT_FOUND
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
    public WrapperResponse policyRelease(String quotationId) {
        if (!quotationQueryClient.exitsById(quotationId)) {
            return WrapperResponse.fail(
                    "Not Found Quotation!", HttpStatus.NOT_FOUND
            );
        }
        if (!quotationQueryClient.isApproved(quotationId)) {
            return WrapperResponse.fail(
                    "QuotationStatus Not Approved!", HttpStatus.BAD_REQUEST
            );
        }

        handler.releasePolicy(quotationId);
        return WrapperResponse.success(
                HttpStatus.OK, HttpStatus.OK
        );
    }

    @Override
    public WrapperResponse delete(QuotationDeleteCommand command) {
        return null;
    }
}
