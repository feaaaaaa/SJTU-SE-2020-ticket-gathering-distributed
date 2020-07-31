package com.oligei.feign.repository;

import com.oligei.feign.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findAllByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);;
}
