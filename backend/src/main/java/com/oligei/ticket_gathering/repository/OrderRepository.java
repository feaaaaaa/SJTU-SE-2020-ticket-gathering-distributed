package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.info.OrderInfo;
import com.oligei.ticket_gathering.entity.mysql.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query(value = "from Order o where o.userId = :userId")
    List<Order> getOrderByUserId(@Param("userId") Integer userId);

   @Query(value="select new com.oligei.ticket_gathering.entity.info.OrderInfo (o.orderId, o.userId, o.actitemId, o.price, o.amount,o.showtime,o.orderTime,at.title,at.venue,at.activityIcon) " +
           "from Order o join Actitem ai on o.actitemId=ai.actitemId join Activity at on ai.activityId=at.activityId where o.userId=:userId")
   List<OrderInfo> getUserOrder(@Param("userId")Integer userId);
}
