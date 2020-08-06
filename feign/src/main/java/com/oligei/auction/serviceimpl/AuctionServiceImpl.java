package com.oligei.auction.serviceimpl;

import com.oligei.auction.dao.ActitemDao;
import com.oligei.auction.dao.ActivityDao;
import com.oligei.auction.dao.AuctionDao;
import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.entity.Activity;
import com.oligei.auction.entity.Auction;
import com.oligei.auction.service.AuctionService;
import com.oligei.auction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private ActitemDao actitemDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private OrderService orderService;

    @Override
    public Boolean save(Integer actitemid, String ddl,String showtime, Integer initprice,Integer orderprice, Integer amount) {
        Auction auction = new Auction();

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null;
        Date Ddl = null;
        Date initTime = new Date();
        String nowTime = null;
        try{
            Showtime=format1.parse(showtime);
            Ddl=format2.parse(ddl);
            nowTime=format2.format(initTime);
            initTime=format2.parse(nowTime);
        } catch (ParseException e){
            e.printStackTrace();
        }
        auction.setActitemid(actitemid);
        auction.setUserid(0);
        auction.setIsover(0);
        auction.setInitprice(initprice);
        auction.setOrderprice(orderprice);
        auction.setDdl(Ddl);
        auction.setShowtime(Showtime);
        auction.setOrdertime(initTime);
        auction.setAmount(amount);

        auctionDao.save(auction);
        return actitemDao.modifyRepository(actitemid,initprice,-amount,showtime);
    }

    @Override
    public List<AuctionListItem> getAvailableAuctions() {
        flushActions();
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
        List<AuctionListItem> auctionListItems = new ArrayList<>();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null,d2 = null;
        String str1 = null, str2 = null;
        for(Auction auction : auctions)
        {
            Activity activity = activityDao.findOneById(actitemDao.findOneById(auction.getActitemid()).getActivityId());
            str1 = auction.getShowtime().toString();
            str2 = auction.getDdl().toString();
            try {
                d1 = format1.parse(str1);
                d2 = format2.parse(str2);
            }catch (Exception e){
                e.printStackTrace();;
            }
            AuctionListItem auctionListItem = new AuctionListItem(auction.getAuctionid(),d2,auction.getOrderprice(),d1,auction.getAmount(),
                    activity.getTitle(),activity.getActor(),activity.getVenue(),auction.getUserid(),activity.getActivityIcon());
            auctionListItems.add(auctionListItem);
        }
        return auctionListItems;
    }

    @Override
    public Integer joinAuction(Integer auctionid, Integer userid, Integer orderprice) {
        Auction auction = auctionDao.findOneById(auctionid);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(new Date());
        Date d1 = null,d2 = null;
        String dateDdl = auction.getDdl().toString();
        try {
            d1 = df.parse(dateNow);
            d2 = df.parse(dateDdl);
        }catch (Exception e){
            e.printStackTrace();;
        }
        if(d1.getTime()>d2.getTime())
        {
            auction.setIsover(1);
            auctionDao.save(auction);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if(auction.getInitprice().equals(auction.getOrderprice()))
                actitemDao.modifyRepository(auction.getActitemid(),auction.getInitprice(),auction.getAmount(),sdf.format(auction.getShowtime()));
            else
                orderService.addOrder(auction.getUserid(),auction.getActitemid(),auction.getInitprice(),auction.getOrderprice(),
                        auction.getAmount(),sdf.format(auction.getShowtime()),df.format(auction.getOrdertime()));
            return -1;
        }
        auction.setUserid(userid);
        auction.setOrderprice(orderprice);
        auction.setOrdertime(new Date());
        auctionDao.save(auction);
        return 1;
    }

    @Override
    public void flushActions() {
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(new Date());
        Date d1 = null,d2 = null;
        for(Auction auction : auctions)
        {
            String dateDdl = auction.getDdl().toString();
            try {
                d1 = df.parse(dateNow);
                d2 = df.parse(dateDdl);
            }catch (Exception e){
                e.printStackTrace();;
            }
            if(d1.getTime()>d2.getTime())
            {
                auction.setIsover(1);
                auctionDao.save(auction);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(auction.getInitprice().equals(auction.getOrderprice()))
                    actitemDao.modifyRepository(auction.getActitemid(),auction.getInitprice(),auction.getAmount(),sdf.format(auction.getShowtime()));
                else
                    orderService.addOrder(auction.getUserid(),auction.getActitemid(),auction.getInitprice(),auction.getOrderprice(),
                            auction.getAmount(),sdf.format(auction.getShowtime()),df.format(auction.getOrdertime()));
            }
        }
    }
}
