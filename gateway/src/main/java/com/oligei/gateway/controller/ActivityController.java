package com.oligei.gateway.controller;

import com.oligei.gateway.dto.ActivitySortpage;
import com.oligei.gateway.service.ActivityService;
import com.oligei.gateway.util.msgutils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActivityController {

    @Autowired
    private ActivityService activityService;


    @RequestMapping("/search")
    public Msg<List<ActivitySortpage>> search(@RequestParam(name = "search") String value) {
        System.out.println("value:" + value);
        return activityService.search(value);
    }

    @RequestMapping("/initActivity")
    public Msg<Boolean> initActivity() {
        return activityService.initActivity();
    }

    @RequestMapping("/initSearchIndex")
    public Msg<Boolean> initSearchIndex() {
        return activityService.initSearchIndex();
    }

    @RequestMapping("/clear")
    public Msg<Boolean> clear(){
        return activityService.clear();
    }

    @RequestMapping("/add")
    public Msg<Boolean> add(@RequestParam(name = "activity") String activity) {
        return activityService.add(activity);
    }

    @RequestMapping("/delete")
    public Msg<Boolean> delete(@RequestParam(name = "activityId") String activityid) {
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
