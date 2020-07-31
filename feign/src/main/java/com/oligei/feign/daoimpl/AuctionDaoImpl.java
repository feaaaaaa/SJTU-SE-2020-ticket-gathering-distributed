package com.oligei.feign.daoimpl;

import com.oligei.feign.dao.AuctionDao;
import com.oligei.feign.entity.Auction;
import com.oligei.feign.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class AuctionDaoImpl implements AuctionDao {

    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public Boolean save(Auction auction) {
        auctionRepository.save(auction);

        return true;
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
