package com.oligei.auction.repository;

import com.oligei.auction.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Integer> {

    List<Auction> findAuctionsByIsoverEquals(Integer isover);
}

