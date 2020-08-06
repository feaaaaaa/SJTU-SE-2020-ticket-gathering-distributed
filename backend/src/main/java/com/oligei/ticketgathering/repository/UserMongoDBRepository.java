package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.mongodb.UserMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoDBRepository extends MongoRepository<UserMongoDB,Integer> {
    UserMongoDB findByUserId(Integer userId);
}
