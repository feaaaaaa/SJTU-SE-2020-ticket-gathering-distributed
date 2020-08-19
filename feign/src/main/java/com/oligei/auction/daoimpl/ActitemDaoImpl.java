package com.oligei.auction.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oligei.auction.dao.ActitemDao;
import com.oligei.auction.entity.ActitemMongoDB;
import com.oligei.auction.entity.Actitem;
import com.oligei.auction.repository.ActitemMongoDBRepository;
import com.oligei.auction.repository.ActitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ActitemDaoImpl implements ActitemDao {

    @Autowired
    private ActitemRepository actitemRepository;

    @Autowired
    private ActitemMongoDBRepository actitemMongoDBRepository;

    @Override
    public Actitem findOneById(Integer id) {
        Actitem actitem = actitemRepository.getOne(id);
        ActitemMongoDB actitemMongoDB = actitemMongoDBRepository.findByActitemId(id);
        actitem.setPrice(actitemMongoDB.getPrice());
        return actitem;
    }

    @Override
    public void deleteMongoDBByActitemId(Integer actitemId) {
        actitemMongoDBRepository.deleteByActitemId(actitemId);
    }

    @Override
    public ActitemMongoDB insertActitemInMongo(int actitemId, List<JSONObject> price) {
        ActitemMongoDB actitemMongoDB = new ActitemMongoDB(actitemId, price);
        return actitemMongoDBRepository.save(actitemMongoDB);
    }


    @Override
    public boolean modifyRepository(int actitemId, int price, int amount, String showtime) {
        Actitem actitem = findOneById(actitemId);
        List<JSONObject> prices = actitem.getPrice();
//        showtime =  showtime.substring(0,9);
        System.out.println(showtime);
        int i, j, repository = 0;
        for (i = 0; i < prices.size(); i++) {
            if (Objects.equals(showtime, prices.get(i).getString("time"))) {
                break;
            }
        }
        if(i==prices.size()){
            System.out.println("no actitem found");
            return false;
        }
        JSONObject tmp = prices.get(i);
        JSONArray tickets = tmp.getJSONArray("class");
        for (j = 0; j < tickets.size(); j++) {
            JSONObject ticket = tickets.getJSONObject(j);
            if (Objects.equals(price, Integer.parseInt(ticket.getString("price")))) {
                repository = Integer.parseInt(ticket.getString("num"));
                if (Objects.equals(0, repository)) {
                    System.out.println("the num is zero");
                    return false;
                } else {
                    repository = repository +amount;
                    ticket.put("num", repository);
                    tickets.set(j, ticket);
                    break;
                }
            }
        }
        if(j==tickets.size()){
            System.out.println("no actitem found");
            return false;
        }
        tmp.put("class", tickets);
        prices.set(i, tmp);

        deleteMongoDBByActitemId(actitemId);
        insertActitemInMongo(actitemId, prices);
        return true;
    }
}
