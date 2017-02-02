package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Twitter;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Twitter entity.
 */
@SuppressWarnings("unused")
public interface TwitterRepository extends MongoRepository<Twitter,String> {

}
