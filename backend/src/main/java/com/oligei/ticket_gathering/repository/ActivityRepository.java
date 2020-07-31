package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.mysql.Activity;
import com.oligei.ticket_gathering.entity.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findAllByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);

    @Query("select a.activityId from Activity a where a.title like :title or a.actor like :actor or a.venue like :venue")
    List<Integer> findAllIdByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);
}
