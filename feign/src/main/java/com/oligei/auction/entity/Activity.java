package com.oligei.auction.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name = "tg_activities")
@Proxy(lazy = false)
public class Activity {
    private Integer activityId;
    private String title;
    private String actor;
    private String timescale;
    private String venue;
    private String activityIcon;

    public Activity(){

    }

    public Activity(Integer activityId,String title,String actor,String timescale,
                    String venue,String activityIcon)
    {
        this.activityId = activityId;
        this.title = title;
        this.actor = actor;
        this.timescale = timescale;
        this.venue = venue;
        this.activityIcon = activityIcon;
    }

    @Id
    @Column(name = "activityid")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    public Integer getActivityId(){return activityId;}
    public void setActivityId(Integer activityId){this.activityId = activityId;}

    @Column(name = "title")
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    @Column(name = "actor")
    public String getActor(){return actor;}
    public void setActor(String actor){this.actor = actor;}

    @Column(name = "timescale")
    public String getTimescale(){return timescale;}
    public void setTimescale(String timescale){this.timescale = timescale;}

    @Column(name = "venue")
    public String getVenue(){return venue;}
    public void setVenue(String venue){this.venue=venue;}

    @Column(name = "activityicon")
    public String getActivityIcon(){return activityIcon;}
    public void setActivityIcon(String activityIcon){this.activityIcon=activityIcon;}

    private String description;
    @Transient
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
}
