package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.neo4j.VisitedRelationship;
import com.oligei.ticketgathering.repository.VisitedRelationshipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class VisitedRelationshipDaoTest {

    @Autowired
    private VisitedRelationshipDao visitedRelationshipDao;

    @MockBean
    private VisitedRelationshipRepository visitedRelationshipRepository;

    private Integer userId = 1;
    private Integer activityId = 1;

    @Test
    @Rollback
    void saveVisitedHistory() {
        VisitedRelationship visitedRelationship = new VisitedRelationship();

        when(visitedRelationshipRepository.saveVisitedHistory(userId.toString(),activityId.toString()))
                .thenReturn(visitedRelationship);

        System.out.println("Correct userId, Correct activityId");
        assertNotNull(visitedRelationshipDao.saveVisitedHistory(userId,activityId));
        verify(visitedRelationshipRepository,times(1))
                .saveVisitedHistory(userId.toString(),activityId.toString());

        System.out.println("Correct userId, Null activityId");
        assertThrows(NullPointerException.class, ()-> visitedRelationshipDao.saveVisitedHistory(userId,null),
                "null activityId --VisitedRelationshipDaoImpl saveVisitedHistory");

        System.out.println("Null userId, Correct activityId");
        assertThrows(NullPointerException.class, ()->visitedRelationshipDao.saveVisitedHistory(null,activityId),
                "null userId --VisitedRelationshipDaoImpl saveVisitedHistory");
    }
}
