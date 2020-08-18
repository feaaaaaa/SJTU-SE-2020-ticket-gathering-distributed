package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.OrderDao;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.entity.mysql.Order;
import com.oligei.ticketgathering.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

//    @Override
//    public List<Order> getOrderByUserId(int userId) {
//        return orderRepository.getOrderByUserId(userId);
//    }

    @Override
    /**
     *@Description get Order using userId
     *@Param [userId]
     *@return java.util.List<com.oligei.ticketgathering.entity.info.OrderInfo>
     *@Author Yang Yicheng
     *@date 2020/8/10
     *@Throws InvalidDataAccessApiUsageException using illegal userId
     *@Throws NullPointerException Order not found
     */
    public List<OrderInfo> getUserOrder(int userId) {
        if (userId<=0){
          throw new InvalidDataAccessApiUsageException("using illegal userId");
        }

        List<OrderInfo> result=orderRepository.getUserOrder(userId);
        if(result==null){
          throw new NullPointerException("Order not found");
        }
        return result;
    }

    @Override
    /**
     *@Description insert an order into database
     *@Param [userId, actitemId, price, amount, showtime, orderTime]
     *@return boolean
     *@Author Yang Yicheng
     *@date 2020/8/12
     */
    public boolean addOrder(int userId, int actitemId, int price, int amount, Date showtime, Date orderTime) {
        Order saveOrder = new Order();
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
