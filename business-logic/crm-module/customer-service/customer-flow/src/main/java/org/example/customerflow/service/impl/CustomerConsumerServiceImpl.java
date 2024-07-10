package org.example.customerflow.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customerdomain.entity.CustomerEntity;
import org.example.customerdomain.enumeration.CustomerType;
import org.example.customerdomain.enumeration.StatusCustomer;
import org.example.customerdomain.event.CustomerCreateEvent;
import org.example.customerdomain.event.CustomerDeleteEvent;
import org.example.customerdomain.event.CustomerUpdateEvent;
import org.example.customerdomain.repository.CustomerEntityRepository;
import org.example.customerdomain.repository.CustomerViewMap;
import org.example.customerdomain.view.CustomerView;
import org.example.customerflow.service.CustomerConsumerService;
import org.example.sharedlibrary.base_constant.ViewConstant;
import org.example.sharedlibrary.enumeration.Gender;
import org.example.sharedlibrary.model.AddressModel;
import org.example.sharedlibrary.model.health.HealthIdentityModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerConsumerServiceImpl implements CustomerConsumerService {

    private final CustomerEntityRepository entityRepository;
    private final ElasticsearchClient client;
    private final CustomerViewMap viewMap;

    @Override
    @KafkaListener(topics = "customer_create", groupId = "customer_group")
    public void subscribe(CustomerCreateEvent event) {
        CustomerEntity customerEntity = saveCustomer(event.getId(), event.getCustomerCode(), event.getCustomerName(),
                event.getGender(), event.getPhoneNumber(), event.getEmail(),
                event.getDateOfBirth(), event.getAddressModels(), event.getJobName(),
                event.getIdentityModel(), event.getStatusCustomer(), event.getCustomerType(),
                event.getInChargeBy(), event.getIsDeleted(), event.getTimestamp(), event.getCreatedBy());

        indexCustomerDoc(customerEntity);
    }

    @Override
    @KafkaListener(topics = "customer_update", groupId = "customer_group")
    public void subscribe(CustomerUpdateEvent event) {

        Optional<CustomerEntity> optional = entityRepository.findById(event.getId());
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Not Found");
        }

        CustomerEntity customerEntity = saveCustomer(event.getId(), event.getCustomerCode(), event.getCustomerName(),
                event.getGender(), event.getPhoneNumber(), event.getEmail(),
                event.getDateOfBirth(), event.getAddressModels(), event.getJobName(),
                event.getIdentityModel(), event.getStatusCustomer(), event.getCustomerType(),
                event.getInChargeBy(), event.getIsDeleted(), event.getTimestamp(), event.getCreatedBy());

        indexCustomerDoc(customerEntity);
    }

    @Override
    @KafkaListener(topics = "customer_delete", groupId = "customer_group")
    public void subscribe(CustomerDeleteEvent event) {

        Optional<CustomerEntity> optional = entityRepository.findById(event.getCustomerId());
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Not Found");
        }

        CustomerEntity customerEntity = optional.get();
        customerEntity.setIsDeleted(event.getIsDelete());
        entityRepository.save(customerEntity);

        DeleteRequest.Builder request = new DeleteRequest.Builder()
                .index(ViewConstant.CUSTOMER_INDEX)
                .id(customerEntity.getId());

        try {
            client.delete(request.build());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CustomerEntity saveCustomer(String id, String customerCode,
                                        String customerName, Gender gender,
                                        String phoneNumber, String email,
                                        Date dateOfBirth, List<AddressModel> addressModels,
                                        String jobName, HealthIdentityModel identityModel,
                                        StatusCustomer statusCustomer, CustomerType customerType,
                                        String inChargeBy, Boolean isDeleted, Date timestamp,
                                        String createdBy) {
        return entityRepository.save(CustomerEntity.builder()
                .id(id)
                .customerCode(customerCode)
                .customerName(customerName)
                .gender(gender)
                .phoneNumber(phoneNumber)
                .email(email)
                .dateOfBirth(dateOfBirth)
                .addressModels(addressModels)
                .jobName(jobName)
                .identityModel(identityModel)
                .statusCustomer(statusCustomer)
                .customerType(customerType)
                .inChargeBy(inChargeBy)
                .isDeleted(isDeleted)
                .createdAt(timestamp)
                .createdBy(createdBy)
                .build());
    }

    private void indexCustomerDoc(CustomerEntity customerEntity) {
        CustomerView customerView = CustomerView.builder()
                .id(customerEntity.getId())
                .customerCode(customerEntity.getCustomerCode())
                .statusCustomer(customerEntity.getStatusCustomer())
                .customerName(customerEntity.getCustomerName())
                .customerNameKeyword(customerEntity.getCustomerName())
                .email(customerEntity.getEmail())
                .phoneNumber(customerEntity.getPhoneNumber())
                .inChargeBy(customerEntity.getInChargeBy())
                .build();

        viewMap.save(customerView);

//            client.index(i -> i
//                    .index(ViewConstant.CUSTOMER_INDEX)
//                    .id(customerView.getId())
//                    .document(customerView));

    }
}
