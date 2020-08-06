package com.oligei.auction.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "ticketGathering",configuration = FeignConfig.class)
public interface OrderService {

    @RequestMapping(value = "/Order/addOrder",method = RequestMethod.POST)
    boolean addOrder(@RequestParam("userId")int userId, @RequestParam("actitemId")int actitemId,
                     @RequestParam("initPrice")int initPrice, @RequestParam("orderPrice")int orderPrice, @RequestParam("amount")int amount,
                     @RequestParam("showtime")String showtime,@RequestParam("orderTime")String orderTime);
}
