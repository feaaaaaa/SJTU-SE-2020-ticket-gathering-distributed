package com.oligei.gateway.service;

import com.oligei.gateway.dto.ActivitySortpage;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "ticketgathering")
public interface ActivityService {

    @RequestMapping(value="/Activity/initActivity",method = RequestMethod.GET)
    Msg<Boolean> initActivity();

    @RequestMapping(value = "/Activity/initSearchIndex",method = RequestMethod.GET)
    Msg<Boolean> initSearchIndex();

    @RequestMapping(value = "/Activity/clear",method = RequestMethod.GET)
    Msg<Boolean> clear();

    @RequestMapping(value = "/Activity/search",method = RequestMethod.GET)
    public Msg<List<ActivitySortpage>> search(@RequestParam(name = "search") String value);

    @RequestMapping(value = "/Activity/add",method = RequestMethod.POST)
    public Msg<Boolean> add(@RequestParam(name = "activity") String activity);

    @RequestMapping(value = "/Activity/delete",method = RequestMethod.POST)
    public Msg<Boolean> delete(@RequestParam(name = "activityId") String activityid);

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
