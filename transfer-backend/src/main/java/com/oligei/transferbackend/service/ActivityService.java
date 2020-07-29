package com.oligei.transferbackend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.transferbackend.dto.ActivitySortpage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@FeignClient(value = "ticketGathering")
@RequestMapping("/Activity")
public interface ActivityService {

    @RequestMapping("/search")
    public List<ActivitySortpage> search(@RequestParam(name = "search") String value);

    @RequestMapping("/add")
    public Boolean add(@RequestParam(name = "activity") String activity);

    @RequestMapping("/delete")
    public Boolean delete(@RequestParam(name = "activityId") String activityid);

    @RequestMapping("/RecommendOnContent")
    public List<ActivitySortpage> recommendOnContent(@RequestParam(name = "userId") Integer userId,
                                                     @RequestParam(name = "activityId") Integer activityId);

    @RequestMapping("/FindActivityByCategory")
    public List<ActivitySortpage> selectSearch(@RequestParam(name = "type")String type,
                                                         @RequestParam(name = "name")String name,
                                                         @RequestParam(name = "city")String city);

    @RequestMapping("/FindActivityByCategoryHome")
    public List<ActivitySortpage> findActivityByCategoryHome();
}
