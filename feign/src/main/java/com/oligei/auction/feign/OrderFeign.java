package com.oligei.auction.feign;

import com.oligei.auction.service.FeignConfig;
import com.oligei.auction.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "ticketgathering", configuration = FeignConfig.class)
public interface OrderFeign {

    @RequestMapping(value = "/Order/addOrder", method = RequestMethod.POST)
    Msg<Boolean> addOrder(@RequestParam("userId") int userId, @RequestParam("actitemId") int actitemId,
                          @RequestParam("initPrice") int initPrice, @RequestParam("orderPrice") int orderPrice,
                          @RequestParam("amount") int amount, @RequestParam("showtime") String showtime,
                          @RequestParam("orderTime") String orderTime);
}
