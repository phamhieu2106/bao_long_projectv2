package org.example.customercommand.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.customercommand.client.CustomerQueryClient;
import org.example.customercommand.handler.CustomerHandlerService;
import org.example.customercommand.service.CustomerCommandService;
import org.example.customerdomain.command.CustomerCreateCommand;
import org.example.customerdomain.command.CustomerDeleteCommand;
import org.example.customerdomain.command.CustomerUpdateCommand;
import org.example.sharedlibrary.base_response.WrapperResponse;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.utils.CustomerValidate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerCommandServiceImpl implements CustomerCommandService {

    private final CustomerHandlerService handlerService;
    private final CustomerQueryClient queryClient;

    @Override
    public WrapperResponse create(CustomerCreateCommand command) {
        try {
            if (isValidAddRequest(command)) {
                handlerService.handle(command);
                return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
            }
            return WrapperResponse.fail("Bad Request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return WrapperResponse.fail("Fail to Create Customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WrapperResponse update(CustomerUpdateCommand command) {
        try {
            if (isValidUpdateRequest(command.getCustomerId(), command)) {
                handlerService.handle(command);
                return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
            }
            return WrapperResponse.fail("Bad Request", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return WrapperResponse.fail("Fail to Update Customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public WrapperResponse delete(CustomerDeleteCommand command) {
        try {
            handlerService.handle(command);
            return WrapperResponse.success(HttpStatus.OK, HttpStatus.OK);
        } catch (Exception e) {
            return WrapperResponse.fail("Fail to Delete Customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private boolean isValidAddRequest(CustomerCreateCommand request) {
        if (request != null) {

            if (request.getGender() == null) {
                return false;
            }

            if (request.getPhoneNumber() == null
                    || request.getPhoneNumber().isBlank() || request.getPhoneNumber().isEmpty()
                    || CustomerValidate.isValidPhoneNumber(request.getPhoneNumber())
                    || this.queryClient.existsByPhoneNumber(request.getPhoneNumber())) {
                return false;
            }

            if (request.getEmail() == null
                    || request.getEmail().isBlank() || request.getEmail().isEmpty()
                    || CustomerValidate.isValidEmail(request.getEmail())
                    || this.queryClient.existsByEmail(request.getEmail())) {
                return false;
            }

//            if (request.getIdentityModel() == null
//                    || CustomerValidate.isValidProof(request.getIdentityModel())) {
//                return false;
//            }

            if (request.getJobName() == null || request.getJobName().isBlank()
                    || request.getJobName().isEmpty()) {
                return false;
            }

            if (request.getDateOfBirth() == null) {
                return false;
            }

            for (AddressModel addressModel : request.getAddressModels()) {
                if (CustomerValidate.isValidAddress(addressModel)) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    private boolean isValidUpdateRequest(String customerId, CustomerUpdateCommand request) {
        if (request != null) {
            if (request.getGender() == null) {
                return false;
            }

            if (request.getPhoneNumber() == null
                    || request.getPhoneNumber().isBlank() || request.getPhoneNumber().isEmpty()
                    || CustomerValidate.isValidPhoneNumber(request.getPhoneNumber())
                    || this.queryClient.existsByPhoneNumberAndIdIsNot(
                    request.getPhoneNumber(), customerId)) {
                return false;
            }

            if (request.getEmail() == null
                    || request.getEmail().isBlank() || request.getEmail().isEmpty()
                    || CustomerValidate.isValidEmail(request.getEmail())
                    || this.queryClient.existsByEmailAndIdIsNot(
                    request.getEmail(), customerId)) {
                return false;
            }


            if (request.getIdentityModel() == null
                    || CustomerValidate.isValidProof(request.getIdentityModel())) {
                return false;
            }

            if (request.getJobName() == null || request.getJobName().isBlank()
                    || request.getJobName().isEmpty()) {
                return false;
            }

            if (request.getDateOfBirth() == null) {
                return false;
            }

            for (AddressModel addressModel : request.getAddressModels()) {
                if (CustomerValidate.isValidAddress(addressModel)) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }
}
