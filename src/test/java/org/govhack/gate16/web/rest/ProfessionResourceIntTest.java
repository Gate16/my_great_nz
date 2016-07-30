package org.govhack.gate16.web.rest;

import org.govhack.gate16.MyGreatNzApp;
import org.govhack.gate16.domain.Profession;
import org.govhack.gate16.repository.ProfessionRepository;
import org.govhack.gate16.service.ProfessionService;
import org.govhack.gate16.repository.search.ProfessionSearchRepository;

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
 * Test class for the ProfessionResource REST controller.
 *
 * @see ProfessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MyGreatNzApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProfessionResourceIntTest {

    private static final String DEFAULT_REGION = "AAAAA";
    private static final String UPDATED_REGION = "BBBBB";

    private static final Integer DEFAULT_PROFESSION_ID = 1;
    private static final Integer UPDATED_PROFESSION_ID = 2;
    private static final String DEFAULT_PROFESSION_NAME = "AAAAA";
    private static final String UPDATED_PROFESSION_NAME = "BBBBB";

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
    private ProfessionRepository professionRepository;

    @Inject
    private ProfessionService professionService;

    @Inject
    private ProfessionSearchRepository professionSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProfessionMockMvc;

    private Profession profession;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProfessionResource professionResource = new ProfessionResource();
        ReflectionTestUtils.setField(professionResource, "professionService", professionService);
        this.restProfessionMockMvc = MockMvcBuilders.standaloneSetup(professionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        professionSearchRepository.deleteAll();
        profession = new Profession();
        profession.setRegion(DEFAULT_REGION);
        profession.setProfessionId(DEFAULT_PROFESSION_ID);
        profession.setProfessionName(DEFAULT_PROFESSION_NAME);
        profession.setYear_2016(DEFAULT_YEAR_2016);
        profession.setYear_2017(DEFAULT_YEAR_2017);
        profession.setYear_2018(DEFAULT_YEAR_2018);
        profession.setYear_2019(DEFAULT_YEAR_2019);
        profession.setGrowth(DEFAULT_GROWTH);
        profession.setGrowthPercentage(DEFAULT_GROWTH_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createProfession() throws Exception {
        int databaseSizeBeforeCreate = professionRepository.findAll().size();

        // Create the Profession

        restProfessionMockMvc.perform(post("/api/public/professions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(profession)))
                .andExpect(status().isCreated());

        // Validate the Profession in the database
        List<Profession> professions = professionRepository.findAll();
        assertThat(professions).hasSize(databaseSizeBeforeCreate + 1);
        Profession testProfession = professions.get(professions.size() - 1);
        assertThat(testProfession.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testProfession.getProfessionId()).isEqualTo(DEFAULT_PROFESSION_ID);
        assertThat(testProfession.getProfessionName()).isEqualTo(DEFAULT_PROFESSION_NAME);
        assertThat(testProfession.getYear_2016()).isEqualTo(DEFAULT_YEAR_2016);
        assertThat(testProfession.getYear_2017()).isEqualTo(DEFAULT_YEAR_2017);
        assertThat(testProfession.getYear_2018()).isEqualTo(DEFAULT_YEAR_2018);
        assertThat(testProfession.getYear_2019()).isEqualTo(DEFAULT_YEAR_2019);
        assertThat(testProfession.getGrowth()).isEqualTo(DEFAULT_GROWTH);
        assertThat(testProfession.getGrowthPercentage()).isEqualTo(DEFAULT_GROWTH_PERCENTAGE);

        // Validate the Profession in ElasticSearch
        Profession professionEs = professionSearchRepository.findOne(testProfession.getId());
        assertThat(professionEs).isEqualToComparingFieldByField(testProfession);
    }

    @Test
    @Transactional
    public void getAllProfessions() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get all the professions
        restProfessionMockMvc.perform(get("/api/public/professions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(profession.getId().intValue())))
                .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
                .andExpect(jsonPath("$.[*].professionId").value(hasItem(DEFAULT_PROFESSION_ID)))
                .andExpect(jsonPath("$.[*].professionName").value(hasItem(DEFAULT_PROFESSION_NAME.toString())))
                .andExpect(jsonPath("$.[*].year_2016").value(hasItem(DEFAULT_YEAR_2016)))
                .andExpect(jsonPath("$.[*].year_2017").value(hasItem(DEFAULT_YEAR_2017)))
                .andExpect(jsonPath("$.[*].year_2018").value(hasItem(DEFAULT_YEAR_2018)))
                .andExpect(jsonPath("$.[*].year_2019").value(hasItem(DEFAULT_YEAR_2019)))
                .andExpect(jsonPath("$.[*].growth").value(hasItem(DEFAULT_GROWTH.doubleValue())))
                .andExpect(jsonPath("$.[*].growthPercentage").value(hasItem(DEFAULT_GROWTH_PERCENTAGE.doubleValue())));
    }

    @Test
    @Transactional
    public void getProfession() throws Exception {
        // Initialize the database
        professionRepository.saveAndFlush(profession);

        // Get the profession
        restProfessionMockMvc.perform(get("/api/public/professions/{id}", profession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(profession.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION.toString()))
            .andExpect(jsonPath("$.professionId").value(DEFAULT_PROFESSION_ID))
            .andExpect(jsonPath("$.professionName").value(DEFAULT_PROFESSION_NAME.toString()))
            .andExpect(jsonPath("$.year_2016").value(DEFAULT_YEAR_2016))
            .andExpect(jsonPath("$.year_2017").value(DEFAULT_YEAR_2017))
            .andExpect(jsonPath("$.year_2018").value(DEFAULT_YEAR_2018))
            .andExpect(jsonPath("$.year_2019").value(DEFAULT_YEAR_2019))
            .andExpect(jsonPath("$.growth").value(DEFAULT_GROWTH.doubleValue()))
            .andExpect(jsonPath("$.growthPercentage").value(DEFAULT_GROWTH_PERCENTAGE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProfession() throws Exception {
        // Get the profession
        restProfessionMockMvc.perform(get("/api/public/professions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfession() throws Exception {
        // Initialize the database
        professionService.save(profession);

        int databaseSizeBeforeUpdate = professionRepository.findAll().size();

        // Update the profession
        Profession updatedProfession = new Profession();
        updatedProfession.setId(profession.getId());
        updatedProfession.setRegion(UPDATED_REGION);
        updatedProfession.setProfessionId(UPDATED_PROFESSION_ID);
        updatedProfession.setProfessionName(UPDATED_PROFESSION_NAME);
        updatedProfession.setYear_2016(UPDATED_YEAR_2016);
        updatedProfession.setYear_2017(UPDATED_YEAR_2017);
        updatedProfession.setYear_2018(UPDATED_YEAR_2018);
        updatedProfession.setYear_2019(UPDATED_YEAR_2019);
        updatedProfession.setGrowth(UPDATED_GROWTH);
        updatedProfession.setGrowthPercentage(UPDATED_GROWTH_PERCENTAGE);

        restProfessionMockMvc.perform(put("/api/public/professions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProfession)))
                .andExpect(status().isOk());

        // Validate the Profession in the database
        List<Profession> professions = professionRepository.findAll();
        assertThat(professions).hasSize(databaseSizeBeforeUpdate);
        Profession testProfession = professions.get(professions.size() - 1);
        assertThat(testProfession.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testProfession.getProfessionId()).isEqualTo(UPDATED_PROFESSION_ID);
        assertThat(testProfession.getProfessionName()).isEqualTo(UPDATED_PROFESSION_NAME);
        assertThat(testProfession.getYear_2016()).isEqualTo(UPDATED_YEAR_2016);
        assertThat(testProfession.getYear_2017()).isEqualTo(UPDATED_YEAR_2017);
        assertThat(testProfession.getYear_2018()).isEqualTo(UPDATED_YEAR_2018);
        assertThat(testProfession.getYear_2019()).isEqualTo(UPDATED_YEAR_2019);
        assertThat(testProfession.getGrowth()).isEqualTo(UPDATED_GROWTH);
        assertThat(testProfession.getGrowthPercentage()).isEqualTo(UPDATED_GROWTH_PERCENTAGE);

        // Validate the Profession in ElasticSearch
        Profession professionEs = professionSearchRepository.findOne(testProfession.getId());
        assertThat(professionEs).isEqualToComparingFieldByField(testProfession);
    }

    @Test
    @Transactional
    public void deleteProfession() throws Exception {
        // Initialize the database
        professionService.save(profession);

        int databaseSizeBeforeDelete = professionRepository.findAll().size();

        // Get the profession
        restProfessionMockMvc.perform(delete("/api/public/professions/{id}", profession.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean professionExistsInEs = professionSearchRepository.exists(profession.getId());
        assertThat(professionExistsInEs).isFalse();

        // Validate the database is empty
        List<Profession> professions = professionRepository.findAll();
        assertThat(professions).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfession() throws Exception {
        // Initialize the database
        professionService.save(profession);

        // Search the profession
        restProfessionMockMvc.perform(get("/api/public/_search/professions?query=id:" + profession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profession.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION.toString())))
            .andExpect(jsonPath("$.[*].professionId").value(hasItem(DEFAULT_PROFESSION_ID)))
            .andExpect(jsonPath("$.[*].professionName").value(hasItem(DEFAULT_PROFESSION_NAME.toString())))
            .andExpect(jsonPath("$.[*].year_2016").value(hasItem(DEFAULT_YEAR_2016)))
            .andExpect(jsonPath("$.[*].year_2017").value(hasItem(DEFAULT_YEAR_2017)))
            .andExpect(jsonPath("$.[*].year_2018").value(hasItem(DEFAULT_YEAR_2018)))
            .andExpect(jsonPath("$.[*].year_2019").value(hasItem(DEFAULT_YEAR_2019)))
            .andExpect(jsonPath("$.[*].growth").value(hasItem(DEFAULT_GROWTH.doubleValue())))
            .andExpect(jsonPath("$.[*].growthPercentage").value(hasItem(DEFAULT_GROWTH_PERCENTAGE.doubleValue())));
    }
}
