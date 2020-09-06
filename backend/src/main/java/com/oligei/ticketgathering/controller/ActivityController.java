package com.oligei.ticketgathering.controller;

import com.oligei.ticketgathering.TicketGatheringApplication;
import com.oligei.ticketgathering.dto.ActivitySortpage;
import com.oligei.ticketgathering.service.ActivityService;
import com.oligei.ticketgathering.util.msgutils.Msg;
import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/Activity")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private static final Logger logger = LoggerFactory.getLogger(TicketGatheringApplication.class);


//    @RequestMapping("/initSearchIndex")
//    /**
//     *  initialize search index
//     * @return Msg(status,msg,Boolean) 200 is OK, 201 is predicted exception, 202 is unpredicted exception, 203 is no exception but wrong
//     * @author feaaaaaa
//     * @date 2020.8.17
//     */
//    public Msg<Boolean> initSearchIndex() {
//        Boolean flag;
//        try{
//            flag=activityService.initSearchIndex();
//        }catch (IOException e){
//            logger.error("索引文件无法打开",e);
//            return new Msg<>(201, "索引文件无法打开", false);
//        }catch (JpaObjectRetrievalFailureException e) {
//            logger.error("查找最大activityId错误", e);
//            return new Msg<>(201, "查找最大activityId错误", false);
//        }catch (Exception e){
//            logger.error("其他错误",e);
//            return new Msg<>(202,"其他错误",false);
//        }
//        if(!flag) {
//            logger.error("错误 --/Activity/initSearchIndex");
//            return new Msg<>(203, "错误", false);
//        }
//        return new Msg<>(200,"搜索索引初始化成功", true);
//    }

    @RequestMapping("/searchPageNum")
    /**
     *  how many pages?
     * @param value
     * @return Msg(status, msg, ListA of ActivitySortpage) 200 is OK, 201 is predicted exception, 202 is unpredicted exception
     * @author ziliuziliu
     * @date 2020/8/21
     */
    public Msg<Integer> searchPageNum(@RequestParam(name = "search") String value) {
        Integer pageNum;
        try {
            pageNum = activityService.searchPageNum(value);
        } catch (IOException e) {
            logger.error("索引文件无法打开", e);
            return new Msg<>(201, "索引文件无法打开", 0);
        }catch (ParseException e){
            logger.error("关键词解析失败",e);
            return new Msg<>(201,"关键词解析失败", 0);
        }catch (JpaObjectRetrievalFailureException e){
            logger.error("使用非法id进行查询",e);
            return new Msg<>(201,"使用非法id进行查询", 0);
        } catch (EmptyResultDataAccessException e){
            logger.error("activity查找失败",e);
            return new Msg<>(201,"activity查找失败", 0);
        } catch (InvalidDataAccessApiUsageException e) {
            logger.error("invalid category",e);
            return new Msg<>(201,"invalid category",0);
        }
        return new Msg<>(200, "搜索成功", pageNum);
    }

    @RequestMapping("/search")
    /**
     *  use value to search
     * @param value,page
     * @return Msg(status, msg, ListA of ActivitySortpage) 200 is OK, 201 is predicted exception, 202 is unpredicted exception
     * @author feaaaaaa, ziliuizliu
     * @date 2020.8.17
     */
    public Msg<List<ActivitySortpage>> search(@RequestParam(name = "search") String value,
                                              @RequestParam(name = "page") String pageStr) {
        System.out.println("value:" + value);
        List<ActivitySortpage> activitySortpages;
        try{
            Integer page = Integer.valueOf(pageStr);
            activitySortpages=activityService.search(value,page);
        }catch (IOException e){
            logger.error("索引文件无法打开",e);
            return new Msg<>(201, "索引文件无法打开", new LinkedList<>());
        } catch (ParseException e) {
            logger.error("关键词解析失败", e);
            return new Msg<>(201, "关键词解析失败", new LinkedList<>());
        } catch (JpaObjectRetrievalFailureException e) {
            logger.error("使用非法id进行查询", e);
            return new Msg<>(201, "使用非法id进行查询", new LinkedList<>());
        } catch (EmptyResultDataAccessException e) {
            logger.error("activity查找失败", e);
            return new Msg<>(201, "activity查找失败", new LinkedList<>());
        } catch (IndexOutOfBoundsException e) {
            logger.error("找不到搜索结果",e);
            return new Msg<>(201,"找不到搜索结果",new LinkedList<>());
        }
        return new Msg<>(200, "搜索成功", activitySortpages);
    }

    @RequestMapping("/add")
    /**
     *  add activity&actitems
     * @param activity info of activity which to be saved
     * @return Msg(status, msg, Boolean) 200 is OK, 201 is predicted exception, 202 is unpredicted exception, 203 is no exception but wrong
     * @author feaaaaaa
     * @date 2020.8.17
     */
    public Msg<Boolean> add(@RequestParam(name = "activity") String activity) {
        Boolean flag;
        try {
            flag = activityService.add(activity);
        } catch (NullPointerException e) {
            logger.error("参数丢失", e);
            return new Msg<>(201, "参数丢失", false);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            logger.error("存储activity格式错误", e);
            return new Msg<>(201, "存储activity格式错误", false);
        } catch (Exception e) {
            logger.error("其他错误", e);
            return new Msg<>(202, "其他错误", false);
        }
        if (!flag) {
            logger.error("错误 --/Activity/add");
            return new Msg<>(203, "错误", false);
        }
        return new Msg<>(200, "添加成功", true);
    }

