package com.oligei.ticket_gathering.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.oligei.ticket_gathering.entity.neo4j.LocatedRelationship;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatedRelationshipRepository extends Neo4jRepository<LocatedRelationship,Long> {
}
