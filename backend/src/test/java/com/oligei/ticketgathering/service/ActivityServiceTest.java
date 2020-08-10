package com.oligei.ticketgathering.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
    void recommendOnContent() {
        assertEquals(4, activityService.recommendOnContent(1,10).size());
    }

    @Test
    @Transactional
    @Rollback
    void selectSearch() {
        assertNotEquals(0,activityService.selectSearch("category","话剧歌剧","成都")
                .size());
        assertNotEquals(0,activityService.selectSearch("subcategory","音乐剧","成都")
                .size());
        assertNotEquals(0,activityService.selectSearch("123","全部","成都")
                .size());
        assertNotEquals(0,activityService.selectSearch("category","话剧歌剧","全国")
                .size());
        assertNotEquals(0,activityService.selectSearch("subcategory","音乐剧","全国")
                .size());
    }
}
