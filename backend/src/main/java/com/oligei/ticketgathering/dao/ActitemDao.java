package com.oligei.ticketgathering.dao;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticketgathering.entity.mysql.Actitem;

import java.util.List;

public interface ActitemDao {
    Actitem findOneById(Integer id);
    List<Actitem> findAllByActivityId(Integer id);
    void deleteMongoDBByActitemId(Integer actitemId);
    ActitemMongoDB insertActitemInMongo(int actitemId,List<JSONObject> price);
    Actitem add(int activityId,String website);
    Boolean deleteActitem(Integer actitemId);

    /**amount为正时为增加，amount为负时减少*/
    boolean modifyRepository(int actitemId, int price, int amount, String showtime);
}
