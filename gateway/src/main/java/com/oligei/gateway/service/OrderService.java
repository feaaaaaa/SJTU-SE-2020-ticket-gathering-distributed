package com.oligei.gateway.service;

import com.oligei.gateway.dto.OrderInfo;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ticketgathering")
public interface OrderService {

    @RequestMapping(value = "/Order/GetOrderInfoByUser",method = RequestMethod.GET)
    public Msg<List<OrderInfo>> getOrderInfoByUser(@RequestParam("userId")int userId);

    @RequestMapping(value = "/Order/addOrder",method = RequestMethod.POST)
    public Msg<Boolean> addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                                 @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                                 @RequestParam("showtime")String showtime, @RequestParam("orderTime")String orderTime);
}
