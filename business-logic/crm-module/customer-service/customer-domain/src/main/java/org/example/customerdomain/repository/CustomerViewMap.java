package org.example.customerdomain.repository;

import org.example.customerdomain.view.CustomerView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerViewMap extends ElasticsearchRepository<CustomerView, String> {
}
