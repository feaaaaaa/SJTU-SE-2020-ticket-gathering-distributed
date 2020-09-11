package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
//    List<Order> getOrderByUserId(int userId);

    /**
     * insert an order into database
     */
    boolean addOrder(Order saveOrder);

    /**
     * get order using user id
     */
    List<OrderInfo> getUserOrder(int userId);
}
