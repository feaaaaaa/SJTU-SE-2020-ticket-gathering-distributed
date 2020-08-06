package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.neo4j.VisitedRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VisitedRelationshipRepository extends Neo4jRepository<VisitedRelationship,Long> {
    @Query("match (from:user{userId:$userId}),(to:activity{activityId:$activityId})\n" +
            "merge (from)-[:VISITED]->(to)")
    VisitedRelationship saveVisitedHistory(String userId, String activityId);
}
