package com.oligei.auction.daoimpl;

import com.oligei.auction.dao.ActivityDao;
import com.oligei.auction.entity.Activity;
import com.oligei.auction.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    /**
     * @param id the activityId of activity
     * @return the activity
     * @author feaaaaaa
     * @date 2020.8.15
     * @throws NullPointerException when id is null
     * @throws JpaObjectRetrievalFailureException when id is invalid or no activity is found
     */
    public Activity findOneById(Integer id) {
//        Activity activity;
//        activity = activityRepository.getOne(id);
//        ActivityMongoDB activityMongoDB = activityMongoDBRepository.findByActivityId(id);
//        activity.setDescription(activityMongoDB.getDescription());
//        return activity;
        Objects.requireNonNull(id, "null id --ActivityDaoimpl findOneById");
        return activityRepository.getOne(id);
    }
}
