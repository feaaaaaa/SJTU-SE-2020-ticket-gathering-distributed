package com.oligei.ticketgathering.service;

import com.alibaba.fastjson.JSONObject;
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
class ActitemServiceTest {

    @Autowired
    ActitemService actitemService;

    @Test
    @Rollback
    void findActivityAndActitemDetail() {
        List<JSONObject> list = new ArrayList<>();
        JSONObject object = new JSONObject();
        object.put("test","T");
        list.add(object);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key",1);
        jsonObject.put("title","name");
        jsonObject.put("actor","actors");
        jsonObject.put("timescale","showtime");
        jsonObject.put("venue","venue");
        jsonObject.put("activityicon","verticalPic");
        jsonObject.put("description",null);
        jsonObject.put("website","JuCheng");
        jsonObject.put("prices",list);
        assertEquals(jsonObject,actitemService.findActivityAndActitemDetail(2,1));

        assertEquals(actitemService.findActivityAndActitemDetail(15,1).get("key"),5);
    }
}
