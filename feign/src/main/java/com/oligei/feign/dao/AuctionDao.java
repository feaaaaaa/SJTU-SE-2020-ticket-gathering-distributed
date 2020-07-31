package com.oligei.feign.dao;

import com.oligei.feign.entity.Auction;

import java.util.List;

public interface AuctionDao {

    Boolean save(Auction auction);

    List<Auction> getAvailableAuctionsForNow();

    Auction findOneById(Integer auctionid);

}