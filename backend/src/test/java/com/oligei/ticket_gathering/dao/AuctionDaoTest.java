package com.oligei.ticket_gathering.dao;

import com.oligei.ticket_gathering.entity.mysql.Auction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuctionDaoTest {

    @Autowired
    AuctionDao auctionDao;

    @Test
    @Rollback
    void save() {
        Auction auction = new Auction();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null,Ddl = null;
        try{
            Showtime=format1.parse("2020-1-1");
            Ddl=format2.parse("2020-10-1 12:12:12");
        } catch (ParseException e){
            e.printStackTrace();
        }
        auction.setActitemid(1);
        auction.setUserid(0);
        auction.setIsover(0);
        auction.setInitprice(500);
        auction.setOrderprice(500);
        auction.setDdl(Ddl);
        auction.setShowtime(Showtime);
        auction.setOrdertime(null);
        auction.setAmount(10);

        assertEquals(true,auctionDao.save(auction));

    }
    @Test
    @Rollback
    void getAvailableAuctionsForNow() {
        assertEquals(4,auctionDao.getAvailableAuctionsForNow().size());
    }

    @Test
    @Rollback
    void findOneById(){
        assertEquals(1,auctionDao.findOneById(1).getAuctionid());
    }
}