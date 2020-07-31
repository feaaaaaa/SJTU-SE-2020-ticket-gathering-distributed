package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.neo4j.VisitedRelationship;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface VisitedRelationshipRepository extends Neo4jRepository<VisitedRelationship,Long> {
    @Query("match (from:user{userId:$userId}),(to:activity{activityId:$activityId})\n" +
            "merge (from)-[:VISITED]->(to)")
    VisitedRelationship saveVisitedHistory(String userId, String activityId);
}
