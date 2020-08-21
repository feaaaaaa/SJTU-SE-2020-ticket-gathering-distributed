package com.oligei.auction.serviceimpl;

import com.oligei.auction.dao.ActitemDao;
import com.oligei.auction.dao.ActivityDao;
import com.oligei.auction.dao.AuctionDao;
import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.entity.Activity;
import com.oligei.auction.entity.Auction;
import com.oligei.auction.feign.UserFeign;
import com.oligei.auction.service.AuctionService;
import com.oligei.auction.feign.OrderFeign;
import com.oligei.auction.util.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@EnableScheduling
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionDao auctionDao;

    @Autowired
    private ActitemDao actitemDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private UserFeign userFeign;

//    private AtomicInteger testPrice=new AtomicInteger();
    private int testPrice=500;
    Lock lock = new ReentrantLock();

    private Cache<AuctionListItem> auctionListItemCache = new Cache<>();



//    @Override
    @Transactional
    /**
     *take part in an auction successfully
     *@param: auctionid, userid, orderprice
     *@return: java.lang.Integer
     *@author: Cui Shaojie
     *@date: 2020/8/18
     */
    public Integer joinAuction2(Integer auctionid, Integer userid, Integer orderprice) {
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(userid, "null userid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(orderprice, "null orderprice --AuctionServiceImpl joinAuction");

//        Auction auction = auctionDao.findOneById(auctionid);
//
//        if (auction == null)
//            throw new NullPointerException("Auction Not Found");
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateNow = df.format(new Date());
//        Date d1 = null, d2 = null;
//        String dateDdl = auction.getDdl().toString();
//        try {
//            d1 = df.parse(dateNow);
//            d2 = df.parse(dateDdl);
//        }catch (Exception e){
//            e.printStackTrace();;
//        }
//        if(d1.getTime()>d2.getTime())
//        {
//            whenSetOver(auction.getAuctionid());
//            auctionListItemCache.evictCache(auctionid);
//            return -1; //超时
//        }
//        AuctionListItem oldAuctionListItem = auctionListItemCache.getValue(auctionid);
        lock.lock();
        if (testPrice >= orderprice) {
            lock.unlock();
            return -2; //出价低
        }
//        System.out.println("old price:" + testPrice+"new price"+orderprice);
        testPrice=orderprice;
        lock.unlock();
//        AuctionListItem auctionListItem = auctionListItemCache.getValue(auctionid);
//        AuctionListItem auctionListItem1 = new AuctionListItem(auctionListItem.getAuctionid(), auctionListItem.getDdl(), orderprice, new Date(), auctionListItem.getAmount(),
//                auctionListItem.getTitle(), auctionListItem.getActor(), auctionListItem.getVenue(), userid, auctionListItem.getActivityIcon());
//        auctionListItemCache.addOrUpdateCache(auctionListItem.getAuctionid(), auctionListItem1);
        return 1;
    }

    @PostConstruct
    /**
     *put all available auctions from database to cache
     *@return: boolean
     *@author: Cui Shaojie
     *@date: 2020/8/18
     */
    public boolean initCache() {
        System.out.println("cache init...");
//        flushActions();
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null, d2 = null;
        String str1 = null, str2 = null;
        for(Auction auction : auctions)
        {
            Activity activity = activityDao.findOneById(actitemDao.findOneById(auction.getActitemid()).getActivityId());
            str1 = auction.getShowtime().toString();
            str2 = auction.getDdl().toString();
            try {
                d1 = format1.parse(str1);
                d2 = format2.parse(str2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            AuctionListItem auctionListItem = new AuctionListItem(auction.getAuctionid(), d2, auction.getOrderprice(), d1, auction.getAmount(),
                    activity.getTitle(), activity.getActor(), activity.getVenue(), auction.getUserid(), activity.getActivityIcon());
            auctionListItemCache.addOrUpdateCache(auction.getAuctionid(), auctionListItem);
        }
        return true;
    }

    /**
    *a private method called when an auction is over
    *@param: auctionid
    *@return: boolean
    *@author: Cui Shaojie
    *@date: 2020/8/18
    */
    private boolean whenSetOver(Integer auctionid){
        Auction auction = auctionDao.findOneById(auctionid);
        AuctionListItem auctionListItem = auctionListItemCache.getValue(auctionid);
        if (auctionListItem == null)
            System.out.println("this is null");

        System.out.println("auctionid" + auctionListItem.getAuctionid());

        auction.setIsover(1);
        auction.setUserid(auctionListItem.getUserid());
        auction.setOrderprice(auctionListItem.getPrice());
        auction.setOrdertime(new Date());
        auctionDao.save(auction);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (auction.getInitprice().equals(auction.getOrderprice()))
            actitemDao.modifyRepository(auction.getActitemid(), auction.getInitprice(), auction.getAmount(), sdf.format(auction.getShowtime()));
        else
            orderFeign.addOrder(auction.getUserid(),auction.getActitemid(),auction.getInitprice(),auction.getOrderprice(),
                    auction.getAmount(),sdf.format(auction.getShowtime()),df.format(auction.getOrdertime()));
        return true;
    }

    @Override
    /**
     *save an auction
     *@param: actitemid, ddl, showtime, initprice, orderprice, amount
     *@return: java.lang.Boolean
     *@author: Cui Shaojie
     *@date: 2020/8/18
     */
    public Boolean save(Integer actitemid, String ddl, String showtime, Integer initprice, Integer orderprice, Integer amount) {
        Objects.requireNonNull(actitemid, "null actitemdid --AuctionServiceImpl save");
        Objects.requireNonNull(ddl, "null ddl --AuctionServiceImpl save");
        Objects.requireNonNull(showtime, "null showtime --AuctionServiceImpl save");
        Objects.requireNonNull(initprice, "null initprice --AuctionServiceImpl save");
        Objects.requireNonNull(orderprice, "null orderprice --AuctionServiceImpl save");
        Objects.requireNonNull(amount, "null amount --AuctionServiceImpl save");

        Auction auction = new Auction();

        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null;
        Date Ddl = null;
        Date initTime = new Date();
        String nowTime = null;
        try {
            Showtime = format1.parse(showtime);
            Ddl = format2.parse(ddl);
            nowTime = format2.format(initTime);
            initTime = format2.parse(nowTime);
        } catch (ParseException e) {
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

        Auction auction1 = auctionDao.save(auction);

        Activity activity = activityDao.findOneById(actitemDao.findOneById(auction1.getActitemid()).getActivityId());
        AuctionListItem auctionListItem = new AuctionListItem(auction1.getAuctionid(), Ddl, auction1.getOrderprice(), initTime, auction1.getAmount(),
                activity.getTitle(), activity.getActor(), activity.getVenue(), auction1.getUserid(), activity.getActivityIcon());
        auctionListItemCache.addOrUpdateCache(auction1.getAuctionid(), auctionListItem);
        return actitemDao.modifyRepository(actitemid, initprice, -amount, showtime);
    }

    @Override
    /**
     *get all available auctions
     *@return: java.util.List<com.oligei.auction.dto.AuctionListItem>
     *@author: Cui Shaojie
     *@date: 2020/8/18
     */
    public List<AuctionListItem> getAvailableAuctions() {
        System.out.println("flushing");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",
                java.util.Locale.ENGLISH);
        String dateNow = df.format(new Date());
        List<Integer> list = new ArrayList<>();
        Date d1 = null, d2 = null;
        for (Map.Entry<Integer, AuctionListItem> entry : auctionListItemCache.getCacheEntrySet()) {
            String dateDdl = entry.getValue().getDdl().toString();
            try {
                d1 = df.parse(dateNow);
                d2 = simpleDateFormat.parse(dateDdl);
            }catch (Exception e){
                e.printStackTrace();;
            }
            if(d1.getTime()>d2.getTime())
            {
                list.add(entry.getValue().getAuctionid());
                whenSetOver(entry.getValue().getAuctionid());
            }
        }

        for (Integer i : list) {
            auctionListItemCache.evictCache(i);
        }
        List<AuctionListItem> auctionListItems = new ArrayList<>();
        for (Map.Entry<Integer, AuctionListItem> entry : auctionListItemCache.getCacheEntrySet()) {
            System.out.println("key:" + entry.getKey());
            auctionListItems.add(entry.getValue());
        }
        System.out.println(auctionListItems.size());
        return auctionListItems;
    }

    @Override
    /**
    *take part in an auction successfully
    *@param: auctionid, userid, orderprice
    *@return: java.lang.Integer
    *@author: Cui Shaojie
    *@date: 2020/8/18
    */
    public Integer joinAuction(Integer auctionid, Integer userid, Integer orderprice) {
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(userid, "null userid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(orderprice, "null orderprice --AuctionServiceImpl joinAuction");

        Auction auction = auctionDao.findOneById(auctionid);

        if (auction == null)
            throw new NullPointerException("Auction Not Found");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(new Date());
        Date d1 = null, d2 = null;
        String dateDdl = auction.getDdl().toString();
        try {
            d1 = df.parse(dateNow);
            d2 = df.parse(dateDdl);
        }catch (Exception e){
            e.printStackTrace();;
        }
        if(d1.getTime()>d2.getTime())
        {
            whenSetOver(auction.getAuctionid());
            auctionListItemCache.evictCache(auctionid);
            return -1; //超时
        }
        AuctionListItem oldAuctionListItem = auctionListItemCache.getValue(auctionid);
        if (oldAuctionListItem.getPrice() >= orderprice)
            return -2; //出价低
        if(oldAuctionListItem.getUserid().equals(userid)) {
            System.out.println("A "+userid+" : "+(orderprice-oldAuctionListItem.getPrice()));
            if (userFeign.rechargeOrDeduct(userid, -(orderprice - oldAuctionListItem.getPrice())).getData() == -1) {
                return -3; //余额不足
            }
        }
        else {
            System.out.println("B "+userid+" : "+orderprice);
            if (userFeign.rechargeOrDeduct(userid, -orderprice).getData() == -1) {
                return -3; //余额不足
            }
            userFeign.rechargeOrDeduct(oldAuctionListItem.getUserid(), oldAuctionListItem.getPrice());
            System.out.println("C"+oldAuctionListItem.getUserid()+" : "+oldAuctionListItem.getPrice());
        }
        System.out.println("old price:" + oldAuctionListItem.getPrice()+"new price"+orderprice);
        AuctionListItem auctionListItem1 = new AuctionListItem(oldAuctionListItem.getAuctionid(),oldAuctionListItem.getDdl(),orderprice,new Date(),oldAuctionListItem.getAmount(),
                oldAuctionListItem.getTitle(),oldAuctionListItem.getActor(),oldAuctionListItem.getVenue(),userid,oldAuctionListItem.getActivityIcon());
        auctionListItemCache.addOrUpdateCache(oldAuctionListItem.getAuctionid(),auctionListItem1);
        return 1;
    }

    @Override
    /**
     *set over all unavailable
     *@return: void
     *@author: Cui Shaojie
     *@date: 2020/8/18
     */
    public void flushActions() {
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(new Date());
        Date d1 = null,d2 = null;
        System.out.println("now:"+dateNow);
        for(Auction auction : auctions)
        {
            String dateDdl = auction.getDdl().toString();
            System.out.println(dateDdl);
            try {
                d1 = df.parse(dateNow);
                d2 = df.parse(dateDdl);
            }catch (Exception e){
                e.printStackTrace();;
            }
            if(d1.getTime()>d2.getTime())
            {
                whenSetOver(auction.getAuctionid());
            }
        }
    }

//    @Scheduled(cron = "0/1 * * * * ?")
//    public void flushAuctions(){
//        System.out.println("flushing");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",
//                java.util.Locale.ENGLISH);
//        String dateNow = df.format(new Date());
//        List<Integer> list = new ArrayList<>();
//        Date d1 = null,d2 = null;
//        for (Map.Entry<Integer,AuctionListItem> entry : auctionListItemCache.getCacheEntrySet()){
//            String dateDdl = entry.getValue().getDdl().toString();
//            try {
//                d1 = df.parse(dateNow);
//                d2 = simpleDateFormat.parse(dateDdl);
//            }catch (Exception e){
//                e.printStackTrace();;
//            }
//            if(d1.getTime()>d2.getTime())
//            {
//                list.add(entry.getValue().getAuctionid());
//                whenSetOver(entry.getValue().getAuctionid());
//            }
//        }
//
//        for(Integer i : list){
//            auctionListItemCache.evictCache(i);
//        }
//    }
}
