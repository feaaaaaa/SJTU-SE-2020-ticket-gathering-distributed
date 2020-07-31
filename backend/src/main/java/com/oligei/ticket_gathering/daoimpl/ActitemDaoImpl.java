package com.oligei.ticket_gathering.daoimpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.dao.ActitemDao;
import com.oligei.ticket_gathering.entity.mongodb.ActitemMongoDB;
import com.oligei.ticket_gathering.entity.mysql.Actitem;
import com.oligei.ticket_gathering.repository.ActitemMongoDBRepository;
import com.oligei.ticket_gathering.repository.ActitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<Actitem> findAllByActivityId(Integer id) {
        List<Actitem> actitems = actitemRepository.findAllByActivityId(id);
        for (int i = 0; i < actitems.size(); ++i) {
            ActitemMongoDB actitemMongoDB = actitemMongoDBRepository.findByActitemId(actitems.get(i).getActitemId());
            if (actitemMongoDB == null) System.out.println(actitems.get(i).getActitemId() + "null");
            else actitems.get(i).setPrice(actitemMongoDB.getPrice());
        }
        return actitems;
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
    public Actitem add(int activityId, String website) {
        return actitemRepository.save(new Actitem(null, activityId, website));
    }

    @Override
    public Boolean deleteActitem(Integer actitemId) {
        actitemRepository.deleteById(actitemId);
        actitemMongoDBRepository.deleteByActitemId(actitemId);
        return true;
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
