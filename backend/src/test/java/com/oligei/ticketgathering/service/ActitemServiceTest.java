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
        assertThrows(NullPointerException.class,()->actitemService.findActivityAndActitemDetail(null,1),
                "null id --ActitemServiceImpl findActivityAndActitemDetail");
        assertThrows(NullPointerException.class,()->actitemService.findActivityAndActitemDetail(1,null),
                "null userId --ActitemServiceImpl findActivityAndActitemDetail");
    }
}
