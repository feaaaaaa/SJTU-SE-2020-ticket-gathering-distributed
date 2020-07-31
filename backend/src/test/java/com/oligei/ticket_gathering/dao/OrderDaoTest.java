package com.oligei.ticket_gathering.dao;

import com.oligei.ticket_gathering.entity.mysql.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    private int existedUserId=1;
    private int noneExistedUserId=10;

    @Test
    @Rollback
    void getOrderByUserId() {
        System.out.println("using existed userId to test");
        List<Order> test=orderDao.getOrderByUserId(existedUserId);
        assertEquals(1,test.get(0).getOrderId());
        assertEquals(3,test.get(1).getOrderId());

        System.out.println("using none-existed userId to test");
        test=orderDao.getOrderByUserId(noneExistedUserId);
        assertEquals(0,test.size());
    }

    @Test
    @Rollback
    void addOrder() throws ParseException {
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        assertTrue(orderDao.addOrder(1,8,80,1,format2.parse("2020-02-21 16:00:00"),format2.parse("2020-02-21 16:00:00")));
    }
}