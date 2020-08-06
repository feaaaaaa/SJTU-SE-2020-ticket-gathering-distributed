package com.oligei.auction.repository;

import com.oligei.auction.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findAllByTitleLikeOrVenueLikeOrActorLike(String title, String venue, String actor);;
}
