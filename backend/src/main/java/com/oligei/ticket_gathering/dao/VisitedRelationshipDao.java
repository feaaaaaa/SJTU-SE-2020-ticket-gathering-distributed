package com.oligei.ticket_gathering.dao;

import com.oligei.ticket_gathering.entity.neo4j.VisitedRelationship;

public interface VisitedRelationshipDao {
    VisitedRelationship saveVisitedHistory(Integer userId, Integer activityId);
}
