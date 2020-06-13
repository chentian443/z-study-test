package org.elasticsearch.test.elast;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemDocumentRepository extends ElasticsearchRepository<ItemDocument, String> {
}
