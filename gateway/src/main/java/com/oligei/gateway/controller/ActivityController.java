package com.oligei.gateway.controller;

import com.oligei.gateway.GatewayApplication;
import com.oligei.gateway.dto.ActivitySortpage;
import com.oligei.gateway.service.ActivityService;
import com.oligei.gateway.util.msgutils.Msg;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/activity")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);


    @RequestMapping("/search")
    public Msg<List<ActivitySortpage>> search(@RequestParam(name = "search") String value) {
        System.out.println("value:" + value);
        try{
            return activityService.search(value);
        }catch (feign.RetryableException e){
            logger.error("请求超时",e);
            return new Msg<>(504,"请求超时，请重试",new LinkedList<>());
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",new LinkedList<>());
        }
    }

    @RequestMapping("/initActivity")
    public Msg<Boolean> initActivity() {
        try {
            return activityService.initActivity();
        }catch (feign.RetryableException e){
            System.out.println(e);
            return new Msg<>(504,"请求已发送",true);
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",false);
        }
    }

    @RequestMapping("/initSearchIndex")
    public Msg<Boolean> initSearchIndex() {
        try{
            return activityService.initSearchIndex();
        }catch (feign.RetryableException e){
            System.out.println(e);
            return new Msg<>(504,"请求已发送",true);
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",false);
        }
    }

    @RequestMapping("/clear")
    public Msg<Boolean> clear(){
        try{
            return activityService.clear();
        }catch (feign.RetryableException e){
            System.out.println(e);
            return new Msg<>(504,"请求已发送",true);
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",false);
        }
    }

    @RequestMapping("/add")
    public Msg<Boolean> add(@RequestParam(name = "activity") String activity) {
        try{
            return activityService.add(activity);
        }catch (feign.RetryableException e){
            System.out.println(e);
            return new Msg<>(504,"超时，请重试",true);
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",false);
        }
    }

    @RequestMapping("/delete")
    public Msg<Boolean> delete(@RequestParam(name = "activityId") String activityid) {
        try{
            return activityService.delete(activityid);
        }catch (feign.RetryableException e){
            System.out.println(e);
            return new Msg<>(504,"超时，请重试",true);
        }catch ( FeignException.InternalServerError e){
            logger.error("出错了",e);
            return new Msg<>(500,"出错了",false);
        }
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
