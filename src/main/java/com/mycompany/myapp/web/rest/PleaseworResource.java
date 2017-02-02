package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.PleaseworService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.PleaseworDTO;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Pleasewor.
 */
@RestController
@RequestMapping("/api")
public class PleaseworResource {

    private final Logger log = LoggerFactory.getLogger(PleaseworResource.class);

    private static final String ENTITY_NAME = "pleasewor";
        
    private final PleaseworService pleaseworService;

    public PleaseworResource(PleaseworService pleaseworService) {
        this.pleaseworService = pleaseworService;
    }

    /**
     * POST  /pleasewors : Create a new pleasewor.
     *
     * @param pleaseworDTO the pleaseworDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pleaseworDTO, or with status 400 (Bad Request) if the pleasewor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pleasewors")
    @Timed
    public ResponseEntity<PleaseworDTO> createPleasewor(@RequestBody PleaseworDTO pleaseworDTO) throws URISyntaxException {
        log.debug("REST request to save Pleasewor : {}", pleaseworDTO);
        if (pleaseworDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pleasewor cannot already have an ID")).body(null);
        }
        PleaseworDTO result = pleaseworService.save(pleaseworDTO);
        return ResponseEntity.created(new URI("/api/pleasewors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pleasewors : Updates an existing pleasewor.
     *
     * @param pleaseworDTO the pleaseworDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pleaseworDTO,
     * or with status 400 (Bad Request) if the pleaseworDTO is not valid,
     * or with status 500 (Internal Server Error) if the pleaseworDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pleasewors")
    @Timed
    public ResponseEntity<PleaseworDTO> updatePleasewor(@RequestBody PleaseworDTO pleaseworDTO) throws URISyntaxException {
        log.debug("REST request to update Pleasewor : {}", pleaseworDTO);
        if (pleaseworDTO.getId() == null) {
            return createPleasewor(pleaseworDTO);
        }
        PleaseworDTO result = pleaseworService.save(pleaseworDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pleaseworDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pleasewors : get all the pleasewors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pleasewors in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/pleasewors")
    @Timed
    public ResponseEntity<List<PleaseworDTO>> getAllPleasewors(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Pleasewors");
        Page<PleaseworDTO> page = pleaseworService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pleasewors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pleasewors/:id : get the "id" pleasewor.
     *
     * @param id the id of the pleaseworDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pleaseworDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pleasewors/{id}")
    @Timed
    public ResponseEntity<PleaseworDTO> getPleasewor(@PathVariable String id) {
        log.debug("REST request to get Pleasewor : {}", id);
        PleaseworDTO pleaseworDTO = pleaseworService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pleaseworDTO));
    }

    /**
     * DELETE  /pleasewors/:id : delete the "id" pleasewor.
     *
     * @param id the id of the pleaseworDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pleasewors/{id}")
    @Timed
    public ResponseEntity<Void> deletePleasewor(@PathVariable String id) {
        log.debug("REST request to delete Pleasewor : {}", id);
        pleaseworService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
