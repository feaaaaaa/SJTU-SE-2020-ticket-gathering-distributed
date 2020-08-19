package com.oligei.auction.daoimpl;

import com.oligei.auction.dao.AuctionDao;
import com.oligei.auction.entity.Auction;
import com.oligei.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AuctionDaoImpl implements AuctionDao {

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    /**
    *save an auction
    *@param: auction
    *@return: com.oligei.auction.entity.Auction
    *@author: Cui Shaojie
    *@date: 2020/8/18
    */
    public Auction save(Auction auction) {
        Objects.requireNonNull(auction,"null auction --AuctionDaoImpl save");

        return auctionRepository.save(auction);
    }

    @Override
    /**
    *get all available auctions
    *@return: java.util.List<com.oligei.auction.entity.Auction>
    *@author: Cui Shaojie
    *@date: 2020/8/18
    */
    public List<Auction> getAvailableAuctionsForNow() {
        return auctionRepository.findAuctionsByIsoverEquals(0);
    }

    @Override
    /**
    *find an auction by id
    *@param: auctionid
    *@return: com.oligei.auction.entity.Auction
    *@author: Cui Shaojie
    *@date: 2020/8/18
    */
    public Auction findOneById(Integer auctionid) {
        Objects.requireNonNull(auctionid,"null auctionid --AuctionDaoImpl findOneById");

        return auctionRepository.getOne(auctionid);
    }

}
