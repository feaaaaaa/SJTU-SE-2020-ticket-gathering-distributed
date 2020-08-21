package com.oligei.auction.controller;

import com.oligei.auction.FeignApplication;
import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.service.AuctionService;
import com.oligei.auction.util.msgutils.Msg;
import com.oligei.auction.util.msgutils.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Auction")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    private static final Logger logger = LoggerFactory.getLogger(FeignApplication.class);

//    @RequestMapping("initCache")
//    /**
//    *put all available auctions from database to cache
//    *@return: com.oligei.auction.util.msgutils.Msg<java.lang.Boolean>
//    *@author: Cui Shaojie
//    *@date: 2020/8/19
//    */
//    public Msg<Boolean> initCache()
//    {
//        auctionService.initCache();
//
//        return new Msg<>(200,"缓存初始化成功",true);
//    }


    @RequestMapping("/add")
    /**
    *save a new auction
    *@param: actitemid, ddl, showtime, initprice, orderprice, amount
    *@return: com.oligei.auction.util.msgutils.Msg<java.lang.Boolean>
    *@author: Cui Shaojie
    *@date: 2020/8/19
    */
    public Msg<Boolean> addAuction(@RequestParam("actitemid")Integer actitemid, @RequestParam("ddl")String ddl,
                          @RequestParam("showtime")String showtime, @RequestParam("initprice")Integer initprice,
                          @RequestParam("orderprice")Integer orderprice, @RequestParam("amount")Integer amount)
    {
        try {
             auctionService.save(actitemid,ddl,showtime,initprice,orderprice,amount);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<Boolean>(201,"空参数",false);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<Boolean>(201,"错误的参数属性",false);
        }
        return new Msg<Boolean>(200,"注册成功",true);
    }


    @RequestMapping("/get")
    /**
    *get all available auctions
    *@return: com.oligei.auction.util.msgutils.Msg<java.util.List<com.oligei.auction.dto.AuctionListItem>>
    *@author: Cui Shaojie
    *@date: 2020/8/19
    */
    public Msg<List<AuctionListItem>> getAuctions(){
        List<AuctionListItem> emptyAuctionListItems = new ArrayList<>();
        List<AuctionListItem> auctionListItems;
        try {
            auctionListItems = auctionService.getAvailableAuctions();
        }catch (Exception e){
            return new Msg<List<AuctionListItem>>(201,"错误返回",emptyAuctionListItems);
        }
        return new Msg<List<AuctionListItem>>(200,"成功返回",auctionListItems);
    }

    @RequestMapping("/join")
    /**
    *take part in an auction successfully
    *@param: auctionid, userid, price
    *@return: com.oligei.auction.util.msgutils.Msg<java.lang.Integer>
    *@author: Cui Shaojie
    *@date: 2020/8/19
    */
    public Msg<Integer> joinAuction(@RequestParam("auctionid")Integer auctionid,@RequestParam("userid")Integer userid,@RequestParam("price")Integer price)
    {
        Integer result;
        try {
            result = auctionService.joinAuction(auctionid,userid,price);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<Integer>(201,"空参数",-4);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<Integer>(201,"错误的参数属性",-5);
        }
        return new Msg<Integer>(200,"竞价成功",result);
    }
}
