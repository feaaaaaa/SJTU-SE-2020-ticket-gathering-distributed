package com.oligei.gateway.service;

import com.alibaba.fastjson.JSONObject;
import com.oligei.gateway.dto.DetailInfo;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ticketgathering")
public interface ActitemService {

    @RequestMapping(value = "/Actitem/detail", method = RequestMethod.GET)
    public Msg<DetailInfo> getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userid") Integer userId);
}
