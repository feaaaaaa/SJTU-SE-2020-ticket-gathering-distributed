package com.oligei.ticket_gathering.service;

import com.oligei.ticket_gathering.dto.AuctionListItem;

import java.util.List;

public interface AuctionService {
    Boolean save(Integer actitemid ,String ddl,String showtime, Integer initprice,Integer orderprice,Integer amount);

    List<AuctionListItem> getAvailableAuctions();

    Integer joinAuction(Integer auctionid,Integer userid,Integer orderprice);

    void flushActions();
}
