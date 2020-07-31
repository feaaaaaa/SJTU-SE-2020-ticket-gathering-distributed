package com.oligei.feign.repository;

import com.oligei.feign.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction,Integer> {

    List<Auction> findAuctionsByIsoverEquals(Integer isover);
}

