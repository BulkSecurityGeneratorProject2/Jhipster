package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pleasewor;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Pleasewor entity.
 */
@SuppressWarnings("unused")
public interface PleaseworRepository extends MongoRepository<Pleasewor,String> {

}
