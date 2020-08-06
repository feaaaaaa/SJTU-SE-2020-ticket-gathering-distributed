package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.neo4j.CityNeo4j;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityNeo4jRepository extends Neo4jRepository<CityNeo4j,Long> {
    CityNeo4j findByName(String name);
}
