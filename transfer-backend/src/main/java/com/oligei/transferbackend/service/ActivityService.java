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
public interface ActivityService {

    @RequestMapping(value = "/Activity/search",method = RequestMethod.GET)
    public List<ActivitySortpage> search(@RequestParam(name = "search") String value);

    @RequestMapping(value = "/Activity/add",method = RequestMethod.POST)
    public Boolean add(@RequestParam(name = "activity") String activity);

    @RequestMapping(value = "/Activity/delete",method = RequestMethod.POST)
    public Boolean delete(@RequestParam(name = "activityId") String activityid);

    @RequestMapping(value = "/Activity/RecommendOnContent",method = RequestMethod.GET)
    public List<ActivitySortpage> recommendOnContent(@RequestParam(name = "userId") Integer userId,
                                                     @RequestParam(name = "activityId") Integer activityId);

    @RequestMapping(value = "/Activity/FindActivityByCategory",method = RequestMethod.GET)
    public List<ActivitySortpage> selectSearch(@RequestParam(name = "type")String type,
                                                         @RequestParam(name = "name")String name,
                                                         @RequestParam(name = "city")String city);

    @RequestMapping(value = "/Activity/FindActivityByCategoryHome",method = RequestMethod.GET)
    public List<ActivitySortpage> findActivityByCategoryHome();
}
