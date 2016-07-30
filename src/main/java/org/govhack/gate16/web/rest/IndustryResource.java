package org.govhack.gate16.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.govhack.gate16.domain.Industry;
import org.govhack.gate16.service.IndustryService;
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

/**
 * REST controller for managing Industry.
 */
@RestController
@RequestMapping("/api/public")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);
        
    @Inject
    private IndustryService industryService;
    
    /**
     * POST  /industries : Create a new industry.
     *
     * @param industry the industry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industry, or with status 400 (Bad Request) if the industry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/industries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Industry> createIndustry(@RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("industry", "idexists", "A new industry cannot already have an ID")).body(null);
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.created(new URI("/api/public/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("industry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industries : Updates an existing industry.
     *
     * @param industry the industry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industry,
     * or with status 400 (Bad Request) if the industry is not valid,
     * or with status 500 (Internal Server Error) if the industry couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/industries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Industry> updateIndustry(@RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to update Industry : {}", industry);
        if (industry.getId() == null) {
            return createIndustry(industry);
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("industry", industry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industries : get all the industries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/industries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Industry>> getAllIndustries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Industries");
        Page<Industry> page = industryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/public/industries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industries/:id : get the "id" industry.
     *
     * @param id the id of the industry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industry, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/industries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Industry industry = industryService.findOne(id);
        return Optional.ofNullable(industry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /industries/:id : delete the "id" industry.
     *
     * @param id the id of the industry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/industries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("industry", id.toString())).build();
    }

    /**
     * SEARCH  /_search/industries?query=:query : search for the industry corresponding
     * to the query.
     *
     * @param query the query of the industry search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/industries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Industry>> searchIndustries(@RequestParam String query, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Industries for query {}", query);
        Page<Industry> page = industryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/public/_search/industries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/industries/regions/{region}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Industry> getIndustryByRegion(Pageable pageable, @PathVariable String region)
            throws URISyntaxException{
        log.debug("REST request to get Industries by Region : {}", region);

        return industryService.findByRegion(region);
    }

    @RequestMapping(value = "/industries/percentage/{region}/{industry}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String getPercentageIndustryByRegion(@PathVariable String region, @PathVariable String industry) throws URISyntaxException{
        log.debug("REST request to get percentage of Industry:{} in Region:{}", industry, region);

        return "{\"percentage\": " + industryService.getIndustryPercentage(region, industry) + "}";
    }

    @RequestMapping(value = "/industries/{region}/{industry}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Industry getIndustryByRegionAndIndustryName(@PathVariable String region, @PathVariable String industry) throws URISyntaxException{
        log.debug("REST request to get percentage of Industry:{} in Region:{}", industry, region);

        return industryService.getIndustryByRegionAndIndustryName(region, industry);
    }

}
