//package com.oligei.auction.service;
//
//import com.oligei.auction.dao.AuctionDao;
//import com.oligei.auction.entity.Auction;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.annotation.Rollback;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
///*
//由于该层用到了静态变量cache，而无法mock掉cache，所以不得已和数据库相关，要求为：仅存在一个未结束竞价且auctionid为1，存在userid为1的用户
// */
//@SpringBootTest
//class AuctionServiceTest {
//
//    @Autowired
//    private AuctionService auctionService;
//
//    @MockBean
//    private AuctionDao auctionDao;
//
////    @Test
////    @Rollback
////    @BeforeEach
////    void initCache() {
////        List<Auction> auctions = new ArrayList<>();
////        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
////        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date Showtime1 = null, Showtime2 = null, Ddl = null;
////        try {
////            Showtime1 = format1.parse("2020-05-10");
////            Showtime2 = format1.parse("2020-04-25");
////            Ddl = format2.parse("2020-10-01 00:00:00");
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        Auction auction1 = new Auction(1, 7588, Ddl, 500, 501, Showtime1, 5);
////        Auction auction2 = new Auction(2, 8291, Ddl, 500, 501, Showtime2, 5);
////        auctions.add(auction1);
////        auctions.add(auction2);
////        when(auctionDao.getAvailableAuctionsForNow()).thenReturn(auctions);
////        assertTrue(auctionService.initCache());
////    }
//
//    @Test
//    @Rollback
//    void save() {
//        assertEquals(true,auctionService.save(7588,"2020-10-1 12:12:12","2020-05-10",500,1000,5));
////        verify(auctionDao,times(1)).save(any(Auction.class));
//    }
//
//    @Test
//    @Rollback
//    void getAvailableAuctions() {
//        assertEquals(1,auctionService.getAvailableAuctions().size());
//    }
//
//    @Test
//    @Rollback
//    void joinAuction() {
//        assertEquals(1,auctionService.joinAuction(1,1,10000));
//    }
//}
