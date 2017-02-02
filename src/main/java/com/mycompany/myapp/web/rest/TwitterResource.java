package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Twitter;
import com.mycompany.myapp.service.TwitterService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Twitter.
 */
@RestController
@RequestMapping("/api")
public class TwitterResource {

    private final Logger log = LoggerFactory.getLogger(TwitterResource.class);

    private static final String ENTITY_NAME = "twitter";
        
    private final TwitterService twitterService;

    public TwitterResource(TwitterService twitterService) {
        this.twitterService = twitterService;
    }

    /**
     * POST  /twitters : Create a new twitter.
     *
     * @param twitter the twitter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new twitter, or with status 400 (Bad Request) if the twitter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/twitters")
    @Timed
    public ResponseEntity<Twitter> createTwitter(@RequestBody Twitter twitter) throws URISyntaxException {
        log.debug("REST request to save Twitter : {}", twitter);
        if (twitter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new twitter cannot already have an ID")).body(null);
        }
        Twitter result = twitterService.save(twitter);
        return ResponseEntity.created(new URI("/api/twitters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /twitters : Updates an existing twitter.
     *
     * @param twitter the twitter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated twitter,
     * or with status 400 (Bad Request) if the twitter is not valid,
     * or with status 500 (Internal Server Error) if the twitter couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/twitters")
    @Timed
    public ResponseEntity<Twitter> updateTwitter(@RequestBody Twitter twitter) throws URISyntaxException {
        log.debug("REST request to update Twitter : {}", twitter);
        if (twitter.getId() == null) {
            return createTwitter(twitter);
        }
        Twitter result = twitterService.save(twitter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, twitter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /twitters : get all the twitters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of twitters in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/twitters")
    @Timed
    public ResponseEntity<List<Twitter>> getAllTwitters(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Twitters");
        Page<Twitter> page = twitterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/twitters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /twitters/:id : get the "id" twitter.
     *
     * @param id the id of the twitter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the twitter, or with status 404 (Not Found)
     */
    @GetMapping("/twitters/{id}")
    @Timed
    public ResponseEntity<Twitter> getTwitter(@PathVariable String id) {
        log.debug("REST request to get Twitter : {}", id);
        Twitter twitter = twitterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(twitter));
    }

    /**
     * DELETE  /twitters/:id : delete the "id" twitter.
     *
     * @param id the id of the twitter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/twitters/{id}")
    @Timed
    public ResponseEntity<Void> deleteTwitter(@PathVariable String id) {
        log.debug("REST request to delete Twitter : {}", id);
        twitterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
