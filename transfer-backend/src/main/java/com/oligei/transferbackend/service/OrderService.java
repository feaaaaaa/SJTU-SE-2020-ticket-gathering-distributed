package com.oligei.transferbackend.service;

import com.oligei.transferbackend.dto.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@FeignClient(value = "ticketGathering")
public interface OrderService {

    @RequestMapping(value = "/Order/GetOrderInfoByUser",method = RequestMethod.GET)
    public List<OrderInfo> getOrderInfoByUser(@RequestParam("userId")int userId);

    @RequestMapping(value = "/Order/addOrder",method = RequestMethod.POST)
    public boolean addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                            @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                            @RequestParam("showtime")String showtime,@RequestParam("orderTime")String orderTime);
}
