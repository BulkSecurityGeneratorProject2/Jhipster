package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.FinalApp;

import com.mycompany.myapp.domain.Twitter;
import com.mycompany.myapp.repository.TwitterRepository;
import com.mycompany.myapp.service.TwitterService;

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
 * Test class for the TwitterResource REST controller.
 *
 * @see TwitterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FinalApp.class)
public class TwitterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TwitterRepository twitterRepository;

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTwitterMockMvc;

    private Twitter twitter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TwitterResource twitterResource = new TwitterResource(twitterService);
        this.restTwitterMockMvc = MockMvcBuilders.standaloneSetup(twitterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Twitter createEntity() {
        Twitter twitter = new Twitter()
                .name(DEFAULT_NAME);
        return twitter;
    }

    @Before
    public void initTest() {
        twitterRepository.deleteAll();
        twitter = createEntity();
    }

    @Test
    public void createTwitter() throws Exception {
        int databaseSizeBeforeCreate = twitterRepository.findAll().size();

        // Create the Twitter

        restTwitterMockMvc.perform(post("/api/twitters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitter)))
            .andExpect(status().isCreated());

        // Validate the Twitter in the database
        List<Twitter> twitterList = twitterRepository.findAll();
        assertThat(twitterList).hasSize(databaseSizeBeforeCreate + 1);
        Twitter testTwitter = twitterList.get(twitterList.size() - 1);
        assertThat(testTwitter.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    public void createTwitterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = twitterRepository.findAll().size();

        // Create the Twitter with an existing ID
        Twitter existingTwitter = new Twitter();
        existingTwitter.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restTwitterMockMvc.perform(post("/api/twitters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingTwitter)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Twitter> twitterList = twitterRepository.findAll();
        assertThat(twitterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllTwitters() throws Exception {
        // Initialize the database
        twitterRepository.save(twitter);

        // Get all the twitterList
        restTwitterMockMvc.perform(get("/api/twitters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(twitter.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    public void getTwitter() throws Exception {
        // Initialize the database
        twitterRepository.save(twitter);

        // Get the twitter
        restTwitterMockMvc.perform(get("/api/twitters/{id}", twitter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(twitter.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    public void getNonExistingTwitter() throws Exception {
        // Get the twitter
        restTwitterMockMvc.perform(get("/api/twitters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTwitter() throws Exception {
        // Initialize the database
        twitterService.save(twitter);

        int databaseSizeBeforeUpdate = twitterRepository.findAll().size();

        // Update the twitter
        Twitter updatedTwitter = twitterRepository.findOne(twitter.getId());
        updatedTwitter
                .name(UPDATED_NAME);

        restTwitterMockMvc.perform(put("/api/twitters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTwitter)))
            .andExpect(status().isOk());

        // Validate the Twitter in the database
        List<Twitter> twitterList = twitterRepository.findAll();
        assertThat(twitterList).hasSize(databaseSizeBeforeUpdate);
        Twitter testTwitter = twitterList.get(twitterList.size() - 1);
        assertThat(testTwitter.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void updateNonExistingTwitter() throws Exception {
        int databaseSizeBeforeUpdate = twitterRepository.findAll().size();

        // Create the Twitter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTwitterMockMvc.perform(put("/api/twitters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(twitter)))
            .andExpect(status().isCreated());

        // Validate the Twitter in the database
        List<Twitter> twitterList = twitterRepository.findAll();
        assertThat(twitterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteTwitter() throws Exception {
        // Initialize the database
        twitterService.save(twitter);

        int databaseSizeBeforeDelete = twitterRepository.findAll().size();

        // Get the twitter
        restTwitterMockMvc.perform(delete("/api/twitters/{id}", twitter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Twitter> twitterList = twitterRepository.findAll();
        assertThat(twitterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Twitter.class);
    }
}
