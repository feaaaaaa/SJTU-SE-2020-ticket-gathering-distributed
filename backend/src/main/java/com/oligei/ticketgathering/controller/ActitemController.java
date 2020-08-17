package com.oligei.ticketgathering.controller;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.TicketGatheringApplication;
import com.oligei.ticketgathering.dto.DetailInfo;
import com.oligei.ticketgathering.service.ActitemService;
import com.oligei.ticketgathering.util.msgutils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Actitem")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActitemController {

    @Autowired
    private ActitemService actitemService;

    private static final Logger logger = LoggerFactory.getLogger(TicketGatheringApplication.class);


    @RequestMapping("/detail")
    public Msg<DetailInfo> getDetail(@RequestParam(name = "actitemid") Integer actitemid, @RequestParam(name = "userid") Integer userId)
    {
        DetailInfo detailInfo;
        try{
            detailInfo =  actitemService.findActivityAndActitemDetail(actitemid,userId);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<DetailInfo>(201,"空参数",null);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<DetailInfo>(201,"错误的参数属性",null);
        }
        return new Msg<DetailInfo>(200,"注册成功",detailInfo);
    }
}
