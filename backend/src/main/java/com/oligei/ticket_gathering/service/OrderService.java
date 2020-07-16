package com.oligei.ticket_gathering.service;

import com.oligei.ticket_gathering.entity.info.OrderInfo;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<OrderInfo> getOrderInfoByUser(int userId);

    boolean addOrder(int userId, int actitemId, int price, int amount, String showtime, String orderTime);
}
