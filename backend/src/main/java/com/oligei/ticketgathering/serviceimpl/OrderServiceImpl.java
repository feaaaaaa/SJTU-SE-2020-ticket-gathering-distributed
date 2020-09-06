package com.oligei.ticketgathering.serviceimpl;

import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.dao.OrderDao;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.mysql.Order;
import com.oligei.ticketgathering.service.OrderService;
import com.oligei.ticketgathering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ActitemDao actitemDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private UserService userService;

//    @Override
//    public List<OrderInfo> getOrderInfoByUser(int userId){
//        List<OrderInfo> orderInfos=new ArrayList<>();
//        List<Order> orderList=orderDao.getOrderByUserId(userId);
//        for (Order orderItem : orderList){
//            Actitem tmp_actItem=actitemDao.findOneById(orderItem.getActitemId());
//            Activity tmp_activity=activityDao.findOneById(tmp_actItem.getActivityId());
//            OrderInfo tmp=new OrderInfo(orderItem,tmp_activity);
//            orderInfos.add(tmp);
//        }
//        return orderInfos;
//    }

    @Override
    /**
     * @param userId
     * @return OrderInfo
     * @author Yang Yicheng
     * @date 2020/8/10
     * @throws InvalidDataAccessApiUsageException using illegal userId
     * @throws NullPointerException Order not found
     */
    public List<OrderInfo> getUserOrder(int userId) {
        if (userId <= 0) {
            throw new InvalidDataAccessApiUsageException("illegal userId --OrderServiceImpl getUserOrder");
        }
        List<OrderInfo> result = orderDao.getUserOrder(userId);
        if (result == null) {
            throw new NullPointerException("null Order --OrderServiceImpl getUserOrder");
        }
        return result;
    }

    @Override
    /**
     * @param userId
     * @param actitemId
     * @param initPrice
     * @param orderPrice
     * @param amount
     * @param showtime
     * @param orderTime
     * @return boolean
     * @author Yang Yicheng
     * @date 2020/8/12
     */
    public boolean addOrder(int userId, int actitemId, int initPrice, int orderPrice, int amount, String showtime, String orderTime) {
        if (actitemDao.modifyRepository(actitemId, initPrice, -amount, showtime)) {
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date Showtime = null, OrderTime = null;
            try {
                Showtime = format1.parse(showtime);
                OrderTime = format2.parse(orderTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (orderPrice == 0) {
                System.out.println("normal addOrder");
                if (userService.rechargeOrDeduct(userId, -initPrice) == -1)
                    return false;//余额不足
                return orderDao.addOrder(userId, actitemId, initPrice, amount, Showtime, OrderTime);
            } else {
                System.out.println("Auction");
                return orderDao.addOrder(userId, actitemId, orderPrice, amount, Showtime, OrderTime);
            }
        } else {
            System.out.println("modify Repository failed");
            return false;
        }
    }
}
