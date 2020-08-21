package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.dto.ActivitySortpage;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface ActivityService {
    List<ActivitySortpage> selectSearch(String type,String name,String city,Integer page) throws IOException, ParseException;
    List<ActivitySortpage> findActivityByCategoryHome();
    List<ActivitySortpage> search(String value, Integer page) throws IOException, ParseException;
//    List<ActivitySortpage> search1(String value);
//    List<ActivitySortpage> search2(String value);
//    ActivitySortpage findActivityAndActitem(Integer id);
    Boolean add(String activity);
    Boolean delete(Integer activityId);
    List<ActivitySortpage> recommendOnContent(Integer userId, Integer activityId);
    Boolean initActivity();
    Boolean clear();
    Boolean initSearchIndex() throws IOException;

}
