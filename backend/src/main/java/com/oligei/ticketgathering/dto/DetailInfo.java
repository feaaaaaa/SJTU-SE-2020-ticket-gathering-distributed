package com.oligei.ticketgathering.dto;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @ClassName: DetailInfo
 * @Description: detail information
 * @Author: Cui Shaojie
 * @Date: 2020/8/14 16:11
 **/
public class DetailInfo {
    Integer key;
    String title;
    String actor;
    String timescale;
    String venue;
    String activityicon;
    String description;
    String website;
    List<JSONObject> prices;

    public DetailInfo(){}
    public DetailInfo(
            Integer key,
            String title,
            String actor,
            String timescale,
            String venue,
            String activityicon,
            String description,
            String website,
            List<JSONObject> prices
    )
    {
        this.key = key;
        this.title = title;
        this.actor = actor;
        this.timescale = timescale;
        this.venue = venue;
        this.activityicon = activityicon;
        this.description = description;
        this.website = website;
        this.prices = prices;
    }

    public Integer getKey(){return key;}
    public void setKey(Integer key){this.key = key;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}
    public String getActor(){return actor;}
    public void setActor(String actor){this.actor = actor;}
    public String getTimescale(){return timescale;}
    public void setTimescale(String timescale){this.timescale = timescale;}
    public String getVenue(){return venue;}
    public void setVenue(String venue){this.venue = venue;}
    public String getActivityicon(){return activityicon;}
    public void setActivityicon(String activityicon){this.activityicon = activityicon;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
    public String getWebsite(){return website;}
    public void setWebsite(String website){this.website = website;}
    public List<JSONObject> getPrices(){return prices;}
    public void setPrices(List<JSONObject> prices){this.prices = prices;}
}
