package com.oligei.auction.daoimpl;

import com.oligei.auction.dao.AuctionDao;
import com.oligei.auction.entity.Auction;
import com.oligei.auction.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AuctionDaoImpl implements AuctionDao {

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public List<Auction> getAvailableAuctionsForNow() {
        return auctionRepository.findAuctionsByIsoverEquals(0);
    }

    @Override
    public Auction findOneById(Integer auctionid) {
        return auctionRepository.getOne(auctionid);
    }

}
