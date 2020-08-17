package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.repository.ActivityRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ActivityDaoTest {


    @Autowired
    private ActivityDao activityDao;

    @MockBean
    private ActivityRepository activityRepository;


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
     * @Description test delete
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
     * @Description test delete
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
     * @Description test delete
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
     * @Description test delete
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
        assertEquals(4, activityDao.recommendOnContent(1,10).size());
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
