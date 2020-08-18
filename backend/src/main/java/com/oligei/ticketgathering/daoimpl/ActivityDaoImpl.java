package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.entity.mongodb.ActivityMongoDB;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.neo4j.*;
import com.oligei.ticketgathering.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMongoDBRepository activityMongoDBRepository;

    @Autowired
    private ActivityNeo4jRepository activityNeo4jRepository;

    @Autowired
    private LocatedRelationshipRepository locatedRelationshipRepository;

    @Autowired
    private CityNeo4jRepository cityNeo4jRepository;

    @Autowired
    private SubcategoryNeo4jRepository subcategoryNeo4jRepository;

    @Autowired
    private IncludeRelationshipRepository includeRelationshipRepository;

    @Override
    /**
     * use activityId to find activity(no data in mongo)
     *@param id the activityId of activity
     *@return the activity
     *@author feaaaaaa
     *@date 2020.8.15
     *@throws NullPointerException when id is null
     *@throws JpaObjectRetrievalFailureException when id is invalid or no activity is found
     */
    public Activity findOneById(Integer id) {
//        Activity activity;
//        activity = activityRepository.getOne(id);
//        ActivityMongoDB activityMongoDB = activityMongoDBRepository.findByActivityId(id);
//        activity.setDescription(activityMongoDB.getDescription());
//        return activity;
        Objects.requireNonNull(id,"null id --ActivityDaoimpl findOneById");
        return activityRepository.getOne(id);
    }

    @Override
    public List<Integer> findActivityByCategoryAndCity(String type, String name, String city) {
        List<ActivityNeo4j> activityNeo4js = new ArrayList<ActivityNeo4j>();
        if (name.equals("全部"))
            activityNeo4js = activityNeo4jRepository.findActivityByCity(city);
        else if (city.equals("全国")) {
            if (type.equals("category"))
                activityNeo4js = activityNeo4jRepository.findActivityByCategory(name);
            else if (type.equals("subcategory"))
                activityNeo4js = activityNeo4jRepository.findActivityBySubcategory(name);
        }
        else {
            if (type.equals("category"))
                activityNeo4js = activityNeo4jRepository.findActivityByCategoryAndCity(name,city);
            else if (type.equals("subcategory"))
                activityNeo4js = activityNeo4jRepository.findActivityBySubcategoryAndCity(name,city);
        }
        List<Integer> activities = new ArrayList<Integer>();
        for (Object activityNeo4j: activityNeo4js) {
            ActivityNeo4j now_activityNeo4j = (ActivityNeo4j) activityNeo4j;
            activities.add(Integer.valueOf(now_activityNeo4j.getActivityId()));
        }
        return activities;
    }

    @Override
    public List<Integer> recommendOnContent(Integer userId, Integer activityId) {
        List<Integer> activities = new ArrayList<Integer>();
        List<ActivityNeo4j> activityNeo4js = activityNeo4jRepository.recommendOnContent(String.valueOf(userId), String.valueOf(activityId));
        for (Object activityNeo4j: activityNeo4js) {
            ActivityNeo4j now_activityNeo4j = (ActivityNeo4j) activityNeo4j;
            activities.add(Integer.valueOf(now_activityNeo4j.getActivityId()));
        }
        return activities;
    }

    @Override
    /**
     * save an activity with title,actor,timescale,venue,activityIcon
     *@param title,actor,timescale,venue,activityIcon
     *@return the saved activity
     *@author feaaaaaa
     *@date 2020.8.15
     *@throws NullPointerException when param is null
     */
    public Activity add(String title, String actor, String timescale, String venue, String activityicon) {
        Objects.requireNonNull(title,"null title --ActivityDaoimpl add");
        Objects.requireNonNull(actor,"null actor --ActivityDaoimpl add");
        Objects.requireNonNull(timescale,"null timescale --ActivityDaoimpl add");
        Objects.requireNonNull(venue,"null venue --ActivityDaoimpl add");
        Objects.requireNonNull(activityicon,"null activityIcon --ActivityDaoimpl add");
        Activity activity=new Activity(null,title,actor,timescale,venue,activityicon);
        return activityRepository.save(activity);
    }

    @Override
    /**
     *  use activityId to delete the activity
     * @param id the activityId of activity
     * @return delete success or fail
     * @author feaaaaaa
     * @date 2020.8.15
     * @throws NullPointerException when id is null
     * @throws JpaObjectRetrievalFailureException when id is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public Boolean delete(Integer activityId) {
        Objects.requireNonNull(activityId,"null id --ActivityDaoimpl delete");
        if(activityId<=0)
            throw new JpaObjectRetrievalFailureException(new EntityNotFoundException("invalid id --ActivityDaoImpl delete"));
        activityRepository.deleteById(activityId);
        return true;
    }


    @Override
    public ActivityNeo4j addActivityNeo4j(String activityId, String category, String subcategory, String city) {
        ActivityNeo4j activityNeo4j = new ActivityNeo4j(activityId,category,subcategory,city);
        activityNeo4jRepository.save(activityNeo4j);
        CityNeo4j cityNeo4j = cityNeo4jRepository.findByName(city);
        if (cityNeo4j != null)
            locatedRelationshipRepository.save(new LocatedRelationship(cityNeo4j,activityNeo4j));
        SubcategoryNeo4j subcategoryNeo4j = subcategoryNeo4jRepository.findByName(subcategory);
        if (subcategoryNeo4j != null)
            includeRelationshipRepository.save(new IncludeRelationship(subcategoryNeo4j,activityNeo4j));
        return activityNeo4j;
    }

    @Override
    /**
     * find max activityId
     *@return max activityId
     *@author feaaaaaa
     *@date 2020.8.15
     */
    public Integer findMaxActivityId(){
        return activityRepository.findMaxId();
    }
    //    @Override
//    public List<Activity> findAllByTitleOrVenueOrActor(String title, String venue, String actor) {
//        return activityRepository.findAllByTitleLikeOrVenueLikeOrActorLike(title, venue, actor);
//    }
//
//    @Override
//    public List<Integer> findAllIdByTitleOrVenueOrActor(String title, String venue, String actor) {
//        return activityRepository.findAllIdByTitleLikeOrVenueLikeOrActorLike(title, venue, actor);
//    }
}
