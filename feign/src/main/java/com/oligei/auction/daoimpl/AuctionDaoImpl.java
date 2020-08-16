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
    public Auction save(Auction auction) {
        Objects.requireNonNull(auction,"null auction --AuctionDaoImpl save");

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
