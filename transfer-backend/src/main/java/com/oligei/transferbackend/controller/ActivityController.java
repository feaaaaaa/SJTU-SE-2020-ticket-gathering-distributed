package com.oligei.transferbackend.controller;

import com.oligei.transferbackend.dto.ActivitySortpage;
import com.oligei.transferbackend.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @RequestMapping("/search")
    public List<ActivitySortpage> search(@RequestParam(name = "search") String value) {
        System.out.println("value:" + value);
        return activityService.search(value);
    }

    @RequestMapping("/add")
    public Boolean add(@RequestParam(name = "activity") String activity) {
        return activityService.add(activity);
    }

    @RequestMapping("/delete")
    public Boolean delete(@RequestParam(name = "activityId") String activityid) {
        return activityService.delete(activityid);
    }

    @RequestMapping("/RecommendOnContent")
    public List<ActivitySortpage> recommendOnContent(@RequestParam(name = "userId") Integer userId,
                                                     @RequestParam(name = "activityId") Integer activityId) {
        return activityService.recommendOnContent(userId, activityId);
    }

    @RequestMapping("/FindActivityByCategory")
//    @RequestBody CategoryQuery categoryQuery
    public List<ActivitySortpage> selectSearch(@RequestParam(name = "type")String type,
                                               @RequestParam(name = "name")String name,
                                               @RequestParam(name = "city")String city) {
        return activityService.selectSearch(type,name,city);
    }

    @RequestMapping("/FindActivityByCategoryHome")
//    @RequestBody CategoryQuery categoryQuery
    public List<ActivitySortpage> findActivityByCategoryHome(){
        System.out.println();
        return activityService.findActivityByCategoryHome();
    }
}
