package com.oligei.ticket_gathering.service;

import static org.junit.jupiter.api.Assertions.*;

import com.oligei.ticket_gathering.dto.ActivitySortpage;
import com.oligei.ticket_gathering.entity.neo4j.ActivityNeo4j;
import com.oligei.ticket_gathering.util.CategoryQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @Test
    @Transactional
    @Rollback
    void findActivityAndActitem(){
        System.out.println("Correct ActivityId");
        assertEquals(activityService.findActivityAndActitem(1).getActivityId(),1);
        assertEquals(activityService.findActivityAndActitem(100).getActivityId(),100);
        assertEquals(activityService.findActivityAndActitem(1000).getActivityId(),1000);
//        System.out.println("Wrong ActivityId");
//        assertNull(activityService.findActivityAndActitem(1500));
    }

    @Test
    @Transactional
    @Rollback
    void search(){
        System.out.println("Null Value");
        assertTrue(activityService.search(null).size()>0);
        System.out.println("Preparation");
        activityService.search("周杰伦演唱会");
        System.out.println("Reasonable Value");
        assertTrue(activityService.search("周杰伦").size()>0);
        assertTrue(activityService.search("周杰伦演唱会").size()>0);
        assertTrue(activityService.search("周杰伦演唱会螺蛳粉肉丸子").size()>0);
        System.out.println("Unreasonable Value");
        assertEquals(0, activityService.search("12345").size());
        assertEquals(0, activityService.search("螺蛳粉肉丸子").size());
    }

    @Test
    @Transactional
    @Rollback
    void findActivityByCategory() {
        List<ActivitySortpage> activitySortpages;
        CategoryQuery categoryQuery1 = new CategoryQuery("category","舞蹈芭蕾");
        CategoryQuery categoryQuery2 = new CategoryQuery("subcategory","其他儿童亲子");
        CategoryQuery categoryQuery3 = new CategoryQuery("1234","5678");
        System.out.println("Category");
        activitySortpages = activityService.findActivityByCategory(categoryQuery1);
        assertNotEquals(0,activitySortpages.size());
        System.out.println("Subcategory");
        activitySortpages = activityService.findActivityByCategory(categoryQuery2);
        assertNotEquals(0,activitySortpages.size());
        System.out.println("Wrong Query");
        activitySortpages = activityService.findActivityByCategory(categoryQuery3);
        assertNull(activitySortpages);
    }
}