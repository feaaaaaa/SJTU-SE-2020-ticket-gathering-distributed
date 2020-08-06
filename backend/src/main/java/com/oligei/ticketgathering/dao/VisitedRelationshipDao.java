package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.neo4j.VisitedRelationship;

public interface VisitedRelationshipDao {
    VisitedRelationship saveVisitedHistory(Integer userId, Integer activityId);
}
