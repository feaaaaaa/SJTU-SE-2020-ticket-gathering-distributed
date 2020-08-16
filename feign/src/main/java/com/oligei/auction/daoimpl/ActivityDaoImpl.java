package com.oligei.auction.daoimpl;

import com.oligei.auction.dao.ActivityDao;
import com.oligei.auction.entity.ActivityMongoDB;
import com.oligei.auction.entity.Activity;
import com.oligei.auction.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ActivityDaoImpl implements ActivityDao {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityMongoDBRepository activityMongoDBRepository;

    @Override
    public Activity findOneById(Integer id) {
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
}
