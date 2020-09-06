package com.oligei.ticketgathering.controller;

import com.oligei.ticketgathering.TicketGatheringApplication;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.service.OrderService;
import com.oligei.ticketgathering.util.msgutils.Msg;
import com.oligei.ticketgathering.util.msgutils.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Order")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
    @Autowired
    private OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(TicketGatheringApplication.class);

    @RequestMapping("/GetOrderInfoByUser")
    /**
     *  get order using userId
     * @param userId
     * @return OrderInfo
     * @author Yang Yicheng
     * @date 2020/8/10
     */
    public Msg getOrderInfoByUser(@RequestParam("userId") int userId) {
        List<OrderInfo> result;
        try {
            result = orderService.getUserOrder(userId);
        } catch (NullPointerException e) {
            logger.error("NullPointerException", e);
            return new Msg<List<OrderInfo>>(201, "未找到订单", null);
        } catch (InvalidDataAccessApiUsageException e) {
            logger.error("InvalidDataAccessApiUsageException", e);
            return new Msg<List<OrderInfo>>(201, "非法参数", null);
        }
        return new Msg<List<OrderInfo>>(200, "查询成功", result);
    }

    @RequestMapping("/addOrder")
    /**
     *  insert an order into database
     * @param userId,actitemId,initPrice,orderPrice,amount,showtime,orderTime
     * @return boolean
     * @author Yang Yicheng
     * @date 2020/8/12
     */
    public Msg addOrder(@RequestParam("userId") int userId, @RequestParam("actitemId") int actitemId,
                        @RequestParam("initPrice") int initPrice, @RequestParam("orderPrice") int orderPrice, @RequestParam("amount") int amount,
                        @RequestParam("showtime") String showtime, @RequestParam("orderTime") String orderTime) {
        boolean result;
        try {
            result = orderService.addOrder(userId, actitemId, initPrice, orderPrice, amount, showtime, orderTime);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("ArrayIndexOutOfBoundsException", e);
            return new Msg<Boolean>(201, "未找到符合showtime和price的商品", false);
        } catch (ArithmeticException e) {
            logger.error("ArithmeticException", e);
            return new Msg<Boolean>(201, "该商品已卖完", false);
        } catch (NullPointerException e) {
            logger.error("NullPointerException", e);
            return new Msg<Boolean>(201, "该商品id不存在", false);
        }
        if (!result)
            return new Msg<>(202, "余额不足", false);
        return new Msg<Boolean>(200, "下单成功", true);
    }
}
