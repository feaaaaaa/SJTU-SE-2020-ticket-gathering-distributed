package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.mongodb.ActitemMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActitemMongoDBRepository extends MongoRepository<ActitemMongoDB,Integer> {

    ActitemMongoDB findByActitemId(Integer actitemId);

    void deleteByActitemId(Integer actitemId);

    ActitemMongoDB findActitemMongoDBByActitemId(Integer id);
}
