package com.oligei.ticket_gathering.entity.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "tg_activities")
public class ActivityMongoDB {
    @Id
    @Field("activityid")
    private Integer activityId;

    @Field("description")
    private String description;

    public ActivityMongoDB(Integer activityId,String description){
        this.activityId = activityId;
        this.description = description;
    }

    public Integer getActivityId(){return activityId;}
    public void setActivityId(Integer activityId){this.activityId = activityId;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
}
