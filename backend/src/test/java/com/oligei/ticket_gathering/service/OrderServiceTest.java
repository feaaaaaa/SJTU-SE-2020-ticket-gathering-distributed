package com.oligei.ticket_gathering.service;

import com.oligei.ticket_gathering.entity.info.OrderInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    private int existedUserId=1;
    private int noneExistedUserId=10;

    @Test
    @Rollback
    void getOrderInfoByUser() {
        System.out.println("using existed userId to test");
        List<OrderInfo> test=orderService.getOrderInfoByUser(existedUserId);
        assertEquals(1,test.get(0).getOrderId());
        assertEquals("江南民调 新版《三笑》",test.get(0).getTitle());
        assertEquals(3,test.get(1).getOrderId());
        assertEquals("法语音乐剧《摇滚红与黑》-杭州站",test.get(1).getTitle());

        System.out.println("using none-existed userId to test");
        test=orderService.getOrderInfoByUser(noneExistedUserId);
        assertEquals(0,test.size());
    }
}