package org.govhack.gate16.web.rest;

import org.govhack.gate16.MyGreatNzApp;
import org.govhack.gate16.domain.Industry;
import org.govhack.gate16.repository.IndustryRepository;
import org.govhack.gate16.repository.search.IndustrySearchRepository;
import org.govhack.gate16.service.IndustryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the IndustryResource REST controller.
 *
 * @see IndustryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyGreatNzApp.class)
@WebAppConfiguration
@IntegrationTest
public class IndustryResourceIntTest {

    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";
    private static final String DEFAULT_INDUSTRY_NAME = "AAAAA";
    private static final String UPDATED_INDUSTRY_NAME = "BBBBB";

    private static final Integer DEFAULT_YEAR_2016 = 1;
    private static final Integer UPDATED_YEAR_2016 = 2;

    private static final Integer DEFAULT_YEAR_2017 = 1;
    private static final Integer UPDATED_YEAR_2017 = 2;

    private static final Integer DEFAULT_YEAR_2018 = 1;
    private static final Integer UPDATED_YEAR_2018 = 2;

    private static final Integer DEFAULT_YEAR_2019 = 1;
    private static final Integer UPDATED_YEAR_2019 = 2;

    private static final Float DEFAULT_GROWTH = 1F;
    private static final Float UPDATED_GROWTH = 2F;

    private static final Float DEFAULT_GROWTH_PERCENTAGE = 1F;
    private static final Float UPDATED_GROWTH_PERCENTAGE = 2F;

    @Inject
    private IndustryRepository industryRepository;

    @Inject
    private IndustryService industryService;

    @Inject
    private IndustrySearchRepository industrySearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIndustryMockMvc;

