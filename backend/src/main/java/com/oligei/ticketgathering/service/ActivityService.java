package com.oligei.ticketgathering.service;

import com.oligei.ticketgathering.dto.ActivitySortpage;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;
import java.util.List;

public interface ActivityService {
    /**
     * use index to get search id, then get data from cache and return
     */
    List<ActivitySortpage> selectSearch(String type, String name, String city, Integer page) throws IOException, ParseException;

    /**
     * how many pages?
     */
    Integer selectSearchPageNum(String type, String name, String city) throws IOException, ParseException;

    /**
     * call findActivityByOneCategoryHome 8 times to find a list of activitySortpage for homepage
     */
    List<ActivitySortpage> findActivityByCategoryHome();

    /**
     * use index to get search id, then get data from cache and return
     */
    List<ActivitySortpage> search(String value, Integer page) throws IOException, ParseException;

    /**
     * how many pages?
     */
    Integer searchPageNum(String value) throws IOException, ParseException;
//    List<ActivitySortpage> search1(String value);
//    List<ActivitySortpage> search2(String value);
//    ActivitySortpage findActivityAndActitem(Integer id);

    /**
     * parse the string info of activity and save it
     */
    Boolean add(String activity) throws IOException;

//    /**
//     * use activityId to delete activity and all the actitem
//     */
//    Boolean delete(Integer activityId);

    /**
     * use userId and activityId to get ActivitySortpage that is the same kind of activity and haven't been seen by the user
     */
    List<ActivitySortpage> recommendOnContent(Integer userId, Integer activityId);

//    /**
//     * dd all the activitySortpage into cache
//     */
////    Boolean initActivity();

//    /**
//     * clear cache which have home cache and select search
//     */
//    Boolean clear();
//    Boolean initSearchIndex() throws IOException;

}
