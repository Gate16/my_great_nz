package org.govhack.gate16.service;

import org.govhack.gate16.domain.Census;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Census.
 */
public interface CensusService {

    /**
     * Save a census.
     * 
     * @param census the entity to save
     * @return the persisted entity
     */
    Census save(Census census);

    /**
     *  Get all the censuses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Census> findAll(Pageable pageable);

    /**
     *  Get the "id" census.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Census findOne(Long id);

    /**
     *  Delete the "id" census.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the census corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Census> search(String query, Pageable pageable);

    float getPercentageFor2013(String region, String interestArea, String type);

    Census getCensus(String region, String interestArea, String type);
}
