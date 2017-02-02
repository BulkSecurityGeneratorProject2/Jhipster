package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Pleasework;

import com.mycompany.myapp.repository.PleaseworkRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Pleasework.
 */
@RestController
@RequestMapping("/api")
public class PleaseworkResource {

    private final Logger log = LoggerFactory.getLogger(PleaseworkResource.class);

    private static final String ENTITY_NAME = "pleasework";
        
    private final PleaseworkRepository pleaseworkRepository;

    public PleaseworkResource(PleaseworkRepository pleaseworkRepository) {
        this.pleaseworkRepository = pleaseworkRepository;
    }

    /**
     * POST  /pleaseworks : Create a new pleasework.
     *
     * @param pleasework the pleasework to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pleasework, or with status 400 (Bad Request) if the pleasework has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pleaseworks")
    @Timed
    public ResponseEntity<Pleasework> createPleasework(@RequestBody Pleasework pleasework) throws URISyntaxException {
        log.debug("REST request to save Pleasework : {}", pleasework);
        if (pleasework.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pleasework cannot already have an ID")).body(null);
        }
        Pleasework result = pleaseworkRepository.save(pleasework);
        return ResponseEntity.created(new URI("/api/pleaseworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pleaseworks : Updates an existing pleasework.
     *
     * @param pleasework the pleasework to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pleasework,
     * or with status 400 (Bad Request) if the pleasework is not valid,
     * or with status 500 (Internal Server Error) if the pleasework couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pleaseworks")
    @Timed
    public ResponseEntity<Pleasework> updatePleasework(@RequestBody Pleasework pleasework) throws URISyntaxException {
        log.debug("REST request to update Pleasework : {}", pleasework);
        if (pleasework.getId() == null) {
            return createPleasework(pleasework);
        }
        Pleasework result = pleaseworkRepository.save(pleasework);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pleasework.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pleaseworks : get all the pleaseworks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pleaseworks in body
     */
    @GetMapping("/pleaseworks")
    @Timed
    public List<Pleasework> getAllPleaseworks() {
        log.debug("REST request to get all Pleaseworks");
        List<Pleasework> pleaseworks = pleaseworkRepository.findAll();
        return pleaseworks;
    }

    /**
     * GET  /pleaseworks/:id : get the "id" pleasework.
     *
     * @param id the id of the pleasework to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pleasework, or with status 404 (Not Found)
     */
    @GetMapping("/pleaseworks/{id}")
    @Timed
    public ResponseEntity<Pleasework> getPleasework(@PathVariable String id) {
        log.debug("REST request to get Pleasework : {}", id);
        Pleasework pleasework = pleaseworkRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pleasework));
    }

    /**
     * DELETE  /pleaseworks/:id : delete the "id" pleasework.
     *
     * @param id the id of the pleasework to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pleaseworks/{id}")
    @Timed
    public ResponseEntity<Void> deletePleasework(@PathVariable String id) {
        log.debug("REST request to delete Pleasework : {}", id);
        pleaseworkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
