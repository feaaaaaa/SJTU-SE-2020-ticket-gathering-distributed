package com.oligei.ticketgathering.entity.mysql;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tg_orders")
public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer actitemId;
    private Integer price;
    private Integer amount;
    private Date showtime;
    private Date orderTime;

    @Id
    @Column(name = "orderid")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getOrderId(){return orderId;}
    public void setOrderId(Integer orderId){this.orderId = orderId;}

    @Column(name = "userid")
    public Integer getUserId(){return userId;}
    public void setUserId(Integer userId){this.userId = userId;}

    @Column(name = "actitemid")
    public Integer getActitemId(){return actitemId;}
    public void setActitemId(Integer actitemId){this.actitemId =actitemId;}

    @Column(name = "price")
    public Integer getPrice(){return price;}
    public void setPrice(Integer price){this.price = price;}

    @Column(name = "amount")
    public Integer getAmount(){return amount;}
    public void setAmount(Integer amount){this.amount = amount;}

    @Column(name = "showtime")
    public Date getShowtime(){return showtime;}
    public void setShowtime(Date showtime){this.showtime = showtime;}

    @Column(name = "ordertime")
    public Date getOrderTime(){return orderTime;}
    public void setOrderTime(Date orderTime){this.orderTime = orderTime;}
}
