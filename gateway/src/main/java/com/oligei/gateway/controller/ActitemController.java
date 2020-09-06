package com.oligei.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import com.oligei.gateway.GatewayApplication;
import com.oligei.gateway.dto.DetailInfo;
import com.oligei.gateway.service.ActitemService;
import com.oligei.gateway.util.msgutils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actitem")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ActitemController {

    @Autowired
    private ActitemService actitemService;

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    @RequestMapping("/detail")
    public Msg<DetailInfo> getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userid") Integer userId) {
        try {
            return actitemService.getDetail(actitemid, userId);
        } catch (feign.RetryableException e) {
            logger.error("请求超时", e);
            return new Msg<>(-100, "请求超时，请重试", new DetailInfo());
        }
    }
}
