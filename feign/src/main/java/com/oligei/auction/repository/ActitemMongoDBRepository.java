package com.oligei.auction.repository;

import com.oligei.auction.entity.ActitemMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActitemMongoDBRepository extends MongoRepository<ActitemMongoDB,Integer> {

    ActitemMongoDB findByActitemId(Integer actitemId);

    void deleteByActitemId(Integer actitemId);

    ActitemMongoDB findActitemMongoDBByActitemId(Integer id);
}
