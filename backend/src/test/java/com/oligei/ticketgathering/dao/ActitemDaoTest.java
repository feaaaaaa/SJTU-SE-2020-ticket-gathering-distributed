package com.oligei.ticketgathering.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.repository.ActitemMongoDBRepository;
import com.oligei.ticketgathering.repository.ActitemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActitemDaoTest {

    @Autowired
    ActitemDao actitemDao;

    @MockBean
    ActitemMongoDBRepository actitemMongoDBRepository;

    @MockBean
    ActitemRepository actitemRepository;


//    @Test
//    @Rollback
//    void findOneById() {
//        List<JSONObject> list = new ArrayList<>();
//        JSONObject object = new JSONObject();
//        object.put("test","T");
//        list.add(object);
//        Actitem actitem = new Actitem(2,1,"JuCheng");
//        actitem.setPrice(list);
//        assertEquals(actitem.getActitemId(),actitemDao.findOneById(2).getActitemId());
//        assertEquals(actitem.getActivityId(),actitemDao.findOneById(2).getActivityId());
//        assertEquals(actitem.getWebsite(),actitemDao.findOneById(2).getWebsite());
//        assertEquals(actitem.getPrice(),actitemDao.findOneById(2).getPrice());
//
//        assertEquals(actitemDao.findOneById(15).getActivityId(),5);
//    }
//
//    @Test
//    @Rollback
//    void findAllByActivityId(){
//
//        List<Actitem> actitems=actitemDao.findAllByActivityId(1);
//        for(Actitem a :actitems){
//            assertEquals(1,a.getActivityId());
//        }
//    }
//
//    @Test
//    @Rollback
//    void deleteMongoDBByActitemId(){
//        System.out.println("delete form mongoDB and check exist");
//        actitemDao.deleteMongoDBByActitemId(150);
//        assertNull(actitemMongoDBRepository.findByActitemId(150));
//    }
//
//    @Test
//    @Rollback
//    void insertActitemInMongo(){
//        String class1 = "{\"price\":\"100\",\"num\":\"200\"}";
//        String class2 = "{\"price\":\"100\",\"num\":\"200\"}";
//        String class3="{\"price\":\"300\",\"num\":\"300\"}";
//        List<JSONObject> test=new ArrayList<>();
//        JSONObject prices=JSON.parseObject(class1);
//        test.add(prices);
//        prices=JSON.parseObject(class2);
//        test.add(prices);
//        prices=JSON.parseObject(class3);
//        test.add(prices);
//        JSONObject result=new JSONObject();
//        result.put("\"time\"","2020-12-31");
//        result.put("\"classcnt\"",3);
//        result.put("\"class\"",test);
//        List<JSONObject> tmp=new ArrayList<>();
//        tmp.add(result);
//
//        ActitemMongoDB mongoData=actitemDao.insertActitemInMongo(150,tmp);
//        assertEquals(mongoData.getPrice(),tmp);
//    }

    @Test
    @Rollback
    void modifyRepository() {
        int validActitemId = 1;
        int invalidActitemId = -1;

        Actitem actitem=new Actitem(1,1,"大麦网");

        String class1 = "{\"price\":\"100\",\"num\":\"100\"}";
        String class2 = "{\"price\":\"200\",\"num\":\"200\"}";
        String class3="{\"price\":\"300\",\"num\":\"300\"}";
        List<JSONObject> test=new ArrayList<>();
        JSONObject prices=JSON.parseObject(class1);
        test.add(prices);
        prices=JSON.parseObject(class2);
        test.add(prices);
        prices=JSON.parseObject(class3);
        test.add(prices);
        JSONObject result=new JSONObject();
        result.put("time","2020-12-31");
        result.put("classcnt",3);
        result.put("class",test);
        List<JSONObject> tmp=new ArrayList<>();
        tmp.add(result);
//        System.out.println(result);

        ActitemMongoDB actitemMongoDB=new ActitemMongoDB(1,tmp);
//        ActitemMongoDB nullActitemMongoDB=new ActitemMongoDB(1,null);

        when(actitemRepository.getOne(validActitemId)).thenReturn(actitem);
        when(actitemMongoDBRepository.findByActitemId(validActitemId)).thenReturn(actitemMongoDB);

        when(actitemRepository.getOne(invalidActitemId)).thenReturn(null);
        when(actitemMongoDBRepository.findByActitemId(invalidActitemId)).thenReturn(null);

        System.out.println("using invalid actitemId to test");
        try{
            actitemDao.modifyRepository(invalidActitemId,100,2,"2020-12-31");
        }
        catch(NullPointerException e){
//            e.printStackTrace();
            assertEquals("null mongoDB data of id-1 --actitemDaoImpl findOneById",e.getMessage());
        }

        System.out.println("using invalid showtime to test");
        try{
            actitemDao.modifyRepository(validActitemId,100,2,"2020-12-30");
        }
        catch(ArrayIndexOutOfBoundsException e){
//            e.printStackTrace();
            assertEquals("null actitem --ActitemDaoImpl modifyRepository",e.getMessage());
        }

        System.out.println("using invalid price to test");
        try{
            actitemDao.modifyRepository(validActitemId,120,2,"2020-12-31");
        }
        catch(ArrayIndexOutOfBoundsException e){
//            e.printStackTrace();
            assertEquals("null actitem --ActitemDaoImpl modifyRepository",e.getMessage());
        }

        System.out.println("tickets sold out");
        try{
            actitemDao.modifyRepository(validActitemId,100,1000,"2020-12-31");
        }
        catch(ArithmeticException e){
//            e.printStackTrace();
            assertEquals("repository is zero --ActitemDaoImpl modifyRepository",e.getMessage());
        }
    }

}
