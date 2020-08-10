package com.oligei.feign.service;

import com.oligei.feign.dto.AuctionListItem;
import com.oligei.feign.util.Cache;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;


public interface AuctionService {
    Boolean save(Integer actitemid ,String ddl,String showtime, Integer initprice,Integer orderprice,Integer amount);

    List<AuctionListItem> getAvailableAuctions();

    Integer joinAuction(Integer auctionid,Integer userid,Integer orderprice);

    void flushActions();
}

