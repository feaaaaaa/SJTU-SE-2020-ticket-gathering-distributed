package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.OrderDao;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderDao orderDao;

    @MockBean
    private ActitemDao actitemDao;

    @Test
    void getUserOrder(){
        int existedUserId = 1;
        int noneExistedUserId = 10;
        int illegalUserId=-1;

        /*准备repository返回的数据*/
        List<OrderInfo> existedUserOrder = new ArrayList<>();
        OrderInfo tmp1 = new OrderInfo(1, existedUserId, 3, 100, 3, "2020-01-01",
                "2020-01-01 16:00:00", "title1", "venue1", "image1");
        OrderInfo tmp2 = new OrderInfo(3, existedUserId, 9, 200, 2, "2020-01-01",
                "2020-01-01 16:00:00", "title2", "venue2", "image2");
        OrderInfo tmp3 = new OrderInfo(5, existedUserId, 6, 300, 1, "2020-01-01",
                "2020-01-01 16:00:00", "title3", "venue3", "image3");
        existedUserOrder.add(tmp1);
        existedUserOrder.add(tmp2);
        existedUserOrder.add(tmp3);

        /*mock掉repository的调用，用thenReturn中的作为返回值，当下面调用到这个函数时，就会自动调用返回值*/
        when(orderDao.getUserOrder(existedUserId)).thenReturn(existedUserOrder);
        when(orderDao.getUserOrder(noneExistedUserId)).thenReturn(null);
        when(orderDao.getUserOrder(illegalUserId)).thenReturn(null);

        System.out.println("using existed userId to test");
        assertEquals(1, orderService.getUserOrder(existedUserId).get(0).getOrderId());
        assertEquals(3, orderService.getUserOrder(existedUserId).size());

        System.out.println("using none existed userId to test");
        try {
            orderService.getUserOrder(noneExistedUserId);
        }
        catch (NullPointerException e){
            assertEquals("null Order --OrderServiceImpl getUserOrder",e.getMessage());
        }

        System.out.println("using illegal userId to test");
        try {
            orderService.getUserOrder(illegalUserId);
        }
        catch (InvalidDataAccessApiUsageException e){
            assertEquals("illegal userId --OrderServiceImpl getUserOrder",e.getMessage());
        }
    }

//    @Test
//    void addOrder(){
//        Date Showtime=null,OrderTime=null;
//        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
//        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try{
//            Showtime=format1.parse("2020-01-01");
//            OrderTime=format2.parse("2020-02-21 16:00:00");
//        } catch (ParseException e){
//            e.printStackTrace();
//        }
//
//        when(orderDao.addOrder(1,1,100,2,Showtime,OrderTime)).thenReturn(true);
//        when(actitemDao.modifyRepository(1,100,-2,"2020-01-01")).thenReturn(true);
//
//        assertTrue(orderService.addOrder(1, 1, 100, 0,2, "2020-01-01", "2020-02-21 16:00:00"));
//        verify(orderDao,times(1)).addOrder(1,1,100,2,Showtime,OrderTime);
//    }
}
