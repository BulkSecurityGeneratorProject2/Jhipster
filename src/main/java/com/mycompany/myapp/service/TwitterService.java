package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Twitter;
import com.mycompany.myapp.repository.TwitterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Twitter.
 */
@Service
public class TwitterService {

    private final Logger log = LoggerFactory.getLogger(TwitterService.class);
    
    private final TwitterRepository twitterRepository;

    public TwitterService(TwitterRepository twitterRepository) {
        this.twitterRepository = twitterRepository;
    }

    /**
     * Save a twitter.
     *
     * @param twitter the entity to save
     * @return the persisted entity
     */
    public Twitter save(Twitter twitter) {
        log.debug("Request to save Twitter : {}", twitter);
        Twitter result = twitterRepository.save(twitter);
        return result;
    }

    /**
     *  Get all the twitters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<Twitter> findAll(Pageable pageable) {
        log.debug("Request to get all Twitters");
        Page<Twitter> result = twitterRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one twitter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Twitter findOne(String id) {
        log.debug("Request to get Twitter : {}", id);
        Twitter twitter = twitterRepository.findOne(id);
        return twitter;
    }

    /**
     *  Delete the  twitter by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Twitter : {}", id);
        twitterRepository.delete(id);
    }
}
