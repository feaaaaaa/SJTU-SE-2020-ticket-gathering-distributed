package com.oligei.ticketgathering.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.oligei.ticketgathering.entity.mysql.Actitem;

import java.util.List;

@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
public class ActivitySortpage {

    Integer activityId;
    String title;
    String actor;
    String timescale;
    String venue;
    String activityIcon;
    List<Actitem> actitems;

    public ActivitySortpage() {}
    public ActivitySortpage(Integer activityId,String title,String actor,String timescale,
                            String venue, String activityIcon, List<Actitem> actitems) {
        this.activityId=activityId;
        this.title=title;
        this.actor=actor;
        this.timescale=timescale;
        this.venue=venue;
        this.activityIcon=activityIcon;
        this.actitems=actitems;
    }

    public Integer getActivityId() {return activityId;}
    public void setActivityId(Integer activityId) {this.activityId=activityId;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title=title;}
    public String getActor() {return actor;}
    public void setActor(String actor) {this.actor=actor;}
    public String getTimescale() {return timescale;}
    public void setTimescale(String timescale) {this.timescale=timescale;}
    public String getVenue() {return venue;}
    public void setVenue(String venue) {this.venue=venue;}
    public String getActivityIcon() {return activityIcon;}
    public void setActivityIcon(String activityIcon) {this.activityIcon=activityIcon;}
    public List<Actitem> getActitems() {return actitems;}
    public void setActitems(List<Actitem> actitems) {this.actitems=actitems;}
}
