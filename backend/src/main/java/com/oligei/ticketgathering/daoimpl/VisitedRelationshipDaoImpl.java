package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.VisitedRelationshipDao;
import com.oligei.ticketgathering.entity.neo4j.VisitedRelationship;
import com.oligei.ticketgathering.repository.ActivityNeo4jRepository;
import com.oligei.ticketgathering.repository.UserNeo4jRepository;
import com.oligei.ticketgathering.repository.VisitedRelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VisitedRelationshipDaoImpl implements VisitedRelationshipDao {

    @Autowired
    private UserNeo4jRepository userNeo4jRepository;

    @Autowired
    private ActivityNeo4jRepository activityNeo4jRepository;

    @Autowired
    private VisitedRelationshipRepository visitedRelationshipRepository;

    @Override
    public VisitedRelationship saveVisitedHistory(Integer userId, Integer activityId) {
        return visitedRelationshipRepository.saveVisitedHistory(userId.toString(),activityId.toString());
    }
}
