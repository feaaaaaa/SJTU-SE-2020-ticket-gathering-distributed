package com.oligei.transferbackend.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "ticketGathering")
@RequestMapping("/Actitem")
public interface ActitemService {

    @RequestMapping("/detail")
    public JSONObject getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userId") Integer userId);
}
