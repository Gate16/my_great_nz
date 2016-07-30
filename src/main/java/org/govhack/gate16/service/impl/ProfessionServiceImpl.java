package org.govhack.gate16.service.impl;

import org.govhack.gate16.service.ProfessionService;
import org.govhack.gate16.domain.Profession;
import org.govhack.gate16.repository.ProfessionRepository;
import org.govhack.gate16.repository.search.ProfessionSearchRepository;
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
 * Service Implementation for managing Profession.
 */
@Service
@Transactional
public class ProfessionServiceImpl implements ProfessionService{

    private final Logger log = LoggerFactory.getLogger(ProfessionServiceImpl.class);
    
    @Inject
    private ProfessionRepository professionRepository;
    
    @Inject
    private ProfessionSearchRepository professionSearchRepository;
    
    /**
     * Save a profession.
     * 
     * @param profession the entity to save
     * @return the persisted entity
     */
    public Profession save(Profession profession) {
        log.debug("Request to save Profession : {}", profession);
        Profession result = professionRepository.save(profession);
        professionSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the professions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Profession> findAll(Pageable pageable) {
        log.debug("Request to get all Professions");
        Page<Profession> result = professionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one profession by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Profession findOne(Long id) {
        log.debug("Request to get Profession : {}", id);
        Profession profession = professionRepository.findOne(id);
        return profession;
    }

    /**
     *  Delete the  profession by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Profession : {}", id);
        professionRepository.delete(id);
        professionSearchRepository.delete(id);
    }

    /**
     * Search for the profession corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Profession> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Professions for query {}", query);
        return professionSearchRepository.search(queryStringQuery(query), pageable);
    }
}
