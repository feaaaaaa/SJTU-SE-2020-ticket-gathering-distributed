package com.oligei.auction.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
public class AuctionListItem {

    private Integer auctionid;
    private Timestamp ddl;
    private Integer price;
    private Date showtime;
    private Integer amount;
    private String title;
    private String actor;
    private String venue;
    private Integer userid;
    private String activityIcon;
    private Integer deposit;
    private Set<Integer> users = new HashSet<>();

    public AuctionListItem(){}
    public AuctionListItem(
            Integer auctionid,
            Timestamp ddl,
            Integer price,
            Date showtime,
            Integer amount,
            String title,
            String actor,
            String venue,
            Integer userid,
            String activityIcon,
            Integer deposit
    )
    {
        this.auctionid = auctionid;
        this.ddl = ddl;
        this.price = price;
        this.showtime = showtime;
        this.amount = amount;
        this.title = title;
        this.actor = actor;
        this.venue = venue;
        this.userid = userid;
        this.activityIcon = activityIcon;
        this.deposit=deposit;
    }

    public Integer getAuctionid(){return auctionid;}
    public void setAuctionid(Integer auctionid){this.auctionid = auctionid;}

    public Date getDdl(){return ddl;}
    public void setDdl(Timestamp ddl){this.ddl = ddl;}

    public Integer getPrice(){return price;}
    public void setPrice(Integer price){this.price = price;}

    public Date getShowtime(){return showtime;}
    public void setShowtime(Date showtime){this.showtime = showtime;}

    public Integer getAmount(){return amount;}
    public void setAmount(Integer amount){this.amount = amount;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public String getActor(){return actor;}
    public void setActor(String actor){this.actor = actor;}

    public String getVenue(){return venue;}
    public void setVenue(String venue){this.venue = venue;}

    public Integer getUserid(){return userid;}
    public void setUserid(Integer userid){this.userid = userid;}

    public String getActivityIcon(){return activityIcon;}
    public void setActivityIcon(String activityIcon){this.activityIcon = activityIcon;}

    public Integer getDeposit() {
        return deposit;
    }
    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public boolean userExists(Integer userid) {return users.contains(userid);}
    public void addUser(Integer userid) {users.add(userid);}
}


