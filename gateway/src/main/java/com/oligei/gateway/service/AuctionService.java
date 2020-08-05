package com.oligei.gateway.service;

import com.oligei.gateway.dto.AuctionListItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "auction-feign")
public interface AuctionService {

    @RequestMapping(value = "/Auction/add",method = RequestMethod.POST)
    public boolean addAuction(@RequestParam("actitemid")Integer actitemid,@RequestParam("ddl")String ddl,
                              @RequestParam("showtime")String showtime,@RequestParam("initprice")Integer initprice,
                              @RequestParam("orderprice")Integer orderprice, @RequestParam("amount")Integer amount);


    @RequestMapping(value = "/Auction/get",method = RequestMethod.GET)
    public List<AuctionListItem> getAuctions();

    @RequestMapping(value = "/Auction/join",method = RequestMethod.POST)
    public Integer joinAuction(@RequestParam("auctionid")Integer auctionid,@RequestParam("userid")Integer userid,@RequestParam("price")Integer price);
}
