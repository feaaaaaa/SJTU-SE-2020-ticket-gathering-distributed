package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.entity.info.OrderInfo;

import java.util.List;

public interface OrderService {
    List<OrderInfo> getOrderInfoByUser(int userId);

    boolean addOrder(int userId, int actitemId, int initPrice,int orderPrice, int amount, String showtime, String orderTime);

    List<OrderInfo> getUserOrder(int userId);
}
