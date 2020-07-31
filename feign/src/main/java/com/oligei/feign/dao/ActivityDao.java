package com.oligei.feign.dao;

import com.oligei.feign.entity.Activity;

import java.util.List;

public interface ActivityDao {
    Activity findOneById(Integer id);
}
