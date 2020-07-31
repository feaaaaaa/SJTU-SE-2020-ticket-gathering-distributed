package com.oligei.feign.repository;

import com.oligei.feign.entity.Actitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActitemRepository extends JpaRepository<Actitem,Integer> {
    List<Actitem> findAllByActivityId(Integer id);
}
