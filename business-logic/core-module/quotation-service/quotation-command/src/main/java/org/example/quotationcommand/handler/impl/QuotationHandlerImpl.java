package org.example.quotationcommand.handler.impl;

import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.client.QuotationQueryClient;
import org.example.quotationcommand.handler.QuotationHandler;
import org.example.quotationcommand.service.QuotationEventStoreService;
import org.example.quotationcommand.service.QuotationProducerService;
import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.quotationdomain.command.QuotationCreateCommand;
import org.example.quotationdomain.command.QuotationUpdateCommand;
import org.example.quotationdomain.domain.model.InsuranceDate;
import org.example.quotationdomain.event.QuotationCreateEvent;
import org.example.quotationdomain.event.QuotationUpdateEvent;
import org.example.sharedlibrary.enumeration.QuotationStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class QuotationHandlerImpl implements QuotationHandler {

    private final QuotationEventStoreService storeService;
    private final QuotationProducerService producerService;
    private final QuotationQueryClient quotationQueryClient;

    @Override
    public void handle(QuotationCreateCommand command) {
        QuotationAggregate aggregate = new QuotationAggregate();

        InsuranceDate date = getQuotationValidAndVoidDate(command.getProduct());
        aggregate.setQuotationCode(quotationQueryClient.getQuotationCode(command.getProductCode()));
        aggregate.setEffectiveDate(date.getValidInsuranceDate());
        aggregate.setMaturityDate(date.getVoidInsuranceDate());
        aggregate.setQuotationStatus(QuotationStatus.DRAFTING);

        QuotationCreateEvent event = aggregate.apply(command);
        storeService.save(aggregate, event);
        producerService.publish("quotation_create", event);
    }

    @Override
    public void handle(QuotationUpdateCommand command) {
        QuotationAggregate aggregate = storeService.load(command.getQuotationId());

        InsuranceDate date = getQuotationValidAndVoidDate(command.getProduct());
        aggregate.setEffectiveDate(date.getValidInsuranceDate());
        aggregate.setMaturityDate(date.getVoidInsuranceDate());
        aggregate.setQuotationStatus(QuotationStatus.DRAFTING);


        QuotationUpdateEvent event = aggregate.apply(command);
        storeService.save(aggregate, event);
        producerService.publish("quotation_update", event);
    }

    private InsuranceDate getQuotationValidAndVoidDate(Object productListObj) {
        List<InsuranceDate> dateList = new ArrayList<>();

        Optional.ofNullable(productListObj)
                .filter(List.class::isInstance).map(List.class::cast)
                .filter(list -> !list.isEmpty())
                .map(list -> (Map<?, ?>) list.get(0))
                .map(product -> (List<?>) product.get("listInsuranceModel"))
                .ifPresent(listInsuranceModel -> {
                    listInsuranceModel.stream()
                            .filter(Map.class::isInstance).map(Map.class::cast)
                            .forEach(insuranceModel -> {
                                String validDateString = (String) insuranceModel.get("validInsuranceDate");
                                String voidDateString = (String) insuranceModel.get("voidInsuranceDate");

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                try {
                                    Date validDate = dateFormat.parse(validDateString);
                                    Date voidDate = dateFormat.parse(voidDateString);
                                    dateList.add(new InsuranceDate(validDate, voidDate));
                                } catch (ParseException e) {
                                    throw new RuntimeException("Error parsing date");
                                }
                            });
                });

        return dateList.stream()
                .reduce((first, second) -> {
                    Date startDate = first.getValidInsuranceDate().before(second.getValidInsuranceDate())
                            ? first.getValidInsuranceDate() : second.getValidInsuranceDate();
                    Date endDate = first.getVoidInsuranceDate().after(second.getVoidInsuranceDate())
                            ? first.getVoidInsuranceDate() : second.getVoidInsuranceDate();
                    return new InsuranceDate(startDate, endDate);
                })
                .orElseThrow(() -> new RuntimeException("No valid dates found"));
    }
}
