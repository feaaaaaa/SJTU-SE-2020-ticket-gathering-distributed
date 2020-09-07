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
import com.oligei.auction.util.TimeFormatter;
import com.oligei.auction.util.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;
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

    private TimeFormatter timeFormatter = new TimeFormatter();

    private HashMap<Integer, AuctionListItem> map0 = new HashMap<Integer, AuctionListItem>() {
    };
    private HashMap<Integer, AuctionListItem> map1 = new HashMap<Integer, AuctionListItem>() {
    };
    private Boolean mapFlag = false;

    private int testPrice = 500;
    Lock lock = new ReentrantLock();

    /**
     * wrapper function for 2list
     *
     * @param auctionid auctionid
     * @return auctionlistitem
     * @throws NullPointerException if parameter is null
     * @author ziliuziliu
     * @date 2020/8/22
     */
    private AuctionListItem getAuctionListItemByAuctionid(Integer auctionid) {
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl getAuctionListItemByAuctionid");
        return mapFlag ? map1.get(auctionid) : map0.get(auctionid);
    }

    @Override
    /**
     * @param userid
     * @param auctionid
     * @author ziliuziliu
     * @return true if OK, false if no
     * @throws NullPointerException if parameter is null
     * @date 2020/8/22
     */
    public Boolean canEnter(Integer userid, Integer auctionid) throws NullPointerException {
        Objects.requireNonNull(userid, "null userid --AuctionServiceImpl canEnter");
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl canEnter");
        AuctionListItem auctionListItem = getAuctionListItemByAuctionid(auctionid);
        if (auctionListItem == null) throw new NullPointerException("auctionid not available");
        return auctionListItem.userExists(userid);
    }

    @Override
    /**
     * @param userid
     * @param auctionid
     * @author ziliuziliu
     * @return true if OK, false if not enough money
     * @throws NullPointerException if parameter is null
     * @date 2020/8/22
     */
    public Boolean deposit(Integer userid, Integer auctionid) throws NullPointerException {
        Objects.requireNonNull(userid, "null userid --AuctionServiceImpl deposit");
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl deposit");
//        Integer deposit = auctionDao.findOneById(auctionid).getInitprice() * 3;
        AuctionListItem auctionListItem = getAuctionListItemByAuctionid(auctionid);
        if (auctionListItem == null) throw new NullPointerException("auctionid not available");
        Integer deposit = -1 * auctionListItem.getDeposit();
        Msg<Integer> msg = userFeign.rechargeOrDeduct(userid, deposit);
        if (msg.getStatus() != 200) return false;
        auctionListItem.addUser(userid);
        return true;
    }

    @Override
    /**
     * a private method called when an auction is over
     *
     * @param auctionListItem auctionListItem
     * @author ziliuziliu, Cui Shaojie
     * @date 2020/8/22
     */
    public Boolean whenSetOver(AuctionListItem auctionListItem) {
        Objects.requireNonNull(auctionListItem, "null auctionListItem --AuctionServiceImpl");

        //save auction detail
        Integer auctionid = auctionListItem.getAuctionid();
        Auction auction = auctionDao.findOneById(auctionid);
        Integer userid = auctionListItem.getUserid();
        auction.setIsover(1);
        auction.setUserid(userid);
        auction.setOrderprice(auctionListItem.getPrice());
        auction.setOrdertime(timeFormatter.currectTimestamp());
        auctionDao.save(auction);

        //add order for user
        if (userid != 0) {
            Msg<Boolean> msg = orderFeign.addOrder(userid, auction.getActitemid(),
                    auction.getInitprice(), auction.getOrderprice(), auction.getAmount(),
                    timeFormatter.dateToStr(auction.getShowtime()),
                    timeFormatter.timestampToStr(auction.getOrdertime()));
            if (msg.getStatus() == 200)
                userFeign.rechargeOrDeduct(auction.getUserid(), auction.getInitprice() * 3 - auction.getOrderprice());
        }

        return true;
    }

    @Override
    /**
     * @param actitemid id of website ticket
     * @param ddl deadline of auction
     * @param showtime show time of activity
     * @param initprice origin price of tickets
     * @param orderprice current price of tickets
     * @param amount number of tickets
     * @return true if successfully saved
     * @author ziliuziliu, Cui Shaojie
     * @throws NullPointerException if parameter is null
     * @throws ArithmeticException if not enough stock
     * @date 2020/8/22
     */
    public void addAuction(Integer actitemid, String ddl, String showtime,
                           Integer initprice, Integer orderprice, Integer amount)
            throws ArithmeticException, NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(actitemid, "null actitemdid --AuctionServiceImpl addAuction");
        Objects.requireNonNull(ddl, "null ddl --AuctionServiceImpl addAuction");
        Objects.requireNonNull(showtime, "null showtime --AuctionServiceImpl addAuction");
        Objects.requireNonNull(initprice, "null initprice --AuctionServiceImpl addAuction");
        Objects.requireNonNull(orderprice, "null orderprice --AuctionServiceImpl addAuction");
        Objects.requireNonNull(amount, "null amount --AuctionServiceImpl addAuction");

        Date showtimeDate = timeFormatter.strToDate(showtime);
        Timestamp ddlTimestamp = timeFormatter.strToTimestamp(ddl);

        //enough stock?
        actitemDao.modifyRepository(actitemid, initprice, -amount, showtime);

        //save auction to database
        Timestamp ordertime = timeFormatter.currectTimestamp();
        Auction auction = new Auction(actitemid, 0, ddlTimestamp, initprice, orderprice, 0,
                showtimeDate, ordertime, amount);
        Auction saved_auction = auctionDao.save(auction);

        //save auctionlistitem to list
        Integer auctionid = saved_auction.getAuctionid();
        Integer activityid = actitemDao.findOneById(actitemid).getActivityId();
        Activity activity = activityDao.findOneById(activityid);
        String title = activity.getTitle();
        String actor = activity.getActor();
        String venue = activity.getVenue();
        String activityIcon = activity.getActivityIcon();
        AuctionListItem auctionListItem = new AuctionListItem(auctionid, timeFormatter.strToTimestamp(ddl), orderprice,
                timeFormatter.strToDate(showtime), amount, title, actor, venue, 0, activityIcon, 3 * initprice);
        modifyAuctionList(auctionListItem);
    }

