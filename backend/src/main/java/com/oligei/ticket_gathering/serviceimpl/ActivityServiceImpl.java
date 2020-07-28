package com.oligei.ticket_gathering.serviceimpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticket_gathering.dao.ActitemDao;
import com.oligei.ticket_gathering.dao.ActivityDao;
import com.oligei.ticket_gathering.dao.UserDao;
import com.oligei.ticket_gathering.dto.ActivitySortpage;
import com.oligei.ticket_gathering.entity.info.Cache;
import com.oligei.ticket_gathering.entity.mysql.Actitem;
import com.oligei.ticket_gathering.entity.mysql.Activity;
import com.oligei.ticket_gathering.entity.mysql.User;
import com.oligei.ticket_gathering.entity.neo4j.ActivityNeo4j;
import com.oligei.ticket_gathering.service.ActivityService;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apdplat.word.segmentation.Segmentation;
import org.apdplat.word.WordSegmenter;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActitemDao actitemDao;

    @Autowired
    private UserDao userDao;

//    @Autowired
    private Cache<List<ActivitySortpage>> cache=new Cache<>();

    private Cache<ActivitySortpage> oneCache=new Cache<>();

    private Cache<List<Activity>> activityCache=new Cache<>();

    private  int searchResultMax=50;


    @Override
    public List<ActivitySortpage> search(String value) {
        if(value==null || value.equals("")|| value.equals("null")){
            List<ActivitySortpage> cacheResult=cache.getValue("searchNull");
            if(cacheResult!=null){
                System.out.println("search null get from cache");
                return cacheResult;
            }
            List<ActivitySortpage> activities=new LinkedList<>();
            for(int i=2;i<=50;++i){
                activities.add(findActivityAndActitem(i));
            }
//            activities.add(findActivityAndActitem(1417));
            cache.addOrUpdateCache("searchNull",activities);
            System.out.println("search null add into cache");
            return activities;
        }
        List<Word> words= WordSegmenter.seg(value);
        System.out.println("words:"+words+words.size());
        int n=words.size();
        if(n==1){
            String cacheName="search"+value;
            List<ActivitySortpage> cacheResult=cache.getValue(cacheName);
            if(cacheResult!=null) {
                System.out.println(cacheName+" get from cache");
                return cacheResult;
            }
        }

        List<List<Activity>> activityList=new LinkedList<>();
        Set<Integer> idSet=new LinkedHashSet<>();
        for(int i=0;i<n;++i){
            String word=words.get(i).getText();
//            String cacheName="search"+word;
//            List<Activity> result=activityCache.getValue(cacheName);
//
            word="%"+word+"%";
            List<Activity>  result=activityDao.findAllByTitleOrVenueOrActor(word,word,word);
            activityList.add(result);
            for(int j=0;j<activityList.get(i).size();++j)
                idSet.add(activityList.get(i).get(j).getActivityId());
        }

        int[] idArray=new int[idSet.size()];
        int[] cntArray=new int[idSet.size()];
        int cnt=0;
        for(Integer i:idSet) {
            System.out.println("id:"+i);
            idArray[cnt] = i;
            cntArray[cnt]=0;
            ++cnt;
        }
        Arrays.sort(idArray);

        for(int i=0;i<n;++i)
            for(int j=0;j<activityList.get(i).size();++j){
                int index=Arrays.binarySearch(idArray,activityList.get(i).get(j).getActivityId());
                cntArray[index]++;
            }

        List<ActivitySortpage> activities=new LinkedList<>();
//        int basic=0;
//        if(n>2)basic=1;
//        if(n>5)basic=2;
//        if(n>10)basic=n-5;
        int basic=Math.max(0,n-3);
        int resultCount=0;
        for(int i=idSet.size();i>basic&&resultCount<=searchResultMax;--i){
            for(int j=0;j<idSet.size();++j){
                if(cntArray[j]==i) {
                        ActivitySortpage activitySortpage = findActivityAndActitem(idArray[j]);
                        activities.add(activitySortpage);
                        resultCount++;
                }
            }
        }

        if(n==1){
            String cacheName="search"+value;
            cache.addOrUpdateCache(cacheName,activities);
            System.out.println(cacheName+" add into cache");
        }

        return activities;
    }

    @Override
    public ActivitySortpage findActivityAndActitem(Integer id) {

        String cacheName="ActivitySortpage"+id.toString();
        ActivitySortpage cacheResult=oneCache.getValue(cacheName);
        if(cacheResult!=null){
            System.out.println(cacheName+"get from cache");
            return cacheResult;
        }

        Activity activity;
        try {
            activity = activityDao.findOneById(id);
        } catch (javax.persistence.EntityNotFoundException e) {
            return null;
        }
        if(activity==null) return null;
        List<Actitem> actitems=actitemDao.findAllByActivityId(id);

        cacheResult= new ActivitySortpage (
                activity.getActivityId(),
                activity.getTitle(),
                activity.getActor(),
                activity.getTimescale(),
                activity.getVenue(),
                activity.getActivityIcon(),
                actitems
        );
        oneCache.addOrUpdateCache(cacheName,cacheResult);
        System.out.println(cacheName+" add into cache");
        return cacheResult;
    }

    @Override
    public Boolean add(String activity) {
        System.out.println(activity);
        activity=activity.substring(1,activity.length()-1);
        String[] arr = activity.split(",");
        System.out.println(Arrays.toString(arr));
        int webcnt=Integer.parseInt(arr[0]);
        int daycnt=Integer.parseInt(arr[1]);
        int classcnt=Integer.parseInt(arr[2]);
        String city=arr[8].substring(1,arr[8].length()-1);
        String category=arr[9].substring(1,arr[9].length()-1);
        String subcategory=arr[10].substring(1,arr[10].length()-1);
        System.out.println(city+category+subcategory);

        Activity savedActivity=activityDao.add(arr[3].substring(1,arr[3].length()-1),arr[4].substring(1,arr[4].length()-1),arr[5].substring(1,arr[5].length()-1),
                arr[6].substring(1,arr[6].length()-1),arr[7].substring(1,arr[7].length()-1));
        int activityId=savedActivity.getActivityId();
        int number=daycnt*classcnt+daycnt;
        activityDao.addActivityNeo4j(String.valueOf(activityId),category,subcategory,city);

        for(int i=0;i<webcnt;++i){
            //website
            int basic=11+i*(number+1);
            Actitem savedActitem=actitemDao.add(activityId,arr[basic].substring(1,arr[basic].length()-1));
            //date
            basic+=1;

            int actitemId=savedActitem.getActitemId();
            List<JSONObject> CLASS;
            List<JSONObject> prices=new ArrayList<>();
            JSONObject clas;

            for(int k=0;k<daycnt;++k) {
                //date
                if(k!=0)basic+=classcnt+1;
                CLASS=new ArrayList<>();
                String date=arr[basic].substring(1,arr[basic].length()-1);

                for (int j = 0; j < classcnt; ++j) {
                    String classPrice = "{price:" + arr[basic+1+j] + ",num:100}";
                    clas = JSON.parseObject(classPrice);
                    CLASS.add(clas);
                }
                JSONObject day = new JSONObject();
                day.put("time", date);
                day.put("classcnt", classcnt);
                day.put("class", CLASS);
                prices.add(day);
            }
//            JSONObject actitemMongo=new JSONObject();
//            actitemMongo.put("actitemid",actitemId);
//            actitemMongo.put("timecnt",daycnt*classcnt);
//            actitemMongo.put("prices",prices);
//            System.out.println(actitemMongo.toString());
//            List<JSONObject> tmp=new LinkedList<>();
//            tmp.add(actitemMongo);
            System.out.println("!!!!");
            System.out.println(prices.toString());
            actitemDao.insertActitemInMongo(actitemId,prices);
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Integer activityId) {
        List<Actitem> actitems=actitemDao.findAllByActivityId(activityId);
        for(Actitem a : actitems)
            actitemDao.deleteActitem(a.getActitemId());
        activityDao.delete(activityId);
        return true;
    }


    @Override
    public List<ActivitySortpage> selectSearch(String type,String name,String city) {
        if (name.equals("全部") && city.equals("全国")) return search("");

        String cacheName=name+city;
        List<Integer> activities ;
        List<ActivitySortpage> activitySortpages = cache.getValue(cacheName);
        if(activitySortpages!=null){
            System.out.println(cacheName+"get from cache");
            return activitySortpages;
        }
        activitySortpages = new ArrayList<ActivitySortpage>();
        activities = activityDao.findActivityByCategoryAndCity(type,name,city);
        for (Integer activity : activities)
                activitySortpages.add(findActivityAndActitem(activity));
        cache.addOrUpdateCache(cacheName,activitySortpages);
        System.out.println(cacheName+"add into cache");
        return activitySortpages;
    }

    @Override
    public List<ActivitySortpage> findActivityByCategoryHome() {
        List<ActivitySortpage> activitySortpages = new ArrayList<ActivitySortpage>(findActivityByOneCategoryHome("儿童亲子"));
        activitySortpages.addAll(findActivityByOneCategoryHome("话剧歌剧"));
        activitySortpages.addAll(findActivityByOneCategoryHome("展览休闲"));
        activitySortpages.addAll(findActivityByOneCategoryHome("曲苑杂坛"));
        activitySortpages.addAll(findActivityByOneCategoryHome("体育"));
        activitySortpages.addAll(findActivityByOneCategoryHome("舞蹈芭蕾"));
        activitySortpages.addAll(findActivityByOneCategoryHome("音乐会"));
        activitySortpages.addAll(findActivityByOneCategoryHome("演唱会"));

//        int i = 0;
//        List<Integer> activities;
//        List<ActivitySortpage> cacheResult=new LinkedList<>();
//        List<ActivitySortpage> cacheTmp=new LinkedList<>();
//
//        cacheResult=cache.getValue("儿童亲子");
//        if(cacheResult!=null){
//            System.out.println("儿童亲子 get from cache");
//            activitySortpages.addAll(cacheResult);
//        }
//        else {
//            activities = activityDao.findActivityByCategoryAndCity("category", "儿童亲子", "全国");
//            for (Integer a : activities) {
//                activitySortpages.add(findActivityAndActitem(a));
//                if (++i >= 10) break;
//            }
//            cache.addOrUpdateCache("儿童亲子",activitySortpages);
//        }
//
//        cacheResult=cache.getValue("话剧歌剧");
//        if(cacheResult!=null){
//            System.out.println("话剧歌剧 get from cache");
//            activitySortpages.addAll(cacheResult);
//        }
//        else {
//            activities = activityDao.findActivityByCategoryAndCity("category", "话剧歌剧", "全国");
//            for (Integer a : activities) {
//                activitySortpages.add(findActivityAndActitem(a));
//                if (++i >= 20) break;
//            }
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","旅游展览","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=30)break;
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","曲苑杂坛","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=40)break;
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","体育","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=50)break;
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","舞蹈芭蕾","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=60)break;
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","音乐会","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=70)break;
//        }
//
//        activities=activityDao.findActivityByCategoryAndCity("category","演唱会","全国");
//        for(Integer a:activities){
//            activitySortpages.add(findActivityAndActitem(a));
//            if(++i>=80)break;
//        }
        return activitySortpages;
    }

    public List<ActivitySortpage> findActivityByOneCategoryHome(String name){
        List<ActivitySortpage> activitySortpages = new ArrayList<ActivitySortpage>();
        int i = 0;
        List<Integer> activities;
        List<ActivitySortpage> cacheResult=new LinkedList<>();

        cacheResult=cache.getValue(name);
        if(cacheResult!=null&&cacheResult.size()!=0){
            System.out.println(name+" get from cache");
            activitySortpages.addAll(cacheResult);
        }
        else {
            System.out.println(name+" put into cache");
            activities = activityDao.findActivityByCategoryAndCity("category", name, "全国");
            for (Integer a : activities) {
                activitySortpages.add(findActivityAndActitem(a));
                if (++i >= 10) break;
            }
            cache.addOrUpdateCache(name,activitySortpages);
        }
        return activitySortpages;
    }

    @Override
    public List<ActivitySortpage> recommendOnContent(Integer userId, Integer activityId) {
        List<Integer> activities = activityDao.recommendOnContent(userId, activityId);
        List<ActivitySortpage> activitySortpages = new ArrayList<ActivitySortpage>();
        for (Integer activity : activities) {
            ActivitySortpage activitySortpage = findActivityAndActitem(activity);
            activitySortpages.add(activitySortpage);
        }
        return activitySortpages;
    }
}
