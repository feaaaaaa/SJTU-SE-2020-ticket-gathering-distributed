package com.oligei.ticketgathering.daoimpl;

import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.entity.mongodb.ActivityMongoDB;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.entity.neo4j.*;
import com.oligei.ticketgathering.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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
    public Activity findOneById(Integer id) {
//        Activity activity = activityRepository.getOne(id);
//        Optional<ActivityMongoDB> activityMongoDB = activityMongoDBRepository.findById(id);
//        if(activityMongoDB.isPresent()){
//            activity.setDescription(activityMongoDB.get().getDescription());
//        }
//        return activity;
        Activity activity;
        try {
            activity = activityRepository.getOne(id);
            Optional<ActivityMongoDB> activityMongoDB = activityMongoDBRepository.findById(id);
            if (activityMongoDB.isPresent()) {
                activity.setDescription(activityMongoDB.get().getDescription());
            }
            return activity;
        } catch (javax.persistence.EntityNotFoundException e) {
            System.out.println("Wrong activityId!");
            return null;
        }
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
    public Activity add(String title, String actor, String timescale, String venue, String activityicon) {
        Activity activity=new Activity(null,title,actor,timescale,venue,activityicon);
        return activityRepository.save(activity);
    }

    @Override
    public Boolean delete(Integer activityId) {
        activityRepository.deleteById(activityId);
        return true;
    }

    @Override
    public List<Activity> findAllByTitleOrVenueOrActor(String title, String venue, String actor) {
        return activityRepository.findAllByTitleLikeOrVenueLikeOrActorLike(title, venue, actor);
    }

    @Override
    public List<Integer> findAllIdByTitleOrVenueOrActor(String title, String venue, String actor) {
        return activityRepository.findAllIdByTitleLikeOrVenueLikeOrActorLike(title, venue, actor);
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
}
