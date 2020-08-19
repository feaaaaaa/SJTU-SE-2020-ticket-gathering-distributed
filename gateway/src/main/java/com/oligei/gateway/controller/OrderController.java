package com.oligei.gateway.controller;

import com.oligei.gateway.dto.OrderInfo;
import com.oligei.gateway.service.OrderService;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/GetOrderInfoByUser")
    public Msg<List<OrderInfo>> getOrderInfoByUser(@RequestParam("userId")int userId){
        return orderService.getOrderInfoByUser(userId);
    }

    @RequestMapping("/addOrder")
    public Msg<Boolean> addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                            @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                            @RequestParam("showtime")String showtime,@RequestParam("orderTime")String orderTime){
        return orderService.addOrder(userId,actitemId,initPrice,orderPrice,amount,showtime,orderTime);
    }
}
