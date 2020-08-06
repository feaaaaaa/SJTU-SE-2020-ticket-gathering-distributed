package com.oligei.ticketgathering.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VisitedRelationshipDaoTest {

    @Autowired
    VisitedRelationshipDao visitedRelationshipDao;

    @Test
    @Rollback
    void saveVisitedHistory() {
        Integer userId=1, activityId=15;
        System.out.println("Correct saving");
        assertEquals("1",visitedRelationshipDao.saveVisitedHistory(userId,activityId)
                                                         .getUserNeo4j().getUserId());
    }
}
