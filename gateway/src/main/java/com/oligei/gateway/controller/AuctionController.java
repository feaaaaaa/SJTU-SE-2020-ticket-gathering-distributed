package com.oligei.gateway.controller;

import com.oligei.gateway.GatewayApplication;
import com.oligei.gateway.dto.AuctionListItem;
import com.oligei.gateway.service.AuctionService;
import com.oligei.gateway.util.msgutils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auction")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);


    @RequestMapping("/add")
    public Msg<Boolean> addAuction(@RequestParam("actitemid")Integer actitemid,
                                    @RequestParam("ddl")String ddl,
                                    @RequestParam("showtime")String showtime,
                                    @RequestParam("initprice")Integer initprice,
                                    @RequestParam("orderprice")Integer orderprice,
                                    @RequestParam("amount")Integer amount)
    {
        try {
            return auctionService.addAuction(actitemid, ddl, showtime, initprice, orderprice, amount);
        }catch (feign.RetryableException e){
            logger.error("请求超时",e);
            return new Msg<>(504,"请求超时",false);
        }
    }


    @RequestMapping("/get")
    public Msg<Map<Integer,AuctionListItem>> getAuctions(){
        try {
            return auctionService.getAuctions();
        }catch (feign.RetryableException e){
            return new Msg<>(504, "请求超时", null);
        }
    }

    @RequestMapping("/join")
    public Msg<Integer> joinAuction(@RequestParam("auctionid") java.lang.Integer auctionid,
                                    @RequestParam("userid") java.lang.Integer userid,
                                    @RequestParam("price") java.lang.Integer price)
    {
        try {
            return auctionService.joinAuction(auctionid, userid, price);
        }catch (feign.RetryableException e){
            return new Msg<>(504,"请求超时",-4);
        }
    }

    @RequestMapping("/canEnter")
    Msg<Boolean> canEnter(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid) {
        try {
            return auctionService.canEnter(userid,auctionid);
        }catch (feign.RetryableException e){
            return new Msg<>(504,"请求超时",false);
        }
    }

    @RequestMapping("/deposit")
    public Msg<Boolean> deposit(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid) {
        try {
            return auctionService.deposit(userid,auctionid);
        }catch (feign.RetryableException e){
            return new Msg<>(504,"请求超时",false);
        }
    }

    @RequestMapping("/getPrice")
    public Msg<Integer> getPrice(@RequestParam("auctionid") Integer auctionid) {
        try {
            return auctionService.getPrice(auctionid);
        }catch (feign.RetryableException e){
            return new Msg<>(504,"请求超时",-4);
        }
    }
}
