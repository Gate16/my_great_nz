package org.govhack.gate16.web.rest;

import org.govhack.gate16.MyGreatNzApp;
import org.govhack.gate16.domain.Census;
import org.govhack.gate16.repository.CensusRepository;
import org.govhack.gate16.service.CensusService;
import org.govhack.gate16.repository.search.CensusSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CensusResource REST controller.
 *
 * @see CensusResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyGreatNzApp.class)
@WebAppConfiguration
@IntegrationTest
public class CensusResourceIntTest {

    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_INTEREST_AREA = "AAAAA";
    private static final String UPDATED_INTEREST_AREA = "BBBBB";
    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";

    private static final Integer DEFAULT_YEAR_2006 = 1;
    private static final Integer UPDATED_YEAR_2006 = 2;

    private static final Integer DEFAULT_YEAR_2013 = 1;
    private static final Integer UPDATED_YEAR_2013 = 2;

    @Inject
    private CensusRepository censusRepository;

    @Inject
    private CensusService censusService;

    @Inject
    private CensusSearchRepository censusSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCensusMockMvc;

    private Census census;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CensusResource censusResource = new CensusResource();
        ReflectionTestUtils.setField(censusResource, "censusService", censusService);
        this.restCensusMockMvc = MockMvcBuilders.standaloneSetup(censusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        censusSearchRepository.deleteAll();
        census = new Census();
        census.setRegion(DEFAULT_REGION);
        census.setInterestArea(DEFAULT_INTEREST_AREA);
        census.setType(DEFAULT_TYPE);
        census.setYear2006(DEFAULT_YEAR_2006);
        census.setYear2013(DEFAULT_YEAR_2013);
    }

    @Test
    @Transactional
    public void createCensus() throws Exception {
        int databaseSizeBeforeCreate = censusRepository.findAll().size();

        // Create the Census

        restCensusMockMvc.perform(post("/api/public/censuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(census)))
                .andExpect(status().isCreated());

        // Validate the Census in the database
        List<Census> censuses = censusRepository.findAll();
        assertThat(censuses).hasSize(databaseSizeBeforeCreate + 1);
        Census testCensus = censuses.get(censuses.size() - 1);
        assertThat(testCensus.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testCensus.getInterestArea()).isEqualTo(DEFAULT_INTEREST_AREA);
        assertThat(testCensus.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCensus.getYear2006()).isEqualTo(DEFAULT_YEAR_2006);
        assertThat(testCensus.getYear2013()).isEqualTo(DEFAULT_YEAR_2013);

        // Validate the Census in ElasticSearch
        Census censusEs = censusSearchRepository.findOne(testCensus.getId());
        assertThat(censusEs).isEqualToComparingFieldByField(testCensus);
    }

    @Test
    @Transactional
    public void getAllCensuses() throws Exception {
        // Initialize the database
        censusRepository.saveAndFlush(census);

        // Get all the censuses
        restCensusMockMvc.perform(get("/api/public/censuses?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(census.getId().intValue())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].interestArea").value(hasItem(DEFAULT_INTEREST_AREA.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].year2006").value(hasItem(DEFAULT_YEAR_2006)))
                .andExpect(jsonPath("$.[*].year2013").value(hasItem(DEFAULT_YEAR_2013)));
    }

    @Test
    @Transactional
    public void getCensus() throws Exception {
        // Initialize the database
        censusRepository.saveAndFlush(census);

        // Get the census
        restCensusMockMvc.perform(get("/api/public/censuses/{id}", census.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(census.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.interestArea").value(DEFAULT_INTEREST_AREA.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.year2006").value(DEFAULT_YEAR_2006))
            .andExpect(jsonPath("$.year2013").value(DEFAULT_YEAR_2013));
    }

    @Test
    @Transactional
    public void getNonExistingCensus() throws Exception {
        // Get the census
        restCensusMockMvc.perform(get("/api/public/censuses/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCensus() throws Exception {
        // Initialize the database
        censusService.save(census);

        int databaseSizeBeforeUpdate = censusRepository.findAll().size();

        // Update the census
        Census updatedCensus = new Census();
        updatedCensus.setId(census.getId());
        updatedCensus.setRegion(UPDATED_REGION);
        updatedCensus.setInterestArea(UPDATED_INTEREST_AREA);
        updatedCensus.setType(UPDATED_TYPE);
        updatedCensus.setYear2006(UPDATED_YEAR_2006);
        updatedCensus.setYear2013(UPDATED_YEAR_2013);

        restCensusMockMvc.perform(put("/api/public/censuses")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCensus)))
                .andExpect(status().isOk());

        // Validate the Census in the database
        List<Census> censuses = censusRepository.findAll();
        assertThat(censuses).hasSize(databaseSizeBeforeUpdate);
        Census testCensus = censuses.get(censuses.size() - 1);
        assertThat(testCensus.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testCensus.getInterestArea()).isEqualTo(UPDATED_INTEREST_AREA);
        assertThat(testCensus.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCensus.getYear2006()).isEqualTo(UPDATED_YEAR_2006);
        assertThat(testCensus.getYear2013()).isEqualTo(UPDATED_YEAR_2013);

        // Validate the Census in ElasticSearch
        Census censusEs = censusSearchRepository.findOne(testCensus.getId());
        assertThat(censusEs).isEqualToComparingFieldByField(testCensus);
    }

    @Test
    @Transactional
    public void deleteCensus() throws Exception {
        // Initialize the database
        censusService.save(census);

        int databaseSizeBeforeDelete = censusRepository.findAll().size();

        // Get the census
        restCensusMockMvc.perform(delete("/api/public/censuses/{id}", census.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean censusExistsInEs = censusSearchRepository.exists(census.getId());
        assertThat(censusExistsInEs).isFalse();

        // Validate the database is empty
        List<Census> censuses = censusRepository.findAll();
        assertThat(censuses).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCensus() throws Exception {
        // Initialize the database
        censusService.save(census);

        // Search the census
        restCensusMockMvc.perform(get("/api/public/_search/censuses?query=id:" + census.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(census.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].interestArea").value(hasItem(DEFAULT_INTEREST_AREA.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].year2006").value(hasItem(DEFAULT_YEAR_2006)))
            .andExpect(jsonPath("$.[*].year2013").value(hasItem(DEFAULT_YEAR_2013)));
    }
}
