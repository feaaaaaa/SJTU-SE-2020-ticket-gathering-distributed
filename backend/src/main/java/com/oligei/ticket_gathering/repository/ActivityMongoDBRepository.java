package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.mongodb.ActivityMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoDBRepository extends MongoRepository<ActivityMongoDB,Integer> {
}
