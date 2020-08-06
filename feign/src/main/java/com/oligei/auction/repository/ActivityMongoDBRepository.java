package com.oligei.auction.repository;

import com.oligei.auction.entity.ActivityMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityMongoDBRepository extends MongoRepository<ActivityMongoDB,Integer> {
}
