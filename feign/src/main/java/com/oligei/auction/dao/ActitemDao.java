package com.oligei.auction.dao;

import com.alibaba.fastjson.JSONObject;
import com.oligei.auction.entity.Actitem;
import com.oligei.auction.entity.ActitemMongoDB;

import java.util.List;

public interface ActitemDao {

    /**
     * modify data in mongoDB and Mysql
     */
    boolean modifyRepository(int actitemId, int price, int amount, String showtime);

    /**
     * use actitemId to find the actitem
     */
    Actitem findOneById(Integer id);

    /**
     * delete data from moongoDB
     */
    void deleteMongoDBByActitemId(Integer actitemId);

    /**
     * insert into mongoDB
     */
    ActitemMongoDB insertActitemInMongo(int actitemId, List<JSONObject> price);
}
