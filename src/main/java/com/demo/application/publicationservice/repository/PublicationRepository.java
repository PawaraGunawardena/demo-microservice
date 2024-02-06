package com.demo.application.publicationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.demo.application.publicationservice.model.Publication;

public interface PublicationRepository extends MongoRepository<Publication, String> {
}
