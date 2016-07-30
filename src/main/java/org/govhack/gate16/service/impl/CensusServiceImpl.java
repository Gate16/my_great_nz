package org.govhack.gate16.service.impl;

import org.govhack.gate16.service.CensusService;
import org.govhack.gate16.domain.Census;
import org.govhack.gate16.repository.CensusRepository;
import org.govhack.gate16.repository.search.CensusSearchRepository;
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
 * Service Implementation for managing Census.
 */
@Service
@Transactional
public class CensusServiceImpl implements CensusService{

    private final Logger log = LoggerFactory.getLogger(CensusServiceImpl.class);
    
    @Inject
    private CensusRepository censusRepository;
    
    @Inject
    private CensusSearchRepository censusSearchRepository;
    
    /**
     * Save a census.
     * 
     * @param census the entity to save
     * @return the persisted entity
     */
    public Census save(Census census) {
        log.debug("Request to save Census : {}", census);
        Census result = censusRepository.save(census);
        censusSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the censuses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Census> findAll(Pageable pageable) {
        log.debug("Request to get all Censuses");
        Page<Census> result = censusRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one census by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Census findOne(Long id) {
        log.debug("Request to get Census : {}", id);
        Census census = censusRepository.findOne(id);
        return census;
    }

    /**
     *  Delete the  census by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Census : {}", id);
        censusRepository.delete(id);
        censusSearchRepository.delete(id);
    }

    /**
     * Search for the census corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Census> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Censuses for query {}", query);
        return censusSearchRepository.search(queryStringQuery(query), pageable);
    }
}