//
//    @RequestMapping("/delete")
//    /**
//     *  delete activity and actitems
//     * @param activityId id of activity and actitem that to be deleted
//     * @return Msg(status,msg,Boolean) 200 is OK, 201 is predicted exception, 203 is no exception but wrong
//     * @author feaaaaaa
//     * @date 2020.8.17
//     */
//    public Msg<Boolean> delete(@RequestParam(name = "activityId") String activityid) {
//        Integer activityId=Integer.parseInt(activityid);
//        System.out.println("delete:"+activityId);
//        Boolean flag;
//        try{
//            flag=activityService.delete(activityId);
//        }catch (NullPointerException e){
//            logger.error("参数丢失",e);
//            return new Msg<>(201,"参数丢失",false);
//        }catch (JpaObjectRetrievalFailureException e){
//            logger.error("非法id",e);
//            return new Msg<>(201,"非法id",false);
//        }catch (EmptyResultDataAccessException e){
//            logger.error("id错误，找不到activity或actitem",e);
//            return new Msg<>(201,"id错误，找不到activity或actitem",false);
//        }
//        if(!flag) {
//            logger.error("错误 --/Activity/delete");
//            return new Msg<>(203, "错误", false);
//        }
//        return new Msg<>(200,"删除成功", true);
//    }

    @RequestMapping("/RecommendOnContent")
    /**
     *  find recommend content
     * @param userId,activityId id od user and the activity that he/she clicks
     * @return Msg(status, msg, Boolean) 200 is OK, 201 is predicted exception
     * @author ziliuziliu, feaaaaaa
     * @date 2020.8.18
     */
    public Msg<List<ActivitySortpage>> recommendOnContent(@RequestParam(name = "userId") Integer userId,
                                                          @RequestParam(name = "activityId") Integer activityId) {
        try {
            return new Msg<>(200, "成功", activityService.recommendOnContent(userId, activityId));
        } catch (NullPointerException e) {
            logger.error("空值", e);
            return new Msg<>(201, "空值", new LinkedList<>());
        } catch (InvalidDataAccessApiUsageException e) {
            logger.error("非法值", e);
            return new Msg<>(201, "非法值", new LinkedList<>());
        } catch (JpaObjectRetrievalFailureException e) {
            logger.error("内部错误", e);
            return new Msg<>(201, "内部错误", new LinkedList<>());
        } catch (EmptyResultDataAccessException e) {
            logger.error("未查找到数据", e);
            return new Msg<>(201, "未查找到数据", new LinkedList<>());
        }
    }

    @RequestMapping("/FindActivityByCategory")
    /**
     *  find activitySorpage of select search
     * @return result
     * @author ziliuziliu, feaaaaaa
     * @date 2020.8.17
     */
    public Msg<List<ActivitySortpage>> selectSearch(@RequestParam(name = "type")String type,
                                                    @RequestParam(name = "name")String name,
                                                    @RequestParam(name = "city")String city,
                                                    @RequestParam(name = "page")String pageStr) {
        try{
            Integer page = Integer.valueOf(pageStr);
            return new Msg<>(200,"成功",activityService.selectSearch(type,name,city,page));
        }catch (IOException e){
            logger.error("索引文件无法打开",e);
            return new Msg<>(201, "索引文件无法打开", new LinkedList<>());
        }catch (ParseException e){
            logger.error("关键词解析失败",e);
            return new Msg<>(201,"关键词解析失败",new LinkedList<>());
        }catch (JpaObjectRetrievalFailureException e){
            logger.error("使用非法id进行查询",e);
            return new Msg<>(201,"使用非法id进行查询",new LinkedList<>());
        } catch (EmptyResultDataAccessException e){
            logger.error("activity查找失败",e);
            return new Msg<>(201,"activity查找失败",new LinkedList<>());
        } catch(InvalidDataAccessApiUsageException e){
            logger.error("invalid category",e);
            return new Msg<>(201,"invalid category",new LinkedList<>());
        } catch(IndexOutOfBoundsException e) {
            logger.error("找不到搜索结果",e);
            return new Msg<>(201,"找不到搜索结果",new LinkedList<>());
        }
    }

    @RequestMapping("/FindActivityByCategoryPageNum")
    /**
     *  how many pages?
     * @return 200 OK, 201 Exception
     * @author ziliuziliu
     * @date 2020/8/21
     */
    public Msg<Integer> selectSearchPageNum(@RequestParam(name = "type") String type,
                                            @RequestParam(name = "name") String name,
                                            @RequestParam(name = "city") String city) {
        Integer pageNum;
        try {
            pageNum = activityService.selectSearchPageNum(type, name, city);
        } catch (IOException e) {
            logger.error("索引文件无法打开", e);
            return new Msg<>(201, "索引文件无法打开", 0);
        }catch (ParseException e){
            logger.error("关键词解析失败",e);
            return new Msg<>(201,"关键词解析失败",0);
        }catch (JpaObjectRetrievalFailureException e){
            logger.error("使用非法id进行查询",e);
            return new Msg<>(201,"使用非法id进行查询",0);
        } catch (EmptyResultDataAccessException e){
            logger.error("activity查找失败",e);
            return new Msg<>(201,"activity查找失败",0);
        } catch(InvalidDataAccessApiUsageException e){
            logger.error("invalid category",e);
            return new Msg<>(201,"invalid category",0);
        }
        return new Msg<>(200, "成功", pageNum);
    }

    @RequestMapping("/FindActivityByCategoryHome")
    /**
     *  find activitySorpage of home
     * @return
     * @author feaaaaaa
     * @date 2020.8.17
     */
    public Msg<List<ActivitySortpage>> findActivityByCategoryHome() {
        try {
            return new Msg<>(200, "成功", activityService.findActivityByCategoryHome());
        } catch (NullPointerException e) {
            logger.error("空值", e);
            return new Msg<>(201, "空值", new LinkedList<>());
        } catch (JpaObjectRetrievalFailureException e) {
            logger.error("内部错误", e);
            return new Msg<>(201, "内部错误", new LinkedList<>());
        } catch (EmptyResultDataAccessException e) {
            logger.error("未查找到数据", e);
            return new Msg<>(201, "未查找到数据", new LinkedList<>());
        }
    }

