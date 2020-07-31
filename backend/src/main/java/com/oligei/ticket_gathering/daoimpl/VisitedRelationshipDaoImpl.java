package com.oligei.ticket_gathering.daoimpl;

import com.oligei.ticket_gathering.dao.VisitedRelationshipDao;
import com.oligei.ticket_gathering.entity.neo4j.ActivityNeo4j;
import com.oligei.ticket_gathering.entity.neo4j.UserNeo4j;
import com.oligei.ticket_gathering.entity.neo4j.VisitedRelationship;
import com.oligei.ticket_gathering.repository.ActivityNeo4jRepository;
import com.oligei.ticket_gathering.repository.UserNeo4jRepository;
import com.oligei.ticket_gathering.repository.VisitedRelationshipRepository;
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
