package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PleaseworDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Pleasewor.
 */
public interface PleaseworService {

    /**
     * Save a pleasewor.
     *
     * @param pleaseworDTO the entity to save
     * @return the persisted entity
     */
    PleaseworDTO save(PleaseworDTO pleaseworDTO);

    /**
     *  Get all the pleasewors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<PleaseworDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" pleasewor.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PleaseworDTO findOne(String id);

    /**
     *  Delete the "id" pleasewor.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
