package com.oligei.auction.repository;

import com.oligei.auction.entity.Actitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActitemRepository extends JpaRepository<Actitem, Integer> {
    List<Actitem> findAllByActivityId(Integer id);
}
