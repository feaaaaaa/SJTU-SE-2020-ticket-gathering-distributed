package com.oligei.auction.feign;

import com.oligei.auction.service.FeignConfig;
import com.oligei.auction.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "ticketGathering",configuration = FeignConfig.class)
public interface UserFeign {

    @RequestMapping(value = "/User/rechargeOrDeduct")
    Msg rechargeOrDeduct(@RequestParam(name = "userid")Integer userid, @RequestParam(name = "increment")Integer increment);
    }
