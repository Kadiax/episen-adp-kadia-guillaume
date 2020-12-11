package com.episen.workerconverter.repository;

import com.episen.workerconverter.model.ReportingImage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReportingImageRepository extends MongoRepository<ReportingImage, String> {
}
