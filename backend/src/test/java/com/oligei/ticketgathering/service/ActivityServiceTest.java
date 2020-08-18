package com.oligei.ticketgathering.service;

import static org.junit.jupiter.api.Assertions.*;

import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    @MockBean
    private ActivityDao activityDao;

    @MockBean
    private ActitemDao actitemDao;


    @Test
    @Transactional
    @Rollback
    /**
     * test search
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void search() throws IOException, ParseException {
        Activity existedAcitivity=new Activity(1,"test","actor","timescale","venue","activityIcon");
        Actitem existedActitem=new Actitem(1,1,"123");
        List<Actitem> existedActitems=new LinkedList<>();
        existedActitems.add(existedActitem);
        for(int i=0;i<10000;++i) {
            doReturn(existedAcitivity).when(activityDao).findOneById(i);
            doReturn(existedActitems).when(actitemDao).findAllByActivityId(i);
        }
        activityService.search(null);
        activityService.search("周杰伦演唱会螺蛳粉肉丸子");
//        System.out.println("Reasonable Value");
//        assertTrue(activityService.search("周杰伦").size()>0);
//        assertTrue(activityService.search("周杰伦演唱会").size()>0);
//        assertTrue(activityService.search("周杰伦演唱会螺蛳粉肉丸子").size()>0);
//        System.out.println("Unreasonable Value");
//        assertEquals(0, activityService.search("12345").size());
//        assertEquals(0, activityService.search("螺蛳粉肉丸子").size());
    }


    @Test
    @Transactional
    @Rollback
    void recommendOnContent() {

        Integer existedUserId=1;
        Integer existedActivityId=10;
        Integer invalidUSerId=-1;
        Integer invalidActivityId=-10;
        List<Integer> integers=new LinkedList<>();
        for(Integer i=1;i<=4;++i)
            integers.add(i);
        doReturn(integers).when(activityDao).recommendOnContent(existedUserId,existedActivityId);

        Activity existedAcitivity=new Activity(1,"test","actor","timescale","venue","activityIcon");
        Actitem existedActitem=new Actitem(1,1,"123");
        List<Actitem> existedActitems=new LinkedList<>();
        existedActitems.add(existedActitem);
        for(int i=1;i<=4;++i) {
            doReturn(existedAcitivity).when(activityDao).findOneById(i);
            doReturn(existedActitems).when(actitemDao).findAllByActivityId(i);
        }

        //null
        assertThrows(NullPointerException.class,()->activityService.recommendOnContent(null,existedActivityId));
        assertThrows(NullPointerException.class,()->activityService.recommendOnContent(existedUserId,null));
        //invalid
        assertThrows(InvalidDataAccessApiUsageException.class,()->activityService.recommendOnContent(invalidUSerId,existedActivityId));
        assertThrows(InvalidDataAccessApiUsageException.class,()->activityService.recommendOnContent(existedUserId,invalidActivityId));
        //valid
        assertEquals(4, activityService.recommendOnContent(existedUserId,existedActivityId).size());

    }

//    @Test
//    @Transactional
//    @Rollback
//    void selectSearch() {
//        assertNotEquals(0,activityService.selectSearch("category","话剧歌剧","成都")
//                .size());
//        assertNotEquals(0,activityService.selectSearch("subcategory","音乐剧","成都")
//                .size());
//        assertNotEquals(0,activityService.selectSearch("123","全部","成都")
//                .size());
//        assertNotEquals(0,activityService.selectSearch("category","话剧歌剧","全国")
//                .size());
//        assertNotEquals(0,activityService.selectSearch("subcategory","音乐剧","全国")
//                .size());
//    }

    @Test
    @Transactional
    @Rollback
    /**
     *  test add
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void add(){
        assertThrows(NullPointerException.class,()->activityService.add(null));
        assertThrows(ArrayIndexOutOfBoundsException.class,()->activityService.add("[1,2,3]"));
        assertThrows(NumberFormatException.class,()->activityService.add("[1,lll,null,4,5,6,7,null,null,test]"));
    }

    @Test
    @Transactional
    @Rollback
    /**
     *  test delete
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void delete(){

        assertThrows(NullPointerException.class,()->activityService.delete(null));
        // TODO: 2020/8/17
    }

    @Test
    @Transactional
    @Rollback
    /**
     *  test clear
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void clear(){
        activityService.clear();
    }
}
