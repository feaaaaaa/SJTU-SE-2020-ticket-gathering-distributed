package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.neo4j.ActivityNeo4j;
import com.oligei.ticketgathering.repository.ActivityNeo4jRepository;
import com.oligei.ticketgathering.repository.ActivityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActivityDaoTest {


    @Autowired
    private ActivityDao activityDao;

    @MockBean
    private ActivityRepository activityRepository;

    @MockBean
    private ActivityNeo4jRepository activityNeo4jRepository;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Transactional
    @Rollback
    /**
     * test delete
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void findOneById() {

        int existedId=1;
        int nonExistedId1=100;
        int nonExistedId2=0;
        int nonExistedId3=-1;
        Activity existedAcitivity=new Activity(existedId,"test","actor","timescale","venue","activityIcon");
        doReturn(existedAcitivity).when(activityRepository).getOne(existedId);
        JpaObjectRetrievalFailureException e=new JpaObjectRetrievalFailureException(new EntityNotFoundException());
        doThrow(e).when(activityRepository).getOne(nonExistedId1);
        doThrow(e).when(activityRepository).getOne(nonExistedId2);
        doThrow(e).when(activityRepository).getOne(nonExistedId3);
//        when(activityRepository.getOne(existedId)).thenReturn(existedAcitivity);
//        when(activityRepository.getOne(nonExistedId1)).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));
//        when(activityRepository.getOne(nonExistedId2)).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));
//        when(activityRepository.getOne(nonExistedId3)).thenThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException()));


        //Invalid ActivityId
        assertThrows(JpaObjectRetrievalFailureException.class,()->activityDao.findOneById(nonExistedId2));
        assertThrows(JpaObjectRetrievalFailureException.class,()->activityDao.findOneById(nonExistedId3));

        //Valid ActivityId
        assertEquals(activityDao.findOneById(existedId).getActivityId(),existedId);
        assertThrows(JpaObjectRetrievalFailureException.class,()->activityDao.findOneById(nonExistedId1));

    }

    @Test
    @Transactional
    @Rollback
    /**
     *  test delete
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void add(){
        assertThrows(NullPointerException.class,()->activityDao.add(null,"test","test","test","test"));
        assertThrows(NullPointerException.class,()->activityDao.add("test",null,"test","test","test"));
        assertThrows(NullPointerException.class,()->activityDao.add("test","test",null,"test","test"));
        assertThrows(NullPointerException.class,()->activityDao.add("test","test","test",null,"test"));
        assertThrows(NullPointerException.class,()->activityDao.add("test","test","test","test",null));
        activityDao.add("test","test","test","test","test");
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
        int invalidId1=0;
        int invalidId2=-1;
        int invalidId3=1000000;
        doThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException())).when(activityRepository).deleteById(invalidId1);
        doThrow(new JpaObjectRetrievalFailureException(new EntityNotFoundException())).when(activityRepository).deleteById(invalidId2);
        doThrow(new EmptyResultDataAccessException(1)).when(activityRepository).deleteById(invalidId3);

        //null
        assertThrows(NullPointerException.class,()->activityDao.delete(null));
        //invalid
        assertThrows(JpaObjectRetrievalFailureException.class,()->activityDao.delete(invalidId1));
        assertThrows(JpaObjectRetrievalFailureException.class,()->activityDao.delete(invalidId2));
        //too large
        assertThrows(EmptyResultDataAccessException.class,()->activityDao.delete(invalidId3));
    }

    @Test
    @Transactional
    @Rollback
    /**
     *  test delete
     * @author feaaaaaa
     * @date 2020.08.17
     */
    void findMaxActivityId(){
        activityDao.findMaxActivityId();
    }

//    @Test
//    @Transactional
//    @Rollback
//    void findAllByTitleOrVenueOrActor() {
//        System.out.println("Reasonable Value");
//        String searchvalue1 = "%上海%";
//        assertTrue(activityDao.findAllByTitleOrVenueOrActor(searchvalue1, searchvalue1, searchvalue1).size()>0);
//        String searchvalue2 = "%周杰伦%";
//        assertTrue(activityDao.findAllByTitleOrVenueOrActor(searchvalue2, searchvalue2, searchvalue2).size()>0);
//        System.out.println("Unreasonable Value");
//        String searchvalue3 = "%螺蛳粉%";
//        assertEquals(0, activityDao.findAllByTitleOrVenueOrActor(searchvalue3, searchvalue3, searchvalue3).size());
//        System.out.println("Reasonable and Unreasonable");
//        assertTrue(activityDao.findAllByTitleOrVenueOrActor(searchvalue1, searchvalue2, searchvalue3).size()>0);
//        assertTrue(activityDao.findAllByTitleOrVenueOrActor(searchvalue1, searchvalue3, searchvalue3).size()>0);
//    }


    @Test
    @Transactional
    @Rollback
    void recommendOnContent() {

        String SexistedUserId="1";
        Integer IexistedUserId=1;
        String SexistedActivityId="10";
        Integer IexistedActivityId=10;
        Integer IinvalidUSerId=-1;
        Integer IinvalidActivityId=-10;
        ActivityNeo4j activityNeo4j1=new ActivityNeo4j("1","舞蹈芭蕾","舞蹈","成都");
        ActivityNeo4j activityNeo4j2=new ActivityNeo4j("2","舞蹈芭蕾","舞蹈","成都");
        ActivityNeo4j activityNeo4j3=new ActivityNeo4j("3","舞蹈芭蕾","舞蹈","成都");
        ActivityNeo4j activityNeo4j4=new ActivityNeo4j("4","舞蹈芭蕾","舞蹈","成都");
        List<ActivityNeo4j> activityNeo4js=new LinkedList<>();
        activityNeo4js.add(activityNeo4j1);
        activityNeo4js.add(activityNeo4j2);
        activityNeo4js.add(activityNeo4j3);
        activityNeo4js.add(activityNeo4j4);
        doReturn(activityNeo4js).when(activityNeo4jRepository).recommendOnContent(SexistedUserId,SexistedActivityId);

        //valid
        assertEquals(4, activityDao.recommendOnContent(IexistedUserId,IexistedActivityId).size());
        //invalid
        assertThrows(InvalidDataAccessApiUsageException.class,()->activityDao.recommendOnContent(IinvalidUSerId,IexistedActivityId));
        assertThrows(InvalidDataAccessApiUsageException.class,()->activityDao.recommendOnContent(IexistedUserId,IinvalidActivityId));
        //null
        assertThrows(NullPointerException.class,()->activityDao.recommendOnContent(null,IexistedActivityId));
        assertThrows(NullPointerException.class,()->activityDao.recommendOnContent(IexistedUserId,null));
    }

    @Test
    @Transactional
    @Rollback
    void findActivityByCategoryAndCity() {
        assertNotEquals(0,activityDao.findActivityByCategoryAndCity("category","话剧歌剧","成都")
                .size());
        assertNotEquals(0,activityDao.findActivityByCategoryAndCity("subcategory","音乐剧","成都")
                .size());
        assertNotEquals(0,activityDao.findActivityByCategoryAndCity("123","全部","成都")
                .size());
        assertNotEquals(0,activityDao.findActivityByCategoryAndCity("category","话剧歌剧","全国")
                .size());
        assertNotEquals(0,activityDao.findActivityByCategoryAndCity("subcategory","音乐剧","全国")
                .size());
    }

    @Test
    @Transactional
    @Rollback
    void addActivityNeo4j() {

    }
}
