package com.oligei.ticketgathering.dao;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticketgathering.entity.mysql.Actitem;

import java.util.List;

public interface ActitemDao {
    /**
     * use actitemId to find the actitem
     */
    Actitem findOneById(Integer id);

    /**
     * use activityId to find all the actitem
     */
    List<Actitem> findAllByActivityId(Integer id);

    /**
     * delete data from moongoDB
     */
    void deleteMongoDBByActitemId(Integer actitemId);
<<<<<<< Updated upstream

    /**
     * insert into mongoDB
     */
    ActitemMongoDB insertActitemInMongo(int actitemId, List<JSONObject> price);

    /**
     * save actitem
     */
    Actitem add(int activityId, String website);

    /**
     * delete Actitem from database
     */
    Boolean deleteActitem(Integer actitemId);
=======
    ActitemMongoDB insertActitemInMongo(Integer actitemId,List<JSONObject> price);
    Actitem add(Integer activityId,String website);
//    Boolean deleteActitem(Integer actitemId);
>>>>>>> Stashed changes

    /**
     * modify data in mongoDB and Mysql
     * amount为正时为增加，amount为负时减少
     */
    boolean modifyRepository(int actitemId, int price, int amount, String showtime);
}
