package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinalApp;

import com.mycompany.myapp.domain.Pleasework;
import com.mycompany.myapp.repository.PleaseworkRepository;

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
 * Test class for the PleaseworkResource REST controller.
 *
 * @see PleaseworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinalApp.class)
public class PleaseworkResourceIntTest {

    @Autowired
    private PleaseworkRepository pleaseworkRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPleaseworkMockMvc;

    private Pleasework pleasework;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            PleaseworkResource pleaseworkResource = new PleaseworkResource(pleaseworkRepository);
        this.restPleaseworkMockMvc = MockMvcBuilders.standaloneSetup(pleaseworkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pleasework createEntity() {
        Pleasework pleasework = new Pleasework();
        return pleasework;
    }

    @Before
    public void initTest() {
        pleaseworkRepository.deleteAll();
        pleasework = createEntity();
    }

    @Test
    public void createPleasework() throws Exception {
        int databaseSizeBeforeCreate = pleaseworkRepository.findAll().size();

        // Create the Pleasework

        restPleaseworkMockMvc.perform(post("/api/pleaseworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pleasework)))
            .andExpect(status().isCreated());

        // Validate the Pleasework in the database
        List<Pleasework> pleaseworkList = pleaseworkRepository.findAll();
        assertThat(pleaseworkList).hasSize(databaseSizeBeforeCreate + 1);
        Pleasework testPleasework = pleaseworkList.get(pleaseworkList.size() - 1);
    }

    @Test
    public void createPleaseworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pleaseworkRepository.findAll().size();

        // Create the Pleasework with an existing ID
        Pleasework existingPleasework = new Pleasework();
        existingPleasework.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPleaseworkMockMvc.perform(post("/api/pleaseworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPleasework)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pleasework> pleaseworkList = pleaseworkRepository.findAll();
        assertThat(pleaseworkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPleaseworks() throws Exception {
        // Initialize the database
        pleaseworkRepository.save(pleasework);

        // Get all the pleaseworkList
        restPleaseworkMockMvc.perform(get("/api/pleaseworks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pleasework.getId())));
    }

    @Test
    public void getPleasework() throws Exception {
        // Initialize the database
        pleaseworkRepository.save(pleasework);

        // Get the pleasework
        restPleaseworkMockMvc.perform(get("/api/pleaseworks/{id}", pleasework.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pleasework.getId()));
    }

    @Test
    public void getNonExistingPleasework() throws Exception {
        // Get the pleasework
        restPleaseworkMockMvc.perform(get("/api/pleaseworks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePleasework() throws Exception {
        // Initialize the database
        pleaseworkRepository.save(pleasework);
        int databaseSizeBeforeUpdate = pleaseworkRepository.findAll().size();

        // Update the pleasework
        Pleasework updatedPleasework = pleaseworkRepository.findOne(pleasework.getId());

        restPleaseworkMockMvc.perform(put("/api/pleaseworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPleasework)))
            .andExpect(status().isOk());

        // Validate the Pleasework in the database
        List<Pleasework> pleaseworkList = pleaseworkRepository.findAll();
        assertThat(pleaseworkList).hasSize(databaseSizeBeforeUpdate);
        Pleasework testPleasework = pleaseworkList.get(pleaseworkList.size() - 1);
    }

    @Test
    public void updateNonExistingPleasework() throws Exception {
        int databaseSizeBeforeUpdate = pleaseworkRepository.findAll().size();

        // Create the Pleasework

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPleaseworkMockMvc.perform(put("/api/pleaseworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pleasework)))
            .andExpect(status().isCreated());

        // Validate the Pleasework in the database
        List<Pleasework> pleaseworkList = pleaseworkRepository.findAll();
        assertThat(pleaseworkList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePleasework() throws Exception {
        // Initialize the database
        pleaseworkRepository.save(pleasework);
        int databaseSizeBeforeDelete = pleaseworkRepository.findAll().size();

        // Get the pleasework
        restPleaseworkMockMvc.perform(delete("/api/pleaseworks/{id}", pleasework.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pleasework> pleaseworkList = pleaseworkRepository.findAll();
        assertThat(pleaseworkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pleasework.class);
    }
}
