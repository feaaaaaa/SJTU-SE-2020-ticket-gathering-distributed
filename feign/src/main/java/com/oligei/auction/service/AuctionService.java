package com.oligei.auction.service;

import com.oligei.auction.dto.AuctionListItem;
import java.util.List;
import java.util.Map;


public interface AuctionService {
    void addAuction(Integer actitemid , String ddl, String showtime,
                    Integer initprice, Integer orderprice, Integer amount) throws ArithmeticException;
    Map<Integer,AuctionListItem> getAvailableAuctions();
    Integer joinAuction(Integer auctionid,Integer userid,Integer orderprice);
    Boolean canEnter(Integer userid, Integer auctionid) throws NullPointerException;
    Boolean deposit(Integer userid, Integer auctionid) throws NullPointerException;
    Integer getPrice(Integer auctionid);
}

