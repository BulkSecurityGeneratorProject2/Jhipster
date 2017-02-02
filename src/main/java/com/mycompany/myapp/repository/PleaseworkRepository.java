package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pleasework;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Pleasework entity.
 */
@SuppressWarnings("unused")
public interface PleaseworkRepository extends MongoRepository<Pleasework,String> {

}
