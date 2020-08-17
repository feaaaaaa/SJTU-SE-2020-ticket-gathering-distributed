package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.mongodb.ActivityMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoDBRepository extends MongoRepository<ActivityMongoDB,Integer> {
    ActivityMongoDB findByActivityId(Integer activityId);
}
