package com.oligei.transferbackend.service;

import com.oligei.transferbackend.dto.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@FeignClient(value = "ticketGathering")
@RequestMapping("/Order")
public interface OrderService {

    @RequestMapping("/GetOrderInfoByUser")
    public List<OrderInfo> getOrderInfoByUser(@RequestParam("userId")int userId);

    @RequestMapping("/addOrder")
    public boolean addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                            @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                            @RequestParam("showtime")String showtime,@RequestParam("orderTime")String orderTime);
}
