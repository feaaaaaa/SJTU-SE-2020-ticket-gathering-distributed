package com.oligei.transferbackend.service;

import com.oligei.transferbackend.dto.AuctionListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/Auction")
@FeignClient(value = "auction-feign")
public interface AuctionService {

    @RequestMapping("/add")
    public boolean addAuction(@RequestParam("actitemid")Integer actitemid,@RequestParam("ddl")String ddl,
                              @RequestParam("showtime")String showtime,@RequestParam("initprice")Integer initprice,
                              @RequestParam("orderprice")Integer orderprice, @RequestParam("amount")Integer amount);


    @RequestMapping("/get")
    public List<AuctionListItem> getAuctions();

    @RequestMapping("/join")
    public Integer joinAuction(@RequestParam("auctionid")Integer auctionid,@RequestParam("userid")Integer userid,@RequestParam("price")Integer price);
}
