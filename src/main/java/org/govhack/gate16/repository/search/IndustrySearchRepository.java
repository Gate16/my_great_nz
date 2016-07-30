package org.govhack.gate16.repository.search;

import org.govhack.gate16.domain.Industry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Industry entity.
 */
public interface IndustrySearchRepository extends ElasticsearchRepository<Industry, Long> {
}
