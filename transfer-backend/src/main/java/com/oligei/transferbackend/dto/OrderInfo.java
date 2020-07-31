package com.oligei.transferbackend.dto;

import com.oligei.transferbackend.entity.Activity;
import com.oligei.transferbackend.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderInfo {
    private Integer orderId;
    private Integer userId;
    private Integer actitemId;
    private Integer price;
    private Integer amount;
    private String showtime;
    private String orderTime;

    private String title;
    private String venue;
    private String activityIcon;

    OrderInfo(){}

    public OrderInfo(Order order, Activity activity){
        this.orderId=order.getOrderId();
        this.userId=order.getUserId();
        this.actitemId=order.getActitemId();
        this.price=order.getPrice();
        this.amount=order.getAmount();
        this.title=activity.getTitle();
        this.venue=activity.getVenue();
        this.activityIcon=activity.getActivityIcon();

        SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy-MM-dd" );
        Date tmp=order.getShowtime();
        this.showtime=sdf2.format(tmp);
        tmp=order.getOrderTime();
        this.orderTime=sdf1.format(tmp);
    }

    public Integer getOrderId(){return orderId;}

    public Integer getUserId(){return userId;}

    public Integer getActitemId(){return actitemId;}

    public Integer getPrice(){return price;}

    public Integer getAmount(){return amount;}

    public String getShowtime(){return showtime;}

    public String getOrderTime(){return orderTime;}

    public String getTitle(){return title;}

    public String getVenue(){return venue;}

    public String getActivityIcon(){return activityIcon;}

}