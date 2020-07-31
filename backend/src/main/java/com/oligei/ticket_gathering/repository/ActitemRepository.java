package com.oligei.ticket_gathering.repository;

import com.oligei.ticket_gathering.entity.mysql.Actitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActitemRepository extends JpaRepository<Actitem,Integer> {
    List<Actitem> findAllByActivityId(Integer id);
}
