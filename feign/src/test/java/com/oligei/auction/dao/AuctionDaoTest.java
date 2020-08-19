package com.oligei.auction.dao;

import com.oligei.auction.entity.Auction;
import com.oligei.auction.repository.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuctionDaoTest {

    @Autowired
    AuctionDao auctionDao;

    @MockBean
    private AuctionRepository auctionRepository;


    @Test
    @Rollback
    void save() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null,Ddl = null;
        try{
            Showtime=format1.parse("2020-1-1");
            Ddl=format2.parse("2020-10-1 12:12:12");
        } catch (ParseException e){
            e.printStackTrace();
        }
        Auction auction = new Auction(1,1,Ddl,500,501,Showtime,5);

        when(auctionRepository.save(any(Auction.class))).thenReturn(auction);
        when(auctionRepository.save(null)).thenReturn(null);

        assertEquals(auction,auctionDao.save(auction));
        verify(auctionRepository,times(1)).save(any(Auction.class));
        try {
            auctionRepository.save(null);
        }
        catch (NullPointerException e){
            assertEquals("null auction",e.getMessage());
        }

    }
    @Test
    @Rollback
    void getAvailableAuctionsForNow() {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null,Ddl = null;
        try{
            Showtime=format1.parse("2020-1-1");
            Ddl=format2.parse("2020-10-1 12:12:12");
        } catch (ParseException e){
            e.printStackTrace();
        }
        List<Auction> auctions = new ArrayList<>();
        Auction auction1 = new Auction(1,1,Ddl,500,501,Showtime,5);
        Auction auction2 = new Auction(2,2,Ddl,1000,1001,Showtime,10);
        auctions.add(auction1);
        auctions.add(auction2);

        when(auctionRepository.findAuctionsByIsoverEquals(0)).thenReturn(auctions);

        assertEquals(2,auctionDao.getAvailableAuctionsForNow().size());
    }

    @Test
    @Rollback
    void findOneById(){
        int existedAuctionId = 1;
        int noneExistedAuctionId = 10;
        int illegalAuctionId=-1;

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null,Ddl = null;
        try{
            Showtime=format1.parse("2020-1-1");
            Ddl=format2.parse("2020-10-1 12:12:12");
        } catch (ParseException e){
            e.printStackTrace();
        }
        Auction auction1 = new Auction(1,1,Ddl,500,501,Showtime,5);

        when(auctionRepository.getOne(existedAuctionId)).thenReturn(auction1);
        when(auctionRepository.getOne(noneExistedAuctionId)).thenReturn(null);
        when(auctionRepository.getOne(illegalAuctionId)).thenReturn(null);


        assertEquals(1,auctionDao.findOneById(existedAuctionId).getAuctionid());
        assertNull(auctionDao.findOneById(noneExistedAuctionId));
        assertNull(auctionDao.findOneById(illegalAuctionId));
    }
}
