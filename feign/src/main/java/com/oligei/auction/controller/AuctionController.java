package com.oligei.auction.controller;

import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Auction")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @RequestMapping("/add")
    public boolean addAuction(@RequestParam("actitemid")Integer actitemid,@RequestParam("ddl")String ddl,
                              @RequestParam("showtime")String showtime,@RequestParam("initprice")Integer initprice,
                              @RequestParam("orderprice")Integer orderprice, @RequestParam("amount")Integer amount)
    {
        return auctionService.save(actitemid,ddl,showtime,initprice,orderprice,amount);
    }


    @RequestMapping("/get")
    public List<AuctionListItem> getAuctions(){
        return auctionService.getAvailableAuctions();
    }

    @RequestMapping("/join")
    public Integer joinAuction(@RequestParam("auctionid")Integer auctionid,@RequestParam("userid")Integer userid,@RequestParam("price")Integer price)
    {
        return auctionService.joinAuction(auctionid,userid,price);
    }
}
