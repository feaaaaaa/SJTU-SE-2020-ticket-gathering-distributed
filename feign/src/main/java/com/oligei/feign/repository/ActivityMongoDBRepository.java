package com.oligei.feign.repository;

import com.oligei.feign.entity.ActivityMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoDBRepository extends MongoRepository<ActivityMongoDB,Integer> {
}
