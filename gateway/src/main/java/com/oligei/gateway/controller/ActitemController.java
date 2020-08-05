package com.oligei.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.oligei.gateway.service.ActitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actitem")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActitemController {

    @Autowired
    private ActitemService actitemService;

    @RequestMapping("/detail")
    public JSONObject getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userId") Integer userId)
    {return actitemService.getDetail(actitemid,userId);}
}
