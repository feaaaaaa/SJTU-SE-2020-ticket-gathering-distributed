package com.oligei.ticketgathering.dao;

import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.neo4j.ActivityNeo4j;

import java.util.List;

public interface ActivityDao {
    /**
     * use activityId to find activity(no data in mongo)
     */
    Activity findOneById(Integer id);
//    List<Activity> findAllByTitleOrVenueOrActor(String title,String venue,String actor);
//    List<Integer> findAllIdByTitleOrVenueOrActor(String title,String venue,String actor);

    /**
     * use activityId to find activity(no data in mongo)
     */
    List<Integer> findActivityByCategoryAndCity(String type, String name, String city);

    /**
     * save an activity with title,actor,timescale,venue,activityIcon
     */
    Activity add(String title, String actor, String timescale, String venue, String activityicon);

    /**
     * add activity to neo4j
     */
    ActivityNeo4j addActivityNeo4j(String activityId, String category, String subcategory, String city);

    /**
     * use activityId to delete the activity
     */
    Boolean delete(Integer activityId);

    /**
     * use userId and activityId to get id of activity that is the same kind of activity and haven't been seen by the user
     */
    List<Integer> recommendOnContent(Integer userId, Integer activityId);

    /**
     * find max activityId
     */
    Integer findMaxActivityId();
}
