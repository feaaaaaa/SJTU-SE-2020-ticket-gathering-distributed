package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.dto.ActivitySortpage;

import java.util.List;

public interface ActivityService {
    List<ActivitySortpage> selectSearch(String type,String name,String city);
    List<ActivitySortpage> findActivityByCategoryHome();
    List<ActivitySortpage> search(String value);
//    List<ActivitySortpage> search1(String value);
//    List<ActivitySortpage> search2(String value);
    ActivitySortpage findActivityAndActitem(Integer id);
    Boolean add(String activity);
    Boolean delete(Integer activityId);
    List<ActivitySortpage> recommendOnContent(Integer userId, Integer activityId);
    Boolean initActivity();
    Boolean clear(String cacheName);
}
