package org.govhack.gate16.service;

import org.govhack.gate16.domain.Industry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Industry.
 */
public interface IndustryService {

    /**
     * Save a industry.
     * 
     * @param industry the entity to save
     * @return the persisted entity
     */
    Industry save(Industry industry);

    /**
     *  Get all the industries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Industry> findAll(Pageable pageable);

    /**
     *  Get the "id" industry.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Industry findOne(Long id);

    /**
     *  Delete the "id" industry.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the industry corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Industry> search(String query, Pageable pageable);

    List<Industry> findByRegion(String region);

    float getIndustryPercentage(String regionName, String industryName);

    Industry getIndustryByRegionAndIndustryName(String regionName, String industryName);
}
