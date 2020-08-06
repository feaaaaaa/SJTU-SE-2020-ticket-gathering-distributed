package com.oligei.auction.service;

import com.oligei.auction.dto.AuctionListItem;

import java.util.List;


public interface AuctionService {
    Boolean save(Integer actitemid ,String ddl,String showtime, Integer initprice,Integer orderprice,Integer amount);

    List<AuctionListItem> getAvailableAuctions();

    Integer joinAuction(Integer auctionid,Integer userid,Integer orderprice);

    void flushActions();
}

