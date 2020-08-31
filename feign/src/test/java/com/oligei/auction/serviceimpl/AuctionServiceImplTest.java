package com.oligei.auction.serviceimpl;

import com.oligei.auction.service.AuctionService;
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
class AuctionServiceImplTest {

    @Autowired
    private AuctionService auctionService;

    @Test
    @Rollback
    void canEnter() {
        System.out.println("Null Userid");
        assertThrows(NullPointerException.class, ()->auctionService.canEnter(null,1),
                "null userid --AuctionServiceImpl canEnter");

        System.out.println("Null Auctionid");
        assertThrows(NullPointerException.class, ()->auctionService.canEnter(1,null),
                "null auctionid --AuctionServiceImpl canEnter");
    }

    @Test
    @Rollback
    void deposit() {
        System.out.println("Null Userid");
        assertThrows(NullPointerException.class, ()->auctionService.deposit(null,1),
                "null userid --AuctionServiceImpl deposit");

        System.out.println("Null Auctionid");
        assertThrows(NullPointerException.class, ()->auctionService.deposit(1,null),
                "null auctionid --AuctionServiceImpl deposit");
    }

    @Test
    @Rollback
    void addAuction() {
        System.out.println("Null Actitemid");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(null,"123","123",1,1,1),
                "null actitemdid --AuctionServiceImpl addAuction");

        System.out.println("Null Ddl");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(1,null,"123",1,1,1),
                "null ddl --AuctionServiceImpl addAuction");

        System.out.println("Null Showtime");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(1,"123",null,1,1,1),
                "null showtime --AuctionServiceImpl addAuction");

        System.out.println("Null Initprice");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(1,"123","123",null,1,1),
                "null initprice --AuctionServiceImpl addAuction");

        System.out.println("Null Orderprice");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(1,"123","123",1,null,1),
                "null orderprice --AuctionServiceImpl addAuction");

        System.out.println("Null Amount");
        assertThrows(NullPointerException.class,
                ()->auctionService.addAuction(1,"123","123",1,1,null),
                "null amount --AuctionServiceImpl addAuction");
    }

    @Test
    @Rollback
    void tmpInit() {
    }

    @Test
    @Rollback
    void flushAuctions() {
    }

    @Test
    @Rollback
    void getAvailableAuctions() {
    }

    @Test
    @Rollback
    void getPrice() {
        System.out.println("Null Auctionid");
        assertThrows(NullPointerException.class, ()->auctionService.getPrice(null),
                "null auctionid --AuctionServiceImpl getPrice");
    }

    @Test
    @Rollback
    void joinAuction() {
        System.out.println("Null Auctionid");
        assertThrows(NullPointerException.class,()->auctionService.joinAuction(null,1,1),
                "null auctionid --AuctionServiceImpl joinAuction");

        System.out.println("Null Userid");
        assertThrows(NullPointerException.class,()->auctionService.joinAuction(1,null,1),
                "null userid --AuctionServiceImpl joinAuction");

        System.out.println("Null Orderprice");
        assertThrows(NullPointerException.class,()->auctionService.joinAuction(1,1,null),
                "null auctionid --AuctionServiceImpl joinAuction");
    }
}
