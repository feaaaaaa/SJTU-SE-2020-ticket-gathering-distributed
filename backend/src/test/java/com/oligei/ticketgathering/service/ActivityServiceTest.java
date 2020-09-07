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
    void search() {
        assertThrows(NullPointerException.class,()->activityService.search(null,1),
                "null value --ActivityServiceImpl search");
        assertThrows(NullPointerException.class,()->activityService.search("周杰伦",null),
                "null page --ActivityServiceImpl page");
        assertThrows(IndexOutOfBoundsException.class,()->activityService.search("周杰伦",2000),
                "找不到搜索结果");
    }

    @Test
    @Transactional
    @Rollback
    void selectSearchPageNum() {
        assertThrows(NullPointerException.class,()->activityService.selectSearchPageNum(null,"1","1"),
                "null type --ActivityServiceImpl selectSearch");
        assertThrows(NullPointerException.class,()->activityService.selectSearchPageNum("1",null,"1"),
                "null name --ActivityServiceImpl selectSearch");
        assertThrows(NullPointerException.class,()->activityService.selectSearchPageNum("1","1",null),
                "null city --ActivityServiceImpl selectSearch");
    }

    @Test
    @Transactional
    @Rollback
    void searchPageNum() {
        try {
            assertTrue(activityService.searchPageNum(null) > 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @Test
    @Transactional
    @Rollback
    /**
     *  test clear
     * @author feaaaaaa
     * @date 2020.08.18
     */
    void findActivityByCategoryHome(){

        List<Integer> integers=new LinkedList<>();
        for(Integer i=1;i<=11;++i)
            integers.add(i);
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","儿童亲子","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","话剧歌剧","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","展览休闲","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","曲苑杂坛","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","体育","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","舞蹈芭蕾","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","音乐会","全国");
        doReturn(integers).when(activityDao).findActivityByCategoryAndCity("category","演唱会","全国");

        Activity existedAcitivity=new Activity(1,"test","actor","timescale","venue","activityIcon");
        Actitem existedActitem=new Actitem(1,1,"123");
        List<Actitem> existedActitems=new LinkedList<>();
        existedActitems.add(existedActitem);
        for(int i=1;i<=11;++i) {
            doReturn(existedAcitivity).when(activityDao).findOneById(i);
            doReturn(existedActitems).when(actitemDao).findAllByActivityId(i);
        }

        assertEquals(80,activityService.findActivityByCategoryHome().size());
    }

    @Test
    @Transactional
    @Rollback
    void selectSearch() {
        assertThrows(NullPointerException.class,()->activityService.selectSearch(null,"name","city",1));
        assertThrows(NullPointerException.class,()->activityService.selectSearch("type",null,"city",1));
        assertThrows(NullPointerException.class,()->activityService.selectSearch("type","name",null,1));
        assertThrows(IndexOutOfBoundsException.class,()->activityService.selectSearch("category","话剧歌剧","成都",2000));
        assertThrows(InvalidDataAccessApiUsageException.class,()->activityService.selectSearch("type","name","city",1));
    }

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
//
//    @Test
//    @Transactional
//    @Rollback
//    /**
//     *  test delete
//     * @author feaaaaaa
//     * @date 2020.08.17
//     */
//    void delete(){
//
//        assertThrows(NullPointerException.class,()->activityService.delete(null));
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    /**
//     *  test clear
//     * @author feaaaaaa
//     * @date 2020.08.17
//     */
//    void clear(){
//        activityService.clear();
//    }
}
