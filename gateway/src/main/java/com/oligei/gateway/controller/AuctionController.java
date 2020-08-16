package com.oligei.gateway.controller;

import com.oligei.gateway.dto.AuctionListItem;
import com.oligei.gateway.service.AuctionService;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auction")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @RequestMapping("/add")
    public Msg<Boolean> addAuction(@RequestParam("actitemid")Integer actitemid, @RequestParam("ddl")String ddl,
                                   @RequestParam("showtime")String showtime, @RequestParam("initprice")Integer initprice,
                                   @RequestParam("orderprice")Integer orderprice, @RequestParam("amount")Integer amount)
    {
        return auctionService.addAuction(actitemid,ddl,showtime,initprice,orderprice,amount);
    }


    @RequestMapping("/get")
    public Msg<List<AuctionListItem>> getAuctions(){
        return auctionService.getAuctions();
    }

    @RequestMapping("/join")
    public Msg<Integer> joinAuction(@RequestParam("auctionid")Integer auctionid,@RequestParam("userid")Integer userid,@RequestParam("price")Integer price)
    {
        return auctionService.joinAuction(auctionid,userid,price);
    }
}
