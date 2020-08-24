package com.oligei.auction.controller;

import com.oligei.auction.FeignApplication;
import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.service.AuctionService;
import com.oligei.auction.util.msgutils.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/Auction")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    private static final Logger logger = LoggerFactory.getLogger(FeignApplication.class);

    @RequestMapping("/add")
    /**
    *save a new auction
    *@param actitemid,ddl,showtime,initprice,orderprice,amount
    *@return com.oligei.auction.util.msgutils.Msg<java.lang.Boolean>
    *@author Cui Shaojie
    *@date 2020/8/19
    */
    public Msg<Boolean> addAuction(@RequestParam("actitemid")Integer actitemid,
                                   @RequestParam("ddl")String ddl,
                                   @RequestParam("showtime")String showtime,
                                   @RequestParam("initprice")Integer initprice,
                                   @RequestParam("orderprice")Integer orderprice,
                                   @RequestParam("amount")Integer amount)
    {
        try {
             auctionService.addAuction(actitemid,ddl,showtime,initprice,orderprice,amount);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(201, "空参数", false);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(201, "错误的参数属性", false);
        }catch (ArithmeticException e) {
            logger.error("ArithmeticException",e);
            return new Msg<>(201,"容量不足",false);
        }
        return new Msg<>(200, "竞价成功加入", true);
    }

    @RequestMapping("/canEnter")
    /**
     *whether you have rights entering this auction
     *@param userid,auctionid
     *@return 200 if OK, 201 if no, 202 for exception
     *@author ziliuziliu
     *@date 2020/8/22
     */
    public Msg<Boolean> canEnter(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid) {
        try {
            boolean result = auctionService.canEnter(userid,auctionid);
            if (result) return new Msg<>(200,"准许进入",true);
            else return new Msg<>(201,"请先交保证金",false);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202,"空参数",false);
        }
    }

    @RequestMapping("/deposit")
    /**
     *deposit!
     *@param userid,auctionid
     *@return 200 if OK, 201 if not enough money, 202 for exception
     *@author ziliuziliu
     *@date 2020/8/22
     */
    public Msg<Boolean> deposit(@RequestParam("userid") Integer userid, @RequestParam("auctionid") Integer auctionid) {
        try {
            boolean result = auctionService.deposit(userid,auctionid);
            if (result) return new Msg<>(200,"成功提交保证金",true);
            else return new Msg<>(201,"余额不足或服务正在维护",false);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(202,"空参数",false);
        }
    }

    @RequestMapping("/getPrice")
    /**
     *price now for this auction
     *@param auctionid auctionid
     *@return 200 if OK, 201 for exception
     *@author ziliuziliu
     *@date 2020/8/22
     */
    public Msg<Integer> getPrice(@RequestParam("auctionid") Integer auctionid) {
        try {
            return new Msg<>(200,"当前价格",auctionService.getPrice(auctionid));
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(201,"空参数",-1);
        }
    }


    @RequestMapping("/get")
    /**
    *get all available auctions
    *@return com.oligei.auction.util.msgutils.Msg<java.util.List<com.oligei.auction.dto.AuctionListItem>>
    *@author Cui Shaojie
    *@date 2020/8/19
    */
    public Msg<Map<Integer,AuctionListItem>> getAuctions(){
        Map<Integer,AuctionListItem> auctionListItems;
        try {
            auctionListItems = auctionService.getAvailableAuctions();
        }catch (Exception e){
            return new Msg<>(201,"错误返回",null);
        }
        return new Msg<>(200,"成功返回",auctionListItems);
    }

    @RequestMapping("/join")
    /**
    *take part in an auction successfully
    *@param auctionid, userid, price
    *@return com.oligei.auction.util.msgutils.Msg<java.lang.Integer>
    *@author ziliuziliu,Cui Shaojie
    *@date 2020/8/19
    */
    public Msg<Integer> joinAuction(@RequestParam("auctionid")Integer auctionid,
                                    @RequestParam("userid")Integer userid,
                                    @RequestParam("price")Integer price)
    {
        Integer result;
        try {
            result = auctionService.joinAuction(auctionid,userid,price);
        }catch (NullPointerException e){
            logger.error("NullPointerException",e);
            return new Msg<>(201, "空参数", -4);
        }catch (InvalidDataAccessApiUsageException e){
            logger.error("InvalidDataAccessApiUsageException",e);
            return new Msg<>(201, "错误的参数属性", -5);
        }
        if (result == -1) return new Msg<>(202, "竞价条目超时", -1);
        else if (result == -2) return new Msg<>(203,"出价低",-2);
        else return new Msg<>(200,"出价成功",0);
    }
}
