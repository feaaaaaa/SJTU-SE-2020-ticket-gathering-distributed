package com.oligei.ticket_gathering.entity.mysql;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.entity.mongodb.ActitemMongoDB;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;


@Entity
@Table(name = "TG_ACTITEMS")
public class Actitem {
    private Integer actitemId;
    private Integer activityId;
    private String website;

    @Id
    @Column(name = "ACTITEMID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getActitemId(){return actitemId;}
    public void setActitemId(Integer actitemId){this.actitemId = actitemId;}

    @Column(name = "ACTIVITYID")
    public Integer getActivityId(){return activityId;}
    public void setActivityId(Integer activityId){this.activityId = activityId;}

    @Column(name = "WEBSITE")
    public String getWebsite(){return website;}
    public void setWebsite(String website){this.website = website;}

    private JSONObject price;
    @Transient
    public JSONObject getPrice(){return price;}
    public void setPrice(JSONObject price){this.price=price;}

}