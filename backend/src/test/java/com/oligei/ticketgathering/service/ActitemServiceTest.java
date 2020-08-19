package com.oligei.ticketgathering.service;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.dao.VisitedRelationshipDao;
import com.oligei.ticketgathering.dto.DetailInfo;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ActitemServiceTest {

    @Autowired
    ActitemService actitemService;

    @MockBean
    ActitemDao actitemDao;

    @MockBean
    ActivityDao activityDao;

    @MockBean
    VisitedRelationshipDao visitedRelationshipDao;

    @Test
    @Rollback
    void findActivityAndActitemDetail() {
        DetailInfo detailInfo = new DetailInfo(1,"title","actor","timescale","venue","icon","descrption","website",null);
        Activity activity = new Activity(1,"title","actor","timescale","venue","icon");
        Actitem actitem = new Actitem(2,1,"website",null);

        when(actitemDao.findOneById(2)).thenReturn(actitem);
        when(activityDao.findOneById(1)).thenReturn(activity);
        when(actitemDao.findOneById(4)).thenReturn(null);
        when(visitedRelationshipDao.saveVisitedHistory(1,2)).thenReturn(null);

        assertEquals(detailInfo.getKey(),actitemService.findActivityAndActitemDetail(2,1).getKey());

        try {
            actitemService.findActivityAndActitemDetail(4,1);
        }
        catch (NullPointerException e){
            assertEquals("Actitem Not Found",e.getMessage());
        }
//        List<JSONObject> list = new ArrayList<>();
//        JSONObject object = new JSONObject();
//        object.put("test","T");
//        list.add(object);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("key",1);
//        jsonObject.put("title","name");
//        jsonObject.put("actor","actors");
//        jsonObject.put("timescale","showtime");
//        jsonObject.put("venue","venue");
//        jsonObject.put("activityicon","verticalPic");
//        jsonObject.put("description",null);
//        jsonObject.put("website","JuCheng");
//        jsonObject.put("prices",list);
//        assertEquals(jsonObject,actitemService.findActivityAndActitemDetail(2,1));
//
//        assertEquals(actitemService.findActivityAndActitemDetail(15,1).get("key"),5);
    }
}
