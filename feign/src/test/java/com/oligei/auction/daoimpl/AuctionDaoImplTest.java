package com.oligei.auction.daoimpl;

import com.oligei.auction.dao.AuctionDao;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AuctionDaoImplTest {

    @Autowired
    private AuctionDao auctionDao;

    @Test
    @Rollback
    void save() {
        System.out.println("Null Auction");
        assertThrows(NullPointerException.class,()->auctionDao.save(null),
                "null auction --AuctionDaoImpl save");
    }

    @Test
    @Rollback
    void getAvailableAuctionsForNow() {
    }

    @Test
    @Rollback
    void findOneById() {
        System.out.println("Null Auctionid");
        assertThrows(NullPointerException.class,()->auctionDao.findOneById(null),
                "null auctionid --AuctionDaoImpl findOneById");
    }
}
