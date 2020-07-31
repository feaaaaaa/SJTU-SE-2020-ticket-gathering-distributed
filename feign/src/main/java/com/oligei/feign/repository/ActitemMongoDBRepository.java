package com.oligei.feign.repository;

import com.oligei.feign.entity.ActitemMongoDB;
import com.oligei.feign.entity.Actitem;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ActitemMongoDBRepository extends MongoRepository<ActitemMongoDB,Integer> {

    ActitemMongoDB findByActitemId(Integer actitemId);

    void deleteByActitemId(Integer actitemId);

    ActitemMongoDB findActitemMongoDBByActitemId(Integer id);
}
