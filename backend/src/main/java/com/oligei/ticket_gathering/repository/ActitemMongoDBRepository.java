package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticket_gathering.entity.mysql.Actitem;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface ActitemMongoDBRepository extends MongoRepository<ActitemMongoDB,Integer> {

    ActitemMongoDB findByActitemId(Integer actitemId);

    void deleteByActitemId(Integer actitemId);

    ActitemMongoDB findActitemMongoDBByActitemId(Integer id);
}
