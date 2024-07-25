package org.example.policydomain.repository.view;

import org.example.policydomain.view.AdditionalModificationHistoryView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AdditionalModificationHistoryViewRepository extends ElasticsearchRepository<AdditionalModificationHistoryView, String> {
}
