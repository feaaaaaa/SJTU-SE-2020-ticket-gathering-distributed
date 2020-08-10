package com.oligei.feign.serviceimpl;

import com.oligei.feign.dao.ActitemDao;
import com.oligei.feign.dao.ActivityDao;
import com.oligei.feign.dao.AuctionDao;
import com.oligei.feign.dto.AuctionListItem;
import com.oligei.feign.entity.Actitem;
import com.oligei.feign.entity.Activity;
import com.oligei.feign.entity.Auction;
import com.oligei.feign.service.AuctionService;
import com.oligei.feign.service.OrderService;
import com.oligei.feign.util.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private Cache<AuctionListItem> auctionListItemCache = new Cache<>();

    @PostConstruct
    public boolean initCache(){
        System.out.println("cache init...");
        flushActions();
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
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
                e.printStackTrace();
            }
            AuctionListItem auctionListItem = new AuctionListItem(auction.getAuctionid(),d2,auction.getOrderprice(),d1,auction.getAmount(),
                    activity.getTitle(),activity.getActor(),activity.getVenue(),auction.getUserid(),activity.getActivityIcon());
            auctionListItemCache.addOrUpdateCache(auction.getAuctionid(),auctionListItem);
        }
        return true;
    }

    private boolean whenSetOver(Integer auctionid){
        Auction auction = auctionDao.findOneById(auctionid);
        AuctionListItem auctionListItem = auctionListItemCache.getValue(auctionid);

        auction.setIsover(1);
        auction.setUserid(auctionListItem.getUserid());
        auction.setOrderprice(auctionListItem.getPrice());
        auction.setOrdertime(new Date());//不是真实下单时间
        auctionDao.save(auction);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(auction.getInitprice().equals(auction.getOrderprice()))
            actitemDao.modifyRepository(auction.getActitemid(),auction.getInitprice(),auction.getAmount(),sdf.format(auction.getShowtime()));
        else
            orderService.addOrder(auction.getUserid(),auction.getActitemid(),auction.getInitprice(),auction.getOrderprice(),
                    auction.getAmount(),sdf.format(auction.getShowtime()),df.format(auction.getOrdertime()));
        return true;
    }

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

        Auction auction1 = auctionDao.save(auction);

        Activity activity = activityDao.findOneById(actitemDao.findOneById(auction1.getActitemid()).getActivityId());
        AuctionListItem auctionListItem = new AuctionListItem(auction1.getAuctionid(),Ddl,auction1.getOrderprice(),initTime,auction1.getAmount(),
                    activity.getTitle(),activity.getActor(),activity.getVenue(),auction1.getUserid(),activity.getActivityIcon());
        auctionListItemCache.addOrUpdateCache(auction1.getAuctionid(),auctionListItem);
        return actitemDao.modifyRepository(actitemid,initprice,-amount,showtime);
    }

    @Override
    public List<AuctionListItem> getAvailableAuctions() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",
                java.util.Locale.ENGLISH);
        String dateNow = df.format(new Date());
        List<Integer> list = new ArrayList<>();
        Date d1 = null,d2 = null;
        for (Map.Entry<Integer,AuctionListItem> entry : auctionListItemCache.getCacheEntrySet()){
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

        for(Integer i : list){
            auctionListItemCache.evictCache(i);
        }

        List<AuctionListItem> auctionListItems = new ArrayList<>();
        for (Map.Entry<Integer,AuctionListItem> entry : auctionListItemCache.getCacheEntrySet()){
            System.out.println(entry.getKey());
            auctionListItems.add(entry.getValue());
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
            whenSetOver(auction.getAuctionid());
            auctionListItemCache.evictCache(auctionid);
            return -1;
        }
        AuctionListItem oldAuctionListItem = auctionListItemCache.getValue(auctionid);
        System.out.println("old price:"+oldAuctionListItem.getPrice());
        if(oldAuctionListItem.getPrice() > orderprice)
            return -2;
        AuctionListItem auctionListItem = auctionListItemCache.getValue(auctionid);
        AuctionListItem auctionListItem1 = new AuctionListItem(auctionListItem.getAuctionid(),auctionListItem.getDdl(),orderprice,new Date(),auctionListItem.getAmount(),
                auctionListItem.getTitle(),auctionListItem.getActor(),auctionListItem.getVenue(),userid,auctionListItem.getActivityIcon());
        auctionListItemCache.addOrUpdateCache(auctionListItem.getAuctionid(),auctionListItem1);
        return 1;
    }

    @Override
    public void flushActions() {
        List<Auction> auctions = auctionDao.getAvailableAuctionsForNow();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = df.format(new Date());
        Date d1 = null,d2 = null;
        System.out.println("now:"+dateNow);
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
                whenSetOver(auction.getAuctionid());
            }
        }
    }
}
