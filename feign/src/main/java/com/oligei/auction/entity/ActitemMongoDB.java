package com.oligei.auction.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "tg_actitems")
public class ActitemMongoDB {
    @Id
    @Field("actitemid")
    private Integer actitemId;

    @Field("prices")
    private List<JSONObject> price;
    public List<JSONObject> getPrice() {return price;}
    public void setPrice(List<JSONObject> price){this.price = price;}

    public ActitemMongoDB(Integer actitemId,List<JSONObject> price){
        this.actitemId = actitemId;
        this.price = price;
    }

    public Integer getActitemId(){return actitemId;}
    public void setActitemId(Integer actitemId){this.actitemId = actitemId;}

}
