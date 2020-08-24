package com.oligei.gateway.service;

import com.oligei.gateway.dto.AuctionListItem;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "auction-feign")
public interface AuctionService {

    @RequestMapping(value = "/Auction/add",method = RequestMethod.POST)
    Msg<Boolean> addAuction(@RequestParam("actitemid")Integer actitemid,
                                   @RequestParam("ddl")String ddl,
                                   @RequestParam("showtime")String showtime,
                                   @RequestParam("initprice")Integer initprice,
                                   @RequestParam("orderprice")Integer orderprice,
                                   @RequestParam("amount")Integer amount);


    @RequestMapping(value = "/Auction/get",method = RequestMethod.GET)
    Msg<Map<Integer,AuctionListItem>> getAuctions();

    @RequestMapping(value = "/Auction/join",method = RequestMethod.POST)
    Msg<Integer> joinAuction(@RequestParam("auctionid") java.lang.Integer auctionid,
                                          @RequestParam("userid") java.lang.Integer userid,
                                          @RequestParam("price") java.lang.Integer price);

    @RequestMapping("/Auction/canEnter")
    Msg<Boolean> canEnter(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid);

    @RequestMapping("/Auction/deposit")
    Msg<Boolean> deposit(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid);

    @RequestMapping("/Auction/getPrice")
    Msg<Integer> getPrice(@RequestParam("auctionid") Integer auctionid);
}
