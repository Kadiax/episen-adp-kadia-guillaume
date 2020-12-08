package com.episen.workerconverter.repository;

import com.episen.workerconverter.model.ImageProperties;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagePropertiesRepository extends MongoRepository<ImageProperties, String> {
}
