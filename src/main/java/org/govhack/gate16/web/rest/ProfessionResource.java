package org.govhack.gate16.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.govhack.gate16.domain.Profession;
import org.govhack.gate16.service.ProfessionService;
import org.govhack.gate16.web.rest.util.HeaderUtil;
import org.govhack.gate16.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Profession.
 */
@RestController
@RequestMapping("/api/public")
public class ProfessionResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionResource.class);
        
    @Inject
    private ProfessionService professionService;
    
    /**
     * POST  /professions : Create a new profession.
     *
     * @param profession the profession to create
     * @return the ResponseEntity with status 201 (Created) and with body the new profession, or with status 400 (Bad Request) if the profession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/professions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profession> createProfession(@RequestBody Profession profession) throws URISyntaxException {
        log.debug("REST request to save Profession : {}", profession);
        if (profession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("profession", "idexists", "A new profession cannot already have an ID")).body(null);
        }
        Profession result = professionService.save(profession);
        return ResponseEntity.created(new URI("/api/professions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("profession", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professions : Updates an existing profession.
     *
     * @param profession the profession to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated profession,
     * or with status 400 (Bad Request) if the profession is not valid,
     * or with status 500 (Internal Server Error) if the profession couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/professions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profession> updateProfession(@RequestBody Profession profession) throws URISyntaxException {
        log.debug("REST request to update Profession : {}", profession);
        if (profession.getId() == null) {
            return createProfession(profession);
        }
        Profession result = professionService.save(profession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("profession", profession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professions : get all the professions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of professions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/professions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Profession>> getAllProfessions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Professions");
        Page<Profession> page = professionService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professions/:id : get the "id" profession.
     *
     * @param id the id of the profession to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the profession, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/professions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Profession> getProfession(@PathVariable Long id) {
        log.debug("REST request to get Profession : {}", id);
        Profession profession = professionService.findOne(id);
        return Optional.ofNullable(profession)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professions/:id : delete the "id" profession.
     *
     * @param id the id of the profession to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/professions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        log.debug("REST request to delete Profession : {}", id);
        professionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("profession", id.toString())).build();
    }

    /**
     * SEARCH  /_search/professions?query=:query : search for the profession corresponding
     * to the query.
     *
     * @param query the query of the profession search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/professions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Profession>> searchProfessions(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Professions for query {}", query);
        Page<Profession> page = professionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/public/_search/professions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
