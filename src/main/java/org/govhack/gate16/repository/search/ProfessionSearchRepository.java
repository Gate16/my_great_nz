package org.govhack.gate16.repository.search;

import org.govhack.gate16.domain.Profession;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Profession entity.
 */
public interface ProfessionSearchRepository extends ElasticsearchRepository<Profession, Long> {
}
