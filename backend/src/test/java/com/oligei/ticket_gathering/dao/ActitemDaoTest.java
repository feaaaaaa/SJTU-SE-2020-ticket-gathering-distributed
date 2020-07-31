package com.oligei.ticket_gathering.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticket_gathering.entity.mysql.Actitem;
import com.oligei.ticket_gathering.repository.ActitemMongoDBRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActitemDaoTest {

    @Autowired
    ActitemDao actitemDao;

    @Autowired
    ActitemMongoDBRepository actitemMongoDBRepository;


    @Test
    @Rollback
    void findOneById() {
        List<JSONObject> list = new ArrayList<>();
        JSONObject object = new JSONObject();
        object.put("test","T");
        list.add(object);
        Actitem actitem = new Actitem(2,1,"JuCheng");
        actitem.setPrice(list);
        assertEquals(actitem.getActitemId(),actitemDao.findOneById(2).getActitemId());
        assertEquals(actitem.getActivityId(),actitemDao.findOneById(2).getActivityId());
        assertEquals(actitem.getWebsite(),actitemDao.findOneById(2).getWebsite());
        assertEquals(actitem.getPrice(),actitemDao.findOneById(2).getPrice());

        assertEquals(actitemDao.findOneById(15).getActivityId(),5);
    }

    @Test
    @Rollback
    void findAllByActivityId(){

        List<Actitem> actitems=actitemDao.findAllByActivityId(1);
        for(Actitem a :actitems){
            assertEquals(1,a.getActivityId());
        }
    }

    @Test
    @Rollback
    void deleteMongoDBByActitemId(){
        System.out.println("delete form mongoDB and check exist");
        actitemDao.deleteMongoDBByActitemId(150);
        assertNull(actitemMongoDBRepository.findByActitemId(150));
    }

    @Test
    @Rollback
    void insertActitemInMongo(){
        String class1 = "{\"price\":\"100\",\"num\":\"200\"}";
        String class2 = "{\"price\":\"100\",\"num\":\"200\"}";
        String class3="{\"price\":\"300\",\"num\":\"300\"}";
        List<JSONObject> test=new ArrayList<>();
        JSONObject prices=JSON.parseObject(class1);
        test.add(prices);
        prices=JSON.parseObject(class2);
        test.add(prices);
        prices=JSON.parseObject(class3);
        test.add(prices);
        JSONObject result=new JSONObject();
        result.put("\"time\"","2020-12-31");
        result.put("\"classcnt\"",3);
        result.put("\"class\"",test);
        List<JSONObject> tmp=new ArrayList<>();
        tmp.add(result);

        ActitemMongoDB mongoData=actitemDao.insertActitemInMongo(150,tmp);
        assertEquals(mongoData.getPrice(),tmp);
    }

    @Test
    @Rollback
    void modifyRepository(){
        assertTrue(actitemDao.modifyRepository(8, 680, 5, "2020-02-22"));
    }

}