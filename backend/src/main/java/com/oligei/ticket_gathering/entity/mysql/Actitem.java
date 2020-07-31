package com.oligei.ticket_gathering.entity.mysql;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.entity.mongodb.ActitemMongoDB;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;


@Entity
@Table(name = "tg_actitems")
public class Actitem {
    private Integer actitemId;
    private Integer activityId;
    private String website;

    public Actitem(){

    }
    public Actitem(Integer actitemId,Integer activityId,String website)
    {
        this.actitemId = actitemId;
        this.activityId = activityId;
        this.website = website;
    }

    @Id
    @Column(name = "actitemid")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getActitemId(){return actitemId;}
    public void setActitemId(Integer actitemId){this.actitemId = actitemId;}

    @Column(name = "activityid")
    public Integer getActivityId(){return activityId;}
    public void setActivityId(Integer activityId){this.activityId = activityId;}

    @Column(name = "website")
    public String getWebsite(){return website;}
    public void setWebsite(String website){this.website = website;}

    private List<JSONObject> price;
    @Transient
    public List<JSONObject> getPrice(){return price;}
    public void setPrice(List<JSONObject> price){this.price=price;}

}
