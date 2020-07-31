package com.oligei.ticket_gathering.daoimpl;

import com.oligei.ticket_gathering.dao.OrderDao;
import com.oligei.ticket_gathering.entity.info.OrderInfo;
import com.oligei.ticket_gathering.entity.mysql.Order;
import com.oligei.ticket_gathering.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrderByUserId(int userId){
        return orderRepository.getOrderByUserId(userId);
    }

    @Override
    public List<OrderInfo> getUserOrder(int userId){
        return orderRepository.getUserOrder(userId);
    }
    @Override
    public boolean addOrder(int userId, int actitemId, int price, int amount, Date showtime, Date orderTime){
        Order saveOrder=new Order();
        saveOrder.setActitemId(actitemId);
        saveOrder.setAmount(amount);
        saveOrder.setOrderTime(orderTime);
        saveOrder.setUserId(userId);
        saveOrder.setShowtime(showtime);
        saveOrder.setPrice(price);
        orderRepository.save(saveOrder);
        return true;
    }
}
