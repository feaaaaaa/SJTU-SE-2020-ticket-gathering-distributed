package com.oligei.ticket_gathering.serviceimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.dao.ActitemDao;
import com.oligei.ticket_gathering.dao.ActivityDao;
import com.oligei.ticket_gathering.dao.OrderDao;
import com.oligei.ticket_gathering.entity.info.OrderInfo;
import com.oligei.ticket_gathering.entity.mysql.Actitem;
import com.oligei.ticket_gathering.entity.mysql.Activity;
import com.oligei.ticket_gathering.entity.mysql.Order;
import com.oligei.ticket_gathering.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<OrderInfo> getOrderInfoByUser(int userId){
        List<OrderInfo> orderInfos=new ArrayList<>();
        List<Order> orderList=orderDao.getOrderByUserId(userId);
        for (Order orderItem : orderList){
            Actitem tmp_actItem=actitemDao.findOneById(orderItem.getActitemId());
            Activity tmp_activity=activityDao.findOneById(tmp_actItem.getActivityId());
            OrderInfo tmp=new OrderInfo(orderItem,tmp_activity);
            orderInfos.add(tmp);
        }
        return orderInfos;
    }

    @Override
    public List<OrderInfo> getUserOrder(int userId){
        return orderDao.getUserOrder(userId);
    }

    @Override
    public boolean addOrder(int userId, int actitemId, int initPrice,int orderPrice, int amount, String showtime, String orderTime){
        if(actitemDao.modifyRepository(actitemId,initPrice,-amount,showtime)){
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date Showtime=null,OrderTime=null;
            try{
                Showtime=format1.parse(showtime);
                OrderTime=format2.parse(orderTime);
            } catch (ParseException e){
                e.printStackTrace();
            }
            if(orderPrice==0){
                return orderDao.addOrder(userId,actitemId,initPrice,amount,Showtime,OrderTime);
            }
            else{
                return orderDao.addOrder(userId,actitemId,orderPrice,amount,Showtime,OrderTime);
            }
        }
        else {
            System.out.println("modify Repository failed");
            return false;
        }
    }
}
