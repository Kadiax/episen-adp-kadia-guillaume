package com.episen.frontconverter.repository;

import com.episen.frontconverter.model.ImageProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePropertiesRepository extends MongoRepository<ImageProperties, String> {
}
