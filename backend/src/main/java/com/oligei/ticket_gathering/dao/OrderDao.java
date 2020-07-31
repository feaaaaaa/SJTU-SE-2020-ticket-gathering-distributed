package com.oligei.ticket_gathering.dao;

import com.oligei.ticket_gathering.entity.info.OrderInfo;
import com.oligei.ticket_gathering.entity.mysql.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    List<Order> getOrderByUserId(int userId);

    boolean addOrder(int userId, int actitemId, int price,int amount, Date showtime, Date orderTime);

    List<OrderInfo> getUserOrder(int userId);
}
