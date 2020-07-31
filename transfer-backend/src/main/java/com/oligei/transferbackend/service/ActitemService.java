package com.oligei.transferbackend.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "ticketGathering")
public interface ActitemService {

    @RequestMapping(value = "/Actitem/detail",method = RequestMethod.GET)
    public JSONObject getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userId") Integer userId);
}
