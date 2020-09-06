package com.oligei.auction.dao;

import com.oligei.auction.entity.Activity;

public interface ActivityDao {

    /**
     * use activityId to find activity(no data in mongo)
     */
    Activity findOneById(Integer id);
}
