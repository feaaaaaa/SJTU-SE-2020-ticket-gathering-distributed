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
     * @param userId
     * @return OrderInfo
     * @author Yang Yicheng
     * @date 2020/8/10
     * @throws InvalidDataAccessApiUsageException using illegal userId
     * @throws NullPointerException Order not found
     */
    public List<OrderInfo> getUserOrder(int userId) {
        if (userId <= 0) {
            throw new InvalidDataAccessApiUsageException("illegal userId --OrderDaoImpl getUserOrder");
        }

        List<OrderInfo> result = orderRepository.getUserOrder(userId);
        if (result == null) {
            throw new NullPointerException("null Order --OrderDaoImpl getUserOrder");
        }
        return result;
    }

    @Override
    /**
     * @param saveOrder
     * @return boolean
     * @author Yang Yicheng
     * @date 2020/8/12
     */
    public boolean addOrder(Order saveOrder) {
        orderRepository.save(saveOrder);
        return true;
    }
}
