package com.oligei.auction.dao;

import com.oligei.auction.entity.Activity;

public interface ActivityDao {
    Activity findOneById(Integer id);
}
