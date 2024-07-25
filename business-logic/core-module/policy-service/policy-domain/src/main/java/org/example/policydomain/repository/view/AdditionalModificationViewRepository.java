package org.example.policydomain.repository.view;

import org.example.policydomain.view.AdditionalModificationView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AdditionalModificationViewRepository extends ElasticsearchRepository<AdditionalModificationView, String> {
}
