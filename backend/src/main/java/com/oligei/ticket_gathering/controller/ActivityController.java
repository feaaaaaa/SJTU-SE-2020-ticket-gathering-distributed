package com.oligei.ticket_gathering.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.dto.ActivitySortpage;
import com.oligei.ticket_gathering.entity.mysql.Activity;
import com.oligei.ticket_gathering.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/Activity")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private int cnt=0;

    @RequestMapping("/search")
    public List<ActivitySortpage> search(@RequestParam(name = "search") String value) {
        System.out.println("value:" + value);
        return activityService.search(value);
    }

//    @RequestMapping("/search1")
//    public List<ActivitySortpage> search1(@RequestParam(name = "search") String value) {
//        System.out.println("value:" + value);
//        return activityService.search1(value);
//    }
//
//    @RequestMapping("/search2")
//    public List<ActivitySortpage> search2(@RequestParam(name = "search") String value) {
//        System.out.println("value:" + value);
//        return activityService.search2(value);
//    }

    @RequestMapping("/add")
    public Boolean add(@RequestParam(name = "activity") String activity) {
        return activityService.add(activity);
    }

    @RequestMapping("/delete")
    public Boolean delete(@RequestParam(name = "activityId") String activityid) {
        Integer activityId=Integer.parseInt(activityid);
        System.out.println("delete:"+activityId);
        return activityService.delete(activityId);
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
    public List<ActivitySortpage> findActivityByCategoryHome(){
        return activityService.findActivityByCategoryHome();
    }

    @RequestMapping("/initActivity")
    public Boolean initActivity(){
        return activityService.initActivity();
    }

    @RequestMapping("/test")
    public Boolean test() throws InterruptedException {
        System.out.println("!"+cnt);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(++cnt);
        return true;
    }

    @RequestMapping("/clear")
    public Boolean clear(@RequestParam(name = "name")String cacheName){
        return activityService.clear(cacheName);
    }

}
