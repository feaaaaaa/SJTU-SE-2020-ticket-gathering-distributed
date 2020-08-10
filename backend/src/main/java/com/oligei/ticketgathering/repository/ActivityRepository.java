package com.oligei.ticketgathering.repository;

import com.oligei.ticketgathering.entity.mysql.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findAllByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);

    @Query("select a.activityId from Activity a where a.title like :title or a.actor like :actor or a.venue like :venue")
    List<Integer> findAllIdByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);
}
