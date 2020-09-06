package com.oligei.auction.service;

import com.oligei.auction.dto.AuctionListItem;

import java.util.List;
import java.util.Map;


public interface AuctionService {
    /**
     * save an auction
     */
    void addAuction(Integer actitemid, String ddl, String showtime,
                    Integer initprice, Integer orderprice, Integer amount) throws ArithmeticException;

    /**
     * get all available auctions
     */
    Map<Integer, AuctionListItem> getAvailableAuctions();

    /**
     * take part in an auction successfully
     */
    Integer joinAuction(Integer auctionid, Integer userid, Integer orderprice);

    /**
     * whether you can enter this auction
     */
    Boolean canEnter(Integer userid, Integer auctionid) throws NullPointerException;

    /**
     * deposit!
     */
    Boolean deposit(Integer userid, Integer auctionid) throws NullPointerException;

    /**
     * price now for this auction
     */
    Integer getPrice(Integer auctionid);
    Boolean flushAuctions();
    Boolean whenSetOver(AuctionListItem auctionListItem);
}

