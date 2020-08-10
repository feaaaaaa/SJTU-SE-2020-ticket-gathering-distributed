package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.dto.ActivitySortpage;
import com.oligei.ticket_gathering.entity.neo4j.ActivityNeo4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActivityNeo4jRepositoryTest {

    @Autowired
    ActivityNeo4jRepository activityNeo4jRepository;

    @Test
    @Rollback
    void findActivityBySubcategory() {
        List<ActivityNeo4j> activityNeo4js;
        System.out.println("Correct Subcategory Name");
        activityNeo4js = activityNeo4jRepository.findActivityBySubcategory("足球");
        assertNotEquals(0,activityNeo4js.size());
        System.out.println("Wrong Subcategory Name");
        activityNeo4js = activityNeo4jRepository.findActivityBySubcategory("棒球");
        assertEquals(0,activityNeo4js.size());
    }

    @Test
    @Rollback
    void findActivityByCategory() {
        List<ActivityNeo4j> activityNeo4js;
        System.out.println("Correct Category Name");
        activityNeo4js = activityNeo4jRepository.findActivityByCategory("展览休闲");
        assertNotEquals(0,activityNeo4js.size());
        activityNeo4js = activityNeo4jRepository.findActivityByCategory("你好");
        assertEquals(0,activityNeo4js.size());
    }
}
