package com.oligei.auction.dao;

import com.oligei.auction.entity.Auction;

import java.util.List;

public interface AuctionDao {

    /**
     * save an auction
     */
    Auction save(Auction auction);

//    /**
//     * get all available auctions
//     */
//    List<Auction> getAvailableAuctionsForNow();

    /**
     * find an auction by id
     */
    Auction findOneById(Integer auctionid);

}
