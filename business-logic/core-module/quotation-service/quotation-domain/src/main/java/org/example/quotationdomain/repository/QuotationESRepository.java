package org.example.quotationdomain.repository;

import org.example.quotationdomain.domain.view.QuotationView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationESRepository extends ElasticsearchRepository<QuotationView, String> {
}
