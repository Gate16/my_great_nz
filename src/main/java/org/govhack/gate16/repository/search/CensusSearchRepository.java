package org.govhack.gate16.repository.search;

import org.govhack.gate16.domain.Census;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Census entity.
 */
public interface CensusSearchRepository extends ElasticsearchRepository<Census, Long> {
}
