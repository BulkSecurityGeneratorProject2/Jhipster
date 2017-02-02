package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PleaseworService;
import com.mycompany.myapp.domain.Pleasewor;
import com.mycompany.myapp.repository.PleaseworRepository;
import com.mycompany.myapp.service.dto.PleaseworDTO;
import com.mycompany.myapp.service.mapper.PleaseworMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pleasewor.
 */
@Service
public class PleaseworServiceImpl implements PleaseworService{

    private final Logger log = LoggerFactory.getLogger(PleaseworServiceImpl.class);
    
    private final PleaseworRepository pleaseworRepository;

    private final PleaseworMapper pleaseworMapper;

    public PleaseworServiceImpl(PleaseworRepository pleaseworRepository, PleaseworMapper pleaseworMapper) {
        this.pleaseworRepository = pleaseworRepository;
        this.pleaseworMapper = pleaseworMapper;
    }

    /**
     * Save a pleasewor.
     *
     * @param pleaseworDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PleaseworDTO save(PleaseworDTO pleaseworDTO) {
        log.debug("Request to save Pleasewor : {}", pleaseworDTO);
        Pleasewor pleasewor = pleaseworMapper.pleaseworDTOToPleasewor(pleaseworDTO);
        pleasewor = pleaseworRepository.save(pleasewor);
        PleaseworDTO result = pleaseworMapper.pleaseworToPleaseworDTO(pleasewor);
        return result;
    }

    /**
     *  Get all the pleasewors.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<PleaseworDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pleasewors");
        Page<Pleasewor> result = pleaseworRepository.findAll(pageable);
        return result.map(pleasewor -> pleaseworMapper.pleaseworToPleaseworDTO(pleasewor));
    }

    /**
     *  Get one pleasewor by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public PleaseworDTO findOne(String id) {
        log.debug("Request to get Pleasewor : {}", id);
        Pleasewor pleasewor = pleaseworRepository.findOne(id);
        PleaseworDTO pleaseworDTO = pleaseworMapper.pleaseworToPleaseworDTO(pleasewor);
        return pleaseworDTO;
    }

    /**
     *  Delete the  pleasewor by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Pleasewor : {}", id);
        pleaseworRepository.delete(id);
    }
}
