package org.example.quotationcommand.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.quotationcommand.service.QuotationEventStoreService;
import org.example.quotationdomain.aggregate.QuotationAggregate;
import org.example.quotationdomain.domain.QuotationEventEntity;
import org.example.quotationdomain.repository.QuotationEventStoreRepository;
import org.example.sharedlibrary.base_class.BaseEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class QuotationEventStoreServiceImpl implements QuotationEventStoreService {

    private final QuotationEventStoreRepository repository;

    @Override
    public void save(QuotationAggregate aggregate, BaseEvent event) {
        repository.save(new QuotationEventEntity(
                new Date(),
                aggregate.getId(),
                event.getClass().getSimpleName(),
                getEventVersion(aggregate.getId()),
                aggregate,
                aggregate.getUserCreatedModel().getUsername()
        ));
    }

    @Override
    public QuotationAggregate load(String id) {
        QuotationEventEntity eventStoreEntity = repository.findFirstByAggregateId(id).orElse(null);
        if (eventStoreEntity == null) {
            throw new EntityNotFoundException("Not Found Quotation Aggregate");
        }
        return eventStoreEntity.getAggregateData();
    }

    private long getEventVersion(String aggregateId) {
        if (repository.countByAggregateId(aggregateId) == 0) {
            return 1;
        }
        return repository.countByAggregateId(aggregateId) + 1;
    }


}
