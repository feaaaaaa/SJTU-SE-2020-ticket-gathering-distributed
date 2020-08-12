package com.oligei.ticketgathering.serviceimpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.dao.UserDao;
import com.oligei.ticketgathering.dto.ActivitySortpage;
import com.oligei.ticketgathering.entity.info.Cache;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.service.ActivityService;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apdplat.word.WordSegmenter;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
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

    private Cache<List<Integer>> idSetCache=new Cache<>();

    private  int searchResultMax=30;


    @Override
    public Boolean initSearchIndex() throws IOException {
        String content;
        int idmax = 12699;
        Activity activity;
        Collection<Document> docs = new ArrayList<>();
        for (Integer id = 1; id <= idmax; ++id) {
            activity = activityDao.findOneById(id);
            content = activity.getTitle() + activity.getVenue() + activity.getActor();
            System.out.println(id.toString() + content);
            Document document = new Document();
            document.add(new IntField("id", activity.getActivityId(), Field.Store.YES));
            document.add(new TextField("title", content, Field.Store.YES));
            docs.add(document);
        }
        Directory directory = FSDirectory.open(new File("d:\\indexDir"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        indexWriter.addDocuments(docs);
        indexWriter.commit();
        indexWriter.close();
        return true;
    }

    @Override
    public List<ActivitySortpage> search(String value) throws IOException, ParseException {
        Directory directory = FSDirectory.open(new File("d:\\indexDir"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse(value);

        // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, 30);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        List<ActivitySortpage> activitySortpages = new LinkedList<>();
        for (ScoreDoc scoreDoc : scoreDocs) {
            Document doc = reader.document(scoreDoc.doc);
            System.out.println("id: " + doc.get("id"));
//            System.out.println(activityDao.findOneById(Integer.parseInt(doc.get("id"))).getTitle());
//            System.out.println("title: " + doc.get("title"));
            System.out.println("得分： " + scoreDoc.score);
            activitySortpages.add(findActivityAndActitem(Integer.parseInt(doc.get("id"))));
        }
        return activitySortpages;
    }

    @Override
    public ActivitySortpage findActivityAndActitem(Integer id) {

        String cacheName="ActivitySortpage"+id.toString();
        ActivitySortpage cacheResult=oneCache.getValue(cacheName);
        if(cacheResult!=null){
            System.out.println(cacheName+" get from cache");
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
    public List<ActivitySortpage> selectSearch(String type,String name,String city) throws IOException, ParseException {
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

    @Override
    public Boolean initActivity() {

//        String init="初始化分词";
//        List<Word> words=WordSegmenter.seg(init);
//        System.out.println(words.toString());

        initActivityById(1000);
        initActivityById(2000);
        initActivityById(3000);
        initActivityById(4000);
        initActivityById(5000);
        initActivityById(6000);
        initActivityById(7000);
        initActivityById(8000);
        initActivityById(9000);
        initActivityById(10000);
        initActivityById(11000);
        initActivityById(12000);


        String[] citys = {"北京","天津","河北","山西","内蒙古","辽宁","吉林","黑龙江","上海","江苏","浙江","安徽","福建","江西","山东","河南","湖北",
                "湖南","广东","广西", "海南","重庆","四川","贵州","云南","西藏","陕西","甘肃","青海","宁夏","新疆","台湾","澳门","香港",
                "北京","成都","重庆","长春","长沙","大连","东莞","佛山","福州","广州","贵阳","哈尔滨","海口","杭州","合肥","呼和浩特","济南",
                "昆明","南昌","南京","南宁","宁波","青岛","厦门","上海","深圳","沈阳","石家庄","苏州","太原","天津","无锡","武汉","西安","郑州", "珠海"
        };
        for(String s :citys)
            initActivityByCity(s);
        return true;
    }

    public void initActivityById(int cnt) {
        int basic=Math.max(1,cnt-1000);
        for(int i=basic;i<cnt;++i){
            findActivityAndActitem(i);
            System.out.println("size:"+oneCache.getSize());
        }
    }

    public void initActivityByCity(String city) {
        String cacheName = "idSet" + city;
        city = "%" + city + "%";
        List<Integer> result = activityDao.findAllIdByTitleOrVenueOrActor(city, city, city);
        idSetCache.addOrUpdateCache(cacheName, result);
        System.out.println(cacheName + " add into cache");
    }

    @Override
    public Boolean clear(String cacheName) {
        if(cacheName==null||cacheName.equals("")||cacheName.equals("null"))
            idSetCache.evictCache();
        idSetCache.evictCache(cacheName);
        cache.evictCache();
        return true;
    }


}
