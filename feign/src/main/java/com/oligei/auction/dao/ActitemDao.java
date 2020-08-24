package com.oligei.auction.dao;

import com.alibaba.fastjson.JSONObject;
import com.oligei.auction.entity.Actitem;
import com.oligei.auction.entity.ActitemMongoDB;

import java.util.List;

public interface ActitemDao {
    boolean modifyRepository(int actitemId, int price, int amount, String showtime);
    Actitem findOneById(Integer id);
    void deleteMongoDBByActitemId(Integer actitemId);
    ActitemMongoDB insertActitemInMongo(int actitemId, List<JSONObject> price);
}
