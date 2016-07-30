package org.govhack.gate16.service.impl;

import org.govhack.gate16.service.IndustryService;
import org.govhack.gate16.domain.Industry;
import org.govhack.gate16.repository.IndustryRepository;
import org.govhack.gate16.repository.search.IndustrySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Industry.
 */
@Service
@Transactional
public class IndustryServiceImpl implements IndustryService{

    private final Logger log = LoggerFactory.getLogger(IndustryServiceImpl.class);
    
    @Inject
    private IndustryRepository industryRepository;
    
    @Inject
    private IndustrySearchRepository industrySearchRepository;
    
    /**
     * Save a industry.
     * 
     * @param industry the entity to save
     * @return the persisted entity
     */
    public Industry save(Industry industry) {
        log.debug("Request to save Industry : {}", industry);
        Industry result = industryRepository.save(industry);
        industrySearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the industries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Industry> findAll(Pageable pageable) {
        log.debug("Request to get all Industries");
        Page<Industry> result = industryRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one industry by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Industry findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        Industry industry = industryRepository.findOne(id);
        return industry;
    }

    /**
     *  Delete the  industry by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.delete(id);
        industrySearchRepository.delete(id);
    }

    /**
     * Search for the industry corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Industry> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Industries for query {}", query);
        return industrySearchRepository.search(queryStringQuery(query), pageable);
    }
}