//     @PostConstruct
//     private void tmpInit(){
// //        Auction auction=auctionDao.findOneById(1);
// //        System.out.println(auction.getInitprice()+"???");
// //        String Ddl=timeFormatter.timestampToStr(auction.getDdl());
//         String Ddl="2020-12-31 06:00:00";
//         String showTime="2020-08-22";
// //        String showTime=timeFormatter.dateToStr(auction.getShowtime());
//         addAuction(30616, Ddl, showTime, 80, 80, 2);
//     }


    /**
     * modify auction list
     *
     * @param auctionListItem auctionListItem if add, or null if flush
     * @author ziliuziliu
     * @date 2020/8/22
     */
    private void modifyAuctionList(AuctionListItem auctionListItem) {
        Timestamp currentTime = timeFormatter.currectTimestamp();
        Integer auctionid = -1;
        if (auctionListItem != null) auctionid = auctionListItem.getAuctionid();
        if (mapFlag) {
            map0 = new HashMap<>();
            for (Map.Entry<Integer, AuctionListItem> entry : map1.entrySet()) {
                map0.put(entry.getKey(), entry.getValue());
            }
            if (auctionListItem != null) map0.put(auctionid, auctionListItem);
            else {
                for (Map.Entry<Integer, AuctionListItem> entry : map0.entrySet()) {
                    AuctionListItem item = entry.getValue();
                    if (currentTime.after(item.getDdl())) {
                        whenSetOver(item);
                        map0.remove(item.getAuctionid());
                    }
                }
            }
        } else {
            map1 = new HashMap<>();
            for (Map.Entry<Integer, AuctionListItem> entry : map0.entrySet()) {
                map1.put(entry.getKey(), entry.getValue());
            }
            if (auctionListItem != null) map1.put(auctionid, auctionListItem);
            else {
                for (Map.Entry<Integer, AuctionListItem> entry : map1.entrySet()) {
                    AuctionListItem item = entry.getValue();
                    if (currentTime.after(item.getDdl())) {
                        whenSetOver(item);
                        map1.remove(item.getAuctionid());
                    }
                }
            }
        }
        mapFlag = !mapFlag;
    }

    @Override
    @Scheduled(cron = "0 0/15 * * * ? ")
    /**
     * clear auctions which ddl has passed
     * @author ziliuziliu
     * @date 2020/8/22
     */
    public Boolean flushAuctions() {
        System.out.println("Here to flush auctions.");
        modifyAuctionList(null);
        return true;
    }

    @Override
    /**
     * @return available auctions
     * @author ziliuziliu
     * @date 2020/8/22
     */
    public Map<Integer, AuctionListItem> getAvailableAuctions() {
        if (!mapFlag) return map0;
        else return map1;
    }

    @Override
    /**
     * @param auctionid auctionid
     * @return price
     * @author ziliuziliu
     * @throws NullPointerException if parameter is null
     * @date 2020/8/22
     */
    public Integer getPrice(Integer auctionid) throws NullPointerException {
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl getPrice");
        AuctionListItem auctionListItem = getAuctionListItemByAuctionid(auctionid);
        if (auctionListItem == null) throw new NullPointerException("auctionid not available");
        return auctionListItem.getPrice();
    }

    @Override
    /**
     * @param auctionid
     * @param userid
     * @param orderprice
     * @return java.lang.Integer
     * @author ziliuziliu, Cui Shaojie
     * @date 2020/8/18
     */
    public Integer joinAuction(Integer auctionid, Integer userid, Integer orderprice) throws NullPointerException {
        Objects.requireNonNull(auctionid, "null auctionid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(userid, "null userid --AuctionServiceImpl joinAuction");
        Objects.requireNonNull(orderprice, "null orderprice --AuctionServiceImpl joinAuction");

        lock.lock();
        AuctionListItem auctionListItem = getAuctionListItemByAuctionid(auctionid);
        if (auctionListItem == null) {
            lock.unlock();
            throw new NullPointerException("auctionid not available");
        }
        Timestamp currentTime = timeFormatter.currectTimestamp();
        if (currentTime.after(auctionListItem.getDdl())) {
            lock.unlock();
            return -1; //超时
        }
        Integer price = auctionListItem.getPrice();
        if (price >= orderprice) {
            lock.unlock();
            return -2; //出价低
        }
        auctionListItem.setPrice(orderprice);
        if (!mapFlag) map0.put(auctionid, auctionListItem);
        else map1.put(auctionid, auctionListItem);
        lock.unlock();
        return 1;
    }
}
