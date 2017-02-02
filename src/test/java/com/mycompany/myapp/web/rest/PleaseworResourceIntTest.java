package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinalApp;

import com.mycompany.myapp.domain.Pleasewor;
import com.mycompany.myapp.repository.PleaseworRepository;
import com.mycompany.myapp.service.PleaseworService;
import com.mycompany.myapp.service.dto.PleaseworDTO;
import com.mycompany.myapp.service.mapper.PleaseworMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PleaseworResource REST controller.
 *
 * @see PleaseworResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinalApp.class)
public class PleaseworResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    @Autowired
    private PleaseworRepository pleaseworRepository;

    @Autowired
    private PleaseworMapper pleaseworMapper;

    @Autowired
    private PleaseworService pleaseworService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPleaseworMockMvc;

    private Pleasewor pleasewor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PleaseworResource pleaseworResource = new PleaseworResource(pleaseworService);
        this.restPleaseworMockMvc = MockMvcBuilders.standaloneSetup(pleaseworResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pleasewor createEntity() {
        Pleasewor pleasewor = new Pleasewor()
                .nom(DEFAULT_NOM);
        return pleasewor;
    }

    @Before
    public void initTest() {
        pleaseworRepository.deleteAll();
        pleasewor = createEntity();
    }

    @Test
    public void createPleasewor() throws Exception {
        int databaseSizeBeforeCreate = pleaseworRepository.findAll().size();

        // Create the Pleasewor
        PleaseworDTO pleaseworDTO = pleaseworMapper.pleaseworToPleaseworDTO(pleasewor);

        restPleaseworMockMvc.perform(post("/api/pleasewors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pleaseworDTO)))
            .andExpect(status().isCreated());

        // Validate the Pleasewor in the database
        List<Pleasewor> pleaseworList = pleaseworRepository.findAll();
        assertThat(pleaseworList).hasSize(databaseSizeBeforeCreate + 1);
        Pleasewor testPleasewor = pleaseworList.get(pleaseworList.size() - 1);
        assertThat(testPleasewor.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    public void createPleaseworWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pleaseworRepository.findAll().size();

        // Create the Pleasewor with an existing ID
        Pleasewor existingPleasewor = new Pleasewor();
        existingPleasewor.setId("existing_id");
        PleaseworDTO existingPleaseworDTO = pleaseworMapper.pleaseworToPleaseworDTO(existingPleasewor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPleaseworMockMvc.perform(post("/api/pleasewors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPleaseworDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pleasewor> pleaseworList = pleaseworRepository.findAll();
        assertThat(pleaseworList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPleasewors() throws Exception {
        // Initialize the database
        pleaseworRepository.save(pleasewor);

        // Get all the pleaseworList
        restPleaseworMockMvc.perform(get("/api/pleasewors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pleasewor.getId())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())));
    }

    @Test
    public void getPleasewor() throws Exception {
        // Initialize the database
        pleaseworRepository.save(pleasewor);

        // Get the pleasewor
        restPleaseworMockMvc.perform(get("/api/pleasewors/{id}", pleasewor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pleasewor.getId()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()));
    }

    @Test
    public void getNonExistingPleasewor() throws Exception {
        // Get the pleasewor
        restPleaseworMockMvc.perform(get("/api/pleasewors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePleasewor() throws Exception {
        // Initialize the database
        pleaseworRepository.save(pleasewor);
        int databaseSizeBeforeUpdate = pleaseworRepository.findAll().size();

        // Update the pleasewor
        Pleasewor updatedPleasewor = pleaseworRepository.findOne(pleasewor.getId());
        updatedPleasewor
                .nom(UPDATED_NOM);
        PleaseworDTO pleaseworDTO = pleaseworMapper.pleaseworToPleaseworDTO(updatedPleasewor);

        restPleaseworMockMvc.perform(put("/api/pleasewors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pleaseworDTO)))
            .andExpect(status().isOk());

        // Validate the Pleasewor in the database
        List<Pleasewor> pleaseworList = pleaseworRepository.findAll();
        assertThat(pleaseworList).hasSize(databaseSizeBeforeUpdate);
        Pleasewor testPleasewor = pleaseworList.get(pleaseworList.size() - 1);
        assertThat(testPleasewor.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    public void updateNonExistingPleasewor() throws Exception {
        int databaseSizeBeforeUpdate = pleaseworRepository.findAll().size();

        // Create the Pleasewor
        PleaseworDTO pleaseworDTO = pleaseworMapper.pleaseworToPleaseworDTO(pleasewor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPleaseworMockMvc.perform(put("/api/pleasewors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pleaseworDTO)))
            .andExpect(status().isCreated());

        // Validate the Pleasewor in the database
        List<Pleasewor> pleaseworList = pleaseworRepository.findAll();
        assertThat(pleaseworList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePleasewor() throws Exception {
        // Initialize the database
        pleaseworRepository.save(pleasewor);
        int databaseSizeBeforeDelete = pleaseworRepository.findAll().size();

        // Get the pleasewor
        restPleaseworMockMvc.perform(delete("/api/pleasewors/{id}", pleasewor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pleasewor> pleaseworList = pleaseworRepository.findAll();
        assertThat(pleaseworList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pleasewor.class);
    }
}
