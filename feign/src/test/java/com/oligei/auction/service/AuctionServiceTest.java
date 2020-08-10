package com.oligei.auction.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuctionServiceTest {

    @Autowired
    private AuctionService auctionService;

    @Test
    @Rollback
    void save() {
        assertEquals(true,auctionService.save(7,"2020-10-1 12:12:12","2020-02-22",680,1000,5));
    }

    @Test
    @Rollback
    void getAvailableAuctions() {
        assertEquals(5,auctionService.getAvailableAuctions().size());
    }

    @Test
    @Rollback
    void joinAuction() {
        assertEquals(1,auctionService.joinAuction(9,2,5000));
    }
}
