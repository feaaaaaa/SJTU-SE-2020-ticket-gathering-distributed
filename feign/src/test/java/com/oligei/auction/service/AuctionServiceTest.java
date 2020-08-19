package com.oligei.auction.service;

import com.oligei.auction.dao.AuctionDao;
import com.oligei.auction.entity.Auction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/*
由于该层用到了静态变量cache，而无法mock掉cache，所以不得已和数据库相关，测试数据库为：
 */
@SpringBootTest
class AuctionServiceTest {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionDao auctionDao;

    @Test
    @Rollback
    void save() {
        assertEquals(true,auctionService.save(7,"2020-10-1 12:12:12","2020-02-22",680,1000,5));
        verify(auctionDao,times(1)).save(any(Auction.class));
    }

    @Test
    @Rollback
    void getAvailableAuctions() {
        assertEquals(2,auctionService.getAvailableAuctions().size());
    }

    @Test
    @Rollback
    void joinAuction() {
        assertEquals(1,auctionService.joinAuction(9,2,5000));
    }
}
