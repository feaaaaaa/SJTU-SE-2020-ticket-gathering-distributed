package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.neo4j.ActivityNeo4j;

import java.util.List;

public interface ActivityDao {
    Activity findOneById(Integer id);
//    List<Activity> findAllByTitleOrVenueOrActor(String title,String venue,String actor);
//    List<Integer> findAllIdByTitleOrVenueOrActor(String title,String venue,String actor);
    List<Integer> findActivityByCategoryAndCity(String type,String name, String city);
    Activity add(String title,String actor,String timescale,String venue,String activityicon);
    ActivityNeo4j addActivityNeo4j(String activityId, String category, String subcategory, String city);
    Boolean delete(Integer activityId);
    List<Integer> recommendOnContent(Integer userId, Integer activityId);
    Integer findMaxActivityId();
}
