package com.oligei.ticket_gathering.entity.mysql;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tg_auctions")
public class Auction {

    private Integer auctionid;
    private Integer actitemid;
    private Integer userid;
    private Date ddl;
    private Integer price;
    private Integer isover;
    private Date showtime;
    private Date ordertime;
    private Integer amount;

    @Id
    @Column(name = "AUCTIONID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public Integer getAuctionid(){return auctionid;}
    public void setAuctionid(Integer auctionid){this.auctionid = auctionid;}

    @Column(name = "actitemid")
    public Integer getActitemid(){return actitemid;}
    public void setActitemid(Integer actitemid){this.actitemid = actitemid;}

    @Column(name = "userid")
    public Integer getUserid(){return userid;}
    public void setUserid(Integer userid){this.userid = userid;}

    @Column(name = "ddl")
    public Date getDdl(){return ddl;}
    public void setDdl(Date ddl){this.ddl = ddl;}

    @Column(name = "price")
    public Integer getPrice(){return price;}
    public void setPrice(Integer price){this.price = price;}

    @Column(name = "isover")
    public Integer getIsover(){return isover;}
    public void setIsover(Integer isover){this.isover = isover;}

    @Column(name = "showtime")
    public Date getShowtime(){return showtime;}
    public void setShowtime(Date showtime){this.showtime = showtime;}

    @Column(name = "ordertime")
    public Date getOrdertime(){return ordertime;}
    public void setOrdertime(Date ordertime){this.ordertime = ordertime;}

    @Column(name = "amount")
    public Integer getAmount(){return amount;}
    public void setAmount(Integer amount){this.amount = amount;}
}
