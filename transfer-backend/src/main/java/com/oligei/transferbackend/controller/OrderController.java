package com.oligei.transferbackend.controller;

import com.oligei.transferbackend.dto.OrderInfo;
import com.oligei.transferbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*",maxAge = 3600)
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/GetOrderInfoByUser")
    public List<OrderInfo> getOrderInfoByUser(@RequestParam("userId")int userId){
        return orderService.getOrderInfoByUser(userId);
    }

    @RequestMapping("/addOrder")
    public boolean addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                            @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                            @RequestParam("showtime")String showtime,@RequestParam("orderTime")String orderTime){
        return orderService.addOrder(userId,actitemId,initPrice,orderPrice,amount,showtime,orderTime);
    }
}
