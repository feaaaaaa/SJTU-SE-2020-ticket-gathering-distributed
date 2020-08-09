package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.OrderDao;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Order;
import com.oligei.ticketgathering.repository.OrderRepository;
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
