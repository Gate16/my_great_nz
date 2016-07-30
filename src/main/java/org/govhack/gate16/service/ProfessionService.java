package org.govhack.gate16.service;

import org.govhack.gate16.domain.Profession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Profession.
 */
public interface ProfessionService {

    /**
     * Save a profession.
     * 
     * @param profession the entity to save
     * @return the persisted entity
     */
    Profession save(Profession profession);

    /**
     *  Get all the professions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Profession> findAll(Pageable pageable);

    /**
     *  Get the "id" profession.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Profession findOne(Long id);

    /**
     *  Delete the "id" profession.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the profession corresponding to the query.
     * 
     *  @param query the query of the search
     *  @return the list of entities
     */
    Page<Profession> search(String query, Pageable pageable);
}