    private Industry industry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IndustryResource industryResource = new IndustryResource();
        ReflectionTestUtils.setField(industryResource, "industryService", industryService);
        this.restIndustryMockMvc = MockMvcBuilders.standaloneSetup(industryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        industrySearchRepository.deleteAll();
        industry = new Industry();
        industry.setRegion(DEFAULT_REGION);
        industry.setIndustryName(DEFAULT_INDUSTRY_NAME);
        industry.setYear2016(DEFAULT_YEAR_2016);
        industry.setYear2017(DEFAULT_YEAR_2017);
        industry.setYear2018(DEFAULT_YEAR_2018);
        industry.setYear2019(DEFAULT_YEAR_2019);
        industry.setGrowth(DEFAULT_GROWTH);
        industry.setGrowthPercentage(DEFAULT_GROWTH_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createIndustry() throws Exception {
        int databaseSizeBeforeCreate = industryRepository.findAll().size();

        // Create the Industry

        restIndustryMockMvc.perform(post("/api/public/industries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(industry)))
                .andExpect(status().isCreated());

        // Validate the Industry in the database
        List<Industry> industries = industryRepository.findAll();
        assertThat(industries).hasSize(databaseSizeBeforeCreate + 1);
        Industry testIndustry = industries.get(industries.size() - 1);
        assertThat(testIndustry.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testIndustry.getIndustryName()).isEqualTo(DEFAULT_INDUSTRY_NAME);
        assertThat(testIndustry.getYear2016()).isEqualTo(DEFAULT_YEAR_2016);
        assertThat(testIndustry.getYear2017()).isEqualTo(DEFAULT_YEAR_2017);
        assertThat(testIndustry.getYear2018()).isEqualTo(DEFAULT_YEAR_2018);
        assertThat(testIndustry.getYear2019()).isEqualTo(DEFAULT_YEAR_2019);
        assertThat(testIndustry.getGrowth()).isEqualTo(DEFAULT_GROWTH);
        assertThat(testIndustry.getGrowthPercentage()).isEqualTo(DEFAULT_GROWTH_PERCENTAGE);

        // Validate the Industry in ElasticSearch
        Industry industryEs = industrySearchRepository.findOne(testIndustry.getId());
        assertThat(industryEs).isEqualToComparingFieldByField(testIndustry);
    }

    @Test
    @Transactional
    public void getAllIndustries() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get all the industries
        restIndustryMockMvc.perform(get("/api/public/industries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].industryName").value(hasItem(DEFAULT_INDUSTRY_NAME.toString())))
                .andExpect(jsonPath("$.[*].year2016").value(hasItem(DEFAULT_YEAR_2016)))
                .andExpect(jsonPath("$.[*].year2017").value(hasItem(DEFAULT_YEAR_2017)))
                .andExpect(jsonPath("$.[*].year2018").value(hasItem(DEFAULT_YEAR_2018)))
                .andExpect(jsonPath("$.[*].year2019").value(hasItem(DEFAULT_YEAR_2019)))
                .andExpect(jsonPath("$.[*].growth").value(hasItem(DEFAULT_GROWTH.doubleValue())))
                .andExpect(jsonPath("$.[*].growthPercentage").value(hasItem(DEFAULT_GROWTH_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getIndustry() throws Exception {
        // Initialize the database
        industryRepository.saveAndFlush(industry);

        // Get the industry
        restIndustryMockMvc.perform(get("/api/public/industries/{id}", industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(industry.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.industryName").value(DEFAULT_INDUSTRY_NAME.toString()))
            .andExpect(jsonPath("$.year2016").value(DEFAULT_YEAR_2016))
            .andExpect(jsonPath("$.year2017").value(DEFAULT_YEAR_2017))
            .andExpect(jsonPath("$.year2018").value(DEFAULT_YEAR_2018))
            .andExpect(jsonPath("$.year2019").value(DEFAULT_YEAR_2019))
            .andExpect(jsonPath("$.growth").value(DEFAULT_GROWTH.doubleValue()))
            .andExpect(jsonPath("$.growthPercentage").value(DEFAULT_GROWTH_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustry() throws Exception {
        // Get the industry
        restIndustryMockMvc.perform(get("/api/public/industries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustry() throws Exception {
        // Initialize the database
        industryService.save(industry);

        int databaseSizeBeforeUpdate = industryRepository.findAll().size();

        // Update the industry
        Industry updatedIndustry = new Industry();
        updatedIndustry.setId(industry.getId());
        updatedIndustry.setRegion(UPDATED_REGION);
        updatedIndustry.setIndustryName(UPDATED_INDUSTRY_NAME);
        updatedIndustry.setYear2016(UPDATED_YEAR_2016);
        updatedIndustry.setYear2017(UPDATED_YEAR_2017);
        updatedIndustry.setYear2018(UPDATED_YEAR_2018);
        updatedIndustry.setYear2019(UPDATED_YEAR_2019);
        updatedIndustry.setGrowth(UPDATED_GROWTH);
        updatedIndustry.setGrowthPercentage(UPDATED_GROWTH_PERCENTAGE);

        restIndustryMockMvc.perform(put("/api/public/industries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedIndustry)))
                .andExpect(status().isOk());

        // Validate the Industry in the database
        List<Industry> industries = industryRepository.findAll();
        assertThat(industries).hasSize(databaseSizeBeforeUpdate);
        Industry testIndustry = industries.get(industries.size() - 1);
        assertThat(testIndustry.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testIndustry.getIndustryName()).isEqualTo(UPDATED_INDUSTRY_NAME);
        assertThat(testIndustry.getYear2016()).isEqualTo(UPDATED_YEAR_2016);
        assertThat(testIndustry.getYear2017()).isEqualTo(UPDATED_YEAR_2017);
        assertThat(testIndustry.getYear2018()).isEqualTo(UPDATED_YEAR_2018);
        assertThat(testIndustry.getYear2019()).isEqualTo(UPDATED_YEAR_2019);
        assertThat(testIndustry.getGrowth()).isEqualTo(UPDATED_GROWTH);
        assertThat(testIndustry.getGrowthPercentage()).isEqualTo(UPDATED_GROWTH_PERCENTAGE);

        // Validate the Industry in ElasticSearch
        Industry industryEs = industrySearchRepository.findOne(testIndustry.getId());
        assertThat(industryEs).isEqualToComparingFieldByField(testIndustry);
    }

    @Test
    @Transactional
    public void deleteIndustry() throws Exception {
        // Initialize the database
        industryService.save(industry);

        int databaseSizeBeforeDelete = industryRepository.findAll().size();

        // Get the industry
        restIndustryMockMvc.perform(delete("/api/public/industries/{id}", industry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean industryExistsInEs = industrySearchRepository.exists(industry.getId());
        assertThat(industryExistsInEs).isFalse();

        // Validate the database is empty
        List<Industry> industries = industryRepository.findAll();
        assertThat(industries).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustry() throws Exception {
        // Initialize the database
        industryService.save(industry);

        // Search the industry
        restIndustryMockMvc.perform(get("/api/public/_search/industries?query=id:" + industry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industry.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].industryName").value(hasItem(DEFAULT_INDUSTRY_NAME.toString())))
            .andExpect(jsonPath("$.[*].year2016").value(hasItem(DEFAULT_YEAR_2016)))
            .andExpect(jsonPath("$.[*].year2017").value(hasItem(DEFAULT_YEAR_2017)))
            .andExpect(jsonPath("$.[*].year2018").value(hasItem(DEFAULT_YEAR_2018)))
            .andExpect(jsonPath("$.[*].year2019").value(hasItem(DEFAULT_YEAR_2019)))
            .andExpect(jsonPath("$.[*].growth").value(hasItem(DEFAULT_GROWTH.doubleValue())))
            .andExpect(jsonPath("$.[*].growthPercentage").value(hasItem(DEFAULT_GROWTH_PERCENTAGE.doubleValue())));
    }
}