//    @RequestMapping("/initActivity")
//    /**
//     *  add all the activity into cache
//     * @return Msg(status,msg,Boolean) 200 is OK, 201 is predicted exception, 203 is no exception but wrong
//     * @author feaaaaaa
//     * @date 2020.8.17
//     */
//    public Msg<Boolean> initActivity(){
//        Boolean flag;
//        try{
//            flag=activityService.initActivity();
//        }catch (JpaObjectRetrievalFailureException e){
//            logger.error("查找activity失败",e);
//            return new Msg<>(201,"查找activity失败",false);
//        }
//        if(!flag) {
//            logger.error("错误 --/Activity/initActivity");
//            return new Msg<>(203, "错误", false);
//        }
//        return new Msg<>(200,"初始化成功",true);
//    }

//    @RequestMapping("/clear")
//    /**
//     *  clear cache of home & search null
//     * @return Msg(status,msg,Boolean) 200 is OK, 203 is no exception but wrong
//     * @author feaaaaaa
//     * @date 2020.8.17
//     */
//    public Msg<Boolean> clear(){//@RequestParam(name = "name")String cacheName
//        Boolean flag=activityService.clear();
//        if(!flag){
//            logger.error("错误 --/Activity/clear");
//            return new Msg<>(203, "错误", false);
//        }
//        return new Msg<>(200,"清除成功", true);
//    }

}
