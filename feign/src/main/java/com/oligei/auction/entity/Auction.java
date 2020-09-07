package com.oligei.auction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "tg_auctions")
public class Auction {

    private Integer auctionid;
    private Integer actitemid;
    private Integer userid;
    private Timestamp ddl;
    private Integer initprice;
    private Integer orderprice;
    private Integer isover;
    private Date showtime;
    private Timestamp ordertime;
    private Integer amount;

    public Auction() {
    }

    public Auction(Integer actitemid, Integer userid, Timestamp ddl, Integer initprice,
                   Integer orderprice, Integer isover, Date showtime, Timestamp ordertime, Integer amount) {
        this.actitemid = actitemid;
        this.userid = userid;
        this.ddl = ddl;
        this.initprice = initprice;
        this.orderprice = orderprice;
        this.isover = isover;
        this.showtime = showtime;
        this.ordertime = ordertime;
        this.amount = amount;
    }

    @Id
    @Column(name = "auctionid")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getAuctionid() {
        return auctionid;
    }

    public void setAuctionid(Integer auctionid) {
        this.auctionid = auctionid;
    }

    @Column(name = "actitemid")
    public Integer getActitemid() {
        return actitemid;
    }

    public void setActitemid(Integer actitemid) {
        this.actitemid = actitemid;
    }

    @Column(name = "userid")
    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Column(name = "ddl")
    public Timestamp getDdl() {
        return ddl;
    }

    public void setDdl(Timestamp ddl) {
        this.ddl = ddl;
    }

    @Column(name = "initprice")
    public Integer getInitprice() {
        return initprice;
    }

    public void setInitprice(Integer initprice) {
        this.initprice = initprice;
    }

    @Column(name = "orderprice")
    public Integer getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(Integer orderprice) {
        this.orderprice = orderprice;
    }

    @Column(name = "isover")
    public Integer getIsover() {
        return isover;
    }

    public void setIsover(Integer isover) {
        this.isover = isover;
    }

    @Column(name = "showtime")
    public Date getShowtime() {
        return showtime;
    }

    public void setShowtime(Date showtime) {
        this.showtime = showtime;
    }

    @Column(name = "ordertime")
    public Timestamp getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Timestamp ordertime) {
        this.ordertime = ordertime;
    }

    @Column(name = "amount")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
