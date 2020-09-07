package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.neo4j.VisitedRelationship;

public interface VisitedRelationshipDao {
    /**
     * save visited history
     */
    VisitedRelationship saveVisitedHistory(Integer userId, Integer activityId);
}
