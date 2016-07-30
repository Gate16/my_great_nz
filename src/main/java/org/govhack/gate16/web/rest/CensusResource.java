package org.govhack.gate16.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.govhack.gate16.domain.Census;
import org.govhack.gate16.service.CensusService;
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
 * REST controller for managing Census.
 */
@RestController
@RequestMapping("/api/public")
public class CensusResource {

    private final Logger log = LoggerFactory.getLogger(CensusResource.class);
        
    @Inject
    private CensusService censusService;
    
    /**
     * POST  /censuses : Create a new census.
     *
     * @param census the census to create
     * @return the ResponseEntity with status 201 (Created) and with body the new census, or with status 400 (Bad Request) if the census has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/censuses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Census> createCensus(@RequestBody Census census) throws URISyntaxException {
        log.debug("REST request to save Census : {}", census);
        if (census.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("census", "idexists", "A new census cannot already have an ID")).body(null);
        }
        Census result = censusService.save(census);
        return ResponseEntity.created(new URI("/api/public/censuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("census", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /censuses : Updates an existing census.
     *
     * @param census the census to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated census,
     * or with status 400 (Bad Request) if the census is not valid,
     * or with status 500 (Internal Server Error) if the census couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/censuses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Census> updateCensus(@RequestBody Census census) throws URISyntaxException {
        log.debug("REST request to update Census : {}", census);
        if (census.getId() == null) {
            return createCensus(census);
        }
        Census result = censusService.save(census);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("census", census.getId().toString()))
            .body(result);
    }

    /**
     * GET  /censuses : get all the censuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of censuses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/censuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Census>> getAllCensuses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Censuses");
        Page<Census> page = censusService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/public/censuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /censuses/:id : get the "id" census.
     *
     * @param id the id of the census to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the census, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/censuses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Census> getCensus(@PathVariable Long id) {
        log.debug("REST request to get Census : {}", id);
        Census census = censusService.findOne(id);
        return Optional.ofNullable(census)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /censuses/:id : delete the "id" census.
     *
     * @param id the id of the census to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/censuses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCensus(@PathVariable Long id) {
        log.debug("REST request to delete Census : {}", id);
        censusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("census", id.toString())).build();
    }

    /**
     * SEARCH  /_search/censuses?query=:query : search for the census corresponding
     * to the query.
     *
     * @param query the query of the census search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/censuses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Census>> searchCensuses(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Censuses for query {}", query);
        Page<Census> page = censusService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/public/_search/censuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/censuses/percentage/{region}/{interestArea}/{type}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getPercentage(@PathVariable String region, @PathVariable String interestArea, @PathVariable String type) throws URISyntaxException{
        return "{\"percentage\": " + censusService.getPercentageFor2013(region, interestArea, type) + "}";
    }

    @RequestMapping(value = "/censuses/{region}/{interestArea}/{type}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Census getIndustryByRegionAndIndustryName(@PathVariable String region, @PathVariable String interestArea, @PathVariable String type) throws URISyntaxException{
        return censusService.getCensus(region, interestArea, type);
    }



}
