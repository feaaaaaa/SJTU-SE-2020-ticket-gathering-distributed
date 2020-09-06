package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Order;
import com.oligei.ticketgathering.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void getUserOrder() {
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
        when(orderRepository.getUserOrder(existedUserId)).thenReturn(existedUserOrder);
        when(orderRepository.getUserOrder(noneExistedUserId)).thenReturn(null);
        when(orderRepository.getUserOrder(illegalUserId)).thenReturn(null);

        System.out.println("using existed userId to test");
        assertEquals(1, orderDao.getUserOrder(existedUserId).get(0).getOrderId());
        assertEquals(3, orderDao.getUserOrder(existedUserId).size());

        System.out.println("using none existed userId to test");
        try {
            orderDao.getUserOrder(noneExistedUserId);
        }
        catch (NullPointerException e){
            assertEquals("null Order --OrderDaoImpl getUserOrder",e.getMessage());
        }

        System.out.println("using illegal userId to test");
        try {
            orderDao.getUserOrder(illegalUserId);
        }
        catch (InvalidDataAccessApiUsageException e){
            assertEquals("illegal userId --OrderDaoImpl getUserOrder",e.getMessage());
        }
    }

    @Test
    void addOrder(){
        Date Showtime=null,OrderTime=null;
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Showtime=format1.parse("2020-01-01");
            OrderTime=format2.parse("2020-02-21 16:00:00");
        } catch (ParseException e){
            e.printStackTrace();
        }
        Order saveOrder=new Order(1,1,1,100,2,Showtime,OrderTime);

        /*any(anyInt,anyString同理)的用法*/
        when(orderRepository.save(any(Order.class))).thenReturn(saveOrder);

        assertTrue(orderDao.addOrder(1, 1, 100, 2, Showtime, OrderTime));
        verify(orderRepository,times(1)).save(any(Order.class));

        /*自定义匹配的类型*/
        when(orderRepository.save(argThat(Objects::nonNull))).thenReturn(saveOrder);
        assertTrue(orderDao.addOrder(1, 1, 100, 2, Showtime, OrderTime));

        /*verify+time 判断该方法调用了几次*/
        verify(orderRepository,times(2)).save(argThat(Objects::nonNull));

    }
}
