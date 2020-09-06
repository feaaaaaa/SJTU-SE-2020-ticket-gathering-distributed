package com.oligei.ticketgathering.serviceimpl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.inject.internal.cglib.proxy.$InvocationHandler;
import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.dao.UserDao;
import com.oligei.ticketgathering.dto.ActivitySortpage;
import com.oligei.ticketgathering.entity.info.Cache;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.service.ActivityService;
import com.oligei.ticketgathering.util.RedisUtil;
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
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.repository.query.Param;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.apdplat.word.WordSegmenter;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private ActitemDao actitemDao;

    //used when search null/
//    private Cache<List<ActivitySortpage>> cache=new Cache<>();

//    private Cache<ActivitySortpage> oneCache=new Cache<>();

    @Autowired
    private RedisUtil redisUtil;

    private int searchResultMax = 200;
    private int pageSize = 10;

    Directory directoryRandom = new RAMDirectory();


    @PostConstruct
    /**
     *  initialize a search index (which only need to be done once)
     * @return true if initialize finished successfully
     * @author feaaaaaa
     * @date 2020.08.14
     * @throws JpaObjectRetrievalFailureException if idmax is wrong
     * @throws IOException if open file fails
     */
    public Boolean initSearchIndex() throws IOException {
        String content;
        int idmax = activityDao.findMaxActivityId();
        System.out.println(idmax);
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
//        Directory directory = FSDirectory.open(new File("./indexDir"));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig conf = new IndexWriterConfig(Version.LATEST, analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
//        IndexWriter indexWriter = new IndexWriter(directory, conf);
        IndexWriter indexWriter1 = new IndexWriter(directoryRandom, conf);
//        indexWriter.addDocuments(docs);
//        indexWriter.commit();
//        indexWriter.close();
        indexWriter1.addDocuments(docs);
        indexWriter1.commit();
        indexWriter1.close();
        System.out.println("Initialize search index successfully!");
        return true;
    }

    @SuppressWarnings("rawtypes")
    private List<ActivitySortpage> object2ActivitySortpage(List<Object> list) {
        List<ActivitySortpage> result = new ArrayList<>();
        for (Object o : list) {
            result.add((ActivitySortpage) o);
        }
        return result;
    }

    /**
     * calculate search score, generate result
     *
     * @param value search value
     * @return search result
     * @throws NullPointerException               the param of activitySortpage is null or the mongo data of actitem is null
     * @throws IOException                        if open file fails
     * @throws ParseException                     if parse of value fails
     * @throws JpaObjectRetrievalFailureException if id is invalid
     * @throws EmptyResultDataAccessException     when activity is not found
     * @author feaaaaaa, ziliuziliu
     * @date 2020/8/21
     */
    private List<ActivitySortpage> searchResult(String value) throws IOException, ParseException {
        List<Object> resultObject = (List<Object>) redisUtil.lGetIndex(value, 0);
        List<ActivitySortpage> result;
        result = new ArrayList<>();
        if (resultObject != null) {
            result = object2ActivitySortpage(resultObject);
        }
        //not in cache
        else {
            //not null
//        Directory directory = FSDirectory.open(new File("./indexDir"));
            // 索引读取工具
//        IndexReader reader = DirectoryReader.open(directory);
            IndexReader reader = DirectoryReader.open(directoryRandom);
            // 索引搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);

            // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
            QueryParser parser = new QueryParser("title", new IKAnalyzer());
            // 创建查询对象
            Query query = parser.parse(value);

            // 搜索数据,两个参数：查询条件对象要查询的最大结果条数, 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
            TopDocs topDocs = searcher.search(query, searchResultMax);
            // 获取总条数
            System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
            // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            for (ScoreDoc scoreDoc : scoreDocs) {
                Document doc = reader.document(scoreDoc.doc);
//            System.out.println("id: " + doc.get("id"));
//            System.out.println(activityDao.findOneById(Integer.parseInt(doc.get("id"))).getTitle());
//            System.out.println("title: " + doc.get("title"));
//            System.out.println("得分： " + scoreDoc.score);
                result.add(findActivityAndActitem(Integer.parseInt(doc.get("id"))));
            }
            redisUtil.lSet(value, result);
        }
        return result;
    }

    @Override
    /**
     * @param value
     * @param page
     * @return search result for certain page
     * @author feaaaaaa, ziliuziliu
     * @date 2020.08.21
     * @throws NullPointerException the param of activitySortpage is null or the mongo data of actitem is null
     * @throws IOException if open file fails
     * @throws ParseException if parse of value fails
     * @throws JpaObjectRetrievalFailureException if id is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public List<ActivitySortpage> search(String value, Integer page) throws IOException, ParseException, IndexOutOfBoundsException {
        List<Object> resultObject;//every item
        List<ActivitySortpage> result;
        List<ActivitySortpage> activitySortpages = new ArrayList<>();//item this page should display

        //null
        if (value == null || value.equals("") || value.equals("null")) {
            resultObject = (List<Object>) redisUtil.lGetIndex("searchNull", 0);
            if (resultObject != null) {
                result = object2ActivitySortpage(resultObject);
                System.out.println("search null get from cache");
                for (int i = (page - 1) * pageSize; i < page * pageSize; i++)
                    activitySortpages.add(result.get(i));
                return activitySortpages;
            } else {
                // should not be used
                result = new ArrayList<>();
                for (int i = 10; i <= 451; i += 9) {
                    ActivitySortpage activitySortpage = findActivityAndActitem(i);
                    if (activitySortpage != null)
                        result.add(activitySortpage);
                }
                redisUtil.lSet("searchNull", result);
                System.out.println("search null add into cache");
                return result;
            }
        }
        result = searchResult(value);
        for (int i = (page - 1) * pageSize; i < page * pageSize; i++) {
            if (i >= result.size()) break;
            ActivitySortpage activitySortpage = result.get(i);
            activitySortpages.add(activitySortpage);
        }
        if (activitySortpages.size() == 0) throw new IndexOutOfBoundsException("页码越界");
        return activitySortpages;
    }

    @Override
    /**
     * @param value
     * @return pagenum
     * @author ziliuziliu
     * @date 2020/8/21
     * @throws NullPointerException the param of activitySortpage is null or the mongo data of actitem is null
     * @throws IOException if open file fails
     * @throws ParseException if parse of value fails
     * @throws JpaObjectRetrievalFailureException if id is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public Integer searchPageNum(String value) throws IOException, ParseException {
        List<Object> resultObject;
        List<ActivitySortpage> result;
        if (value == null || value.equals("") || value.equals("null")) {
            resultObject = (List<Object>) redisUtil.lGetIndex("searchNull", 0);
            if (resultObject == null) {
                // should not be used
                result = new ArrayList<>();
                for (int i = 123; i <= 456; i += 9) {
                    ActivitySortpage activitySortpage = findActivityAndActitem(i);
                    if (activitySortpage != null)
                        result.add(activitySortpage);
                }
                redisUtil.lSet("searchNull", result);
                System.out.println("search null add into cache");
            } else result = object2ActivitySortpage(resultObject);
            return result.size() / pageSize;
        }
        result = searchResult(value);
        return result.size() / pageSize;
    }

//    @Override

    /**
     * use id to find activity and actitems to make a activitySortpage
     *
     * @param id the activityId
     * @return ActivitySortpage that have the id
     * @throws NullPointerException               when id is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException when id is invalid or no activity is found
     * @throws EmptyResultDataAccessException     when activity is not found
     * @author feaaaaaa
     * @date 2020.8.15
     */
    public ActivitySortpage findActivityAndActitem(Integer id) {

        Objects.requireNonNull(id, "null id --ActivityServiceImpl findActivityAndActitem");
        String cacheName = "ActivitySortpage" + id.toString();
        ActivitySortpage cacheResult = (ActivitySortpage) redisUtil.lGetIndex(cacheName, 0);
        if (cacheResult != null) {
            System.out.println(cacheName + " get from cache");
            return cacheResult;
        }
        Activity activity = activityDao.findOneById(id);
        List<Actitem> actitems = actitemDao.findAllByActivityId(id);
        cacheResult = new ActivitySortpage(
                activity.getActivityId(),
                activity.getTitle(),
                activity.getActor(),
                activity.getTimescale(),
                activity.getVenue(),
                activity.getActivityIcon(),
                actitems
        );
        redisUtil.lSet(cacheName, cacheResult);
        System.out.println(cacheName + " add into cache");
        return cacheResult;
    }

    @Override
    /**
     * @param activity
     * @return save success or fail
     * @author feaaaaaa
     * @date 2020.8.15
     * @throws NullPointerException when param is null
     * @throws ArrayIndexOutOfBoundsException,NumberFormatException if string activity is not the correct format
     */
    public Boolean add(String activity) {
        Objects.requireNonNull(activity, "null activity --ActivityServiceImpl add");
        activity = activity.substring(1, activity.length() - 1);
        String[] arr = activity.split(",");
//        System.out.println(Arrays.toString(arr));
        int webcnt = Integer.parseInt(arr[0]);
        int daycnt = Integer.parseInt(arr[1]);
        int classcnt = Integer.parseInt(arr[2]);
        String city = arr[8].substring(1, arr[8].length() - 1);
        String category = arr[9].substring(1, arr[9].length() - 1);
        String subcategory = arr[10].substring(1, arr[10].length() - 1);
//        System.out.println(city+category+subcategory);

        Activity savedActivity = activityDao.add(arr[3].substring(1, arr[3].length() - 1), arr[4].substring(1, arr[4].length() - 1), arr[5].substring(1, arr[5].length() - 1),
                arr[6].substring(1, arr[6].length() - 1), arr[7].substring(1, arr[7].length() - 1));
        int activityId = savedActivity.getActivityId();
        int number = daycnt * classcnt + daycnt;
        activityDao.addActivityNeo4j(String.valueOf(activityId), category, subcategory, city);

        for (int i = 0; i < webcnt; ++i) {
            //website
            int basic = 11 + i * (number + 1);
            Actitem savedActitem = actitemDao.add(activityId, arr[basic].substring(1, arr[basic].length() - 1));
            //date
            basic += 1;

            int actitemId = savedActitem.getActitemId();
            List<JSONObject> CLASS;
            List<JSONObject> prices = new ArrayList<>();
            JSONObject clas;

            for (int k = 0; k < daycnt; ++k) {
                //date
                if (k != 0) basic += classcnt + 1;
                CLASS = new ArrayList<>();
                String date = arr[basic].substring(1, arr[basic].length() - 1);

                for (int j = 0; j < classcnt; ++j) {
                    String classPrice = "{price:" + arr[basic + 1 + j] + ",num:100}";
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
//            System.out.println("!!!!");
//            System.out.println(prices.toString());
            actitemDao.insertActitemInMongo(actitemId, prices);
        }
        return true;
    }

    @Override
    @Transactional
    /**
     * @param id the activityId of activity
     * @return delete success or fail
     * @author feaaaaaa
     * @date 2020.8.15
     * @throws NullPointerException when id is null or mongo data is null
     * @throws JpaObjectRetrievalFailureException when id is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public Boolean delete(Integer activityId) {
        Objects.requireNonNull(activityId, "null id --ActivityServiceImpl delete");
        List<Actitem> actitems = actitemDao.findAllByActivityId(activityId);
        for (Actitem a : actitems)
            actitemDao.deleteActitem(a.getActitemId());
        activityDao.delete(activityId);
        return true;
    }

    /**
     * selectSearch result
     *
     * @param type
     * @param name
     * @param city
     * @return true if initialize finished successfully
     * @throws NullPointerException               when param is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException if id found by neo4j is invalid
     * @throws EmptyResultDataAccessException     when activity is not found
     * @throws InvalidDataAccessApiUsageException when type is invalid
     * @author ziliuziliu
     * @date 2020/8/21
     */
    public List<ActivitySortpage> selectSearchResult(String type, String name, String city) throws IOException, ParseException {
        List<ActivitySortpage> result;
        List<Object> resultObject;
        List<Integer> activities;
        String cacheName = name + city;
        resultObject = (List<Object>) redisUtil.lGetIndex(cacheName, 0);
        if (resultObject == null) {
            result = new ArrayList<>();
            activities = activityDao.findActivityByCategoryAndCity(type, name, city);
            for (Integer activity : activities)
                result.add(findActivityAndActitem(activity));
            redisUtil.lSet(cacheName, result);
        } else result = object2ActivitySortpage(resultObject);
        return result;
    }

    @Override
    /**
     * @param type
     * @param name
     * @param city
     * @param page
     * @return true if initialize finished successfully
     * @author ziliuziliu, feaaaaaa
     * @date 2020.08.21
     * @throws NullPointerException when param is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException if id found by neo4j is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     * @throws InvalidDataAccessApiUsageException when type is invalid
     */
    public List<ActivitySortpage> selectSearch(String type, String name, String city, Integer page) throws IOException,
            ParseException, IndexOutOfBoundsException {
        Objects.requireNonNull(type, "null type --ActivityServiceImpl selectSearch");
        Objects.requireNonNull(name, "null name --ActivityServiceImpl selectSearch");
        Objects.requireNonNull(city, "null city --ActivityServiceImpl selectSearch");

        List<ActivitySortpage> result;//every item
        List<ActivitySortpage> activitySortpages = new ArrayList<>(); //item this page should display

        if (!type.equals("category") && !type.equals("subcategory"))
            throw new InvalidDataAccessApiUsageException("invalid category");
        if (name.equals("全部") && city.equals("全国")) return search("", page);
        result = selectSearchResult(type, name, city);
        for (int i = (page - 1) * 10; i < page * 10; i++) {
            if (i >= result.size()) break;
            activitySortpages.add(result.get(i));
        }
        if (activitySortpages.size() == 0) throw new IndexOutOfBoundsException("页码越界");
        return activitySortpages;
    }

    @Override
    /**
     * @param type
     * @param name
     * @param city
     * @return pagenum
     * @author ziliuziliu
     * @date 2020/8/21
     * @throws NullPointerException when param is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException if id found by neo4j is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     * @throws InvalidDataAccessApiUsageException when type is invalid
     */
    public Integer selectSearchPageNum(String type, String name, String city) throws IOException, ParseException {
        Objects.requireNonNull(type, "null type --ActivityServiceImpl selectSearch");
        Objects.requireNonNull(name, "null name --ActivityServiceImpl selectSearch");
        Objects.requireNonNull(city, "null city --ActivityServiceImpl selectSearch");
        List<ActivitySortpage> result;//every item
        if (!type.equals("category") && !type.equals("subcategory"))
            throw new InvalidDataAccessApiUsageException("invalid category");
        if (name.equals("全部") && city.equals("全国")) return searchPageNum("");
        result = selectSearchResult(type, name, city);
        return result.size() / pageSize;
    }

    @Override
    /**
     * @return total list of activitySortpage for homepage
     * @author feaaaaaa
     * @date 2020.08.14
     * @throws NullPointerException when the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException when id found by neo4j is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public List<ActivitySortpage> findActivityByCategoryHome() {
        List<ActivitySortpage> activitySortpages = new ArrayList<>();
        activitySortpages.addAll(findActivityByOneCategoryHome("儿童亲子"));
        activitySortpages.addAll(findActivityByOneCategoryHome("话剧歌剧"));
        activitySortpages.addAll(findActivityByOneCategoryHome("展览休闲"));
        activitySortpages.addAll(findActivityByOneCategoryHome("曲苑杂坛"));
        activitySortpages.addAll(findActivityByOneCategoryHome("体育"));
        activitySortpages.addAll(findActivityByOneCategoryHome("舞蹈芭蕾"));
        activitySortpages.addAll(findActivityByOneCategoryHome("音乐会"));
        activitySortpages.addAll(findActivityByOneCategoryHome("演唱会"));
        return activitySortpages;
    }

    /**
     * use findActivityByCategoryAndCity("category", name, "全国")  to get one content of homepage from cache
     *
     * @param name name of category
     * @return a list of activitySortpage for homepage
     * @throws NullPointerException               when name is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws JpaObjectRetrievalFailureException when id found by neo4j is invalid
     * @throws EmptyResultDataAccessException     when activity is not found
     * @author feaaaaaa
     * @date 2020.08.18
     */
    private List<ActivitySortpage> findActivityByOneCategoryHome(String name) {
        Objects.requireNonNull(name, "null name --ActivityServiceImpl findActivityByOneCategoryHome");

        List<ActivitySortpage> activitySortpages = new ArrayList<ActivitySortpage>();
        int i = 0;
        List<Integer> activities;
        List<ActivitySortpage> cacheResult;
        List<Object> cacheResultObject;

        //get from cache
        cacheResultObject = (List<Object>) redisUtil.lGetIndex(name, 0);
        if (cacheResultObject != null && cacheResultObject.size() != 0) {
            System.out.println(name + " get from cache");
            cacheResult = object2ActivitySortpage(cacheResultObject);
            activitySortpages.addAll(cacheResult);
        }
        //1st time, add into cache, which will be done in initialization
        else {
            System.out.println(name + " put into cache");
            activities = activityDao.findActivityByCategoryAndCity("category", name, "全国");
            for (Integer a : activities) {
                activitySortpages.add(findActivityAndActitem(a));
                if (++i >= 10) break;
            }
            redisUtil.lSet(name, activitySortpages);
        }
        return activitySortpages;
    }

    @Override
    /**
     * @param userId
     * @param activityId
     * @return the list of recommend activity
     * @author ziliuziliu, feaaaaaa
     * @date 2020.8.18
     * @throws NullPointerException when param is null or the param of activitySortpage is null or the mongo data of actitem is null
     * @throws InvalidDataAccessApiUsageException when param is invalid
     * @throws JpaObjectRetrievalFailureException when id found by neo4j and used to find activitySortpage is invalid
     * @throws EmptyResultDataAccessException when activity is not found
     */
    public List<ActivitySortpage> recommendOnContent(Integer userId, Integer activityId) {
        //check param
        Objects.requireNonNull(userId, "null userId --ActivityServiceImpl recommendOnContent");
        Objects.requireNonNull(activityId, "null activityId --ActivityServiceImpl recommendOnContent");
        if (userId <= 0)
            throw new InvalidDataAccessApiUsageException("invalid userId --ActivityServiceImpl recommendOnContent");
        if (activityId <= 0)
            throw new InvalidDataAccessApiUsageException("invalid activityId --ActivityServiceImpl recommendOnContent");
        //find activity
        List<Integer> activities = activityDao.recommendOnContent(userId, activityId);
        List<ActivitySortpage> activitySortpages = new ArrayList<ActivitySortpage>();
        for (Integer activity : activities) {
            ActivitySortpage activitySortpage = findActivityAndActitem(activity);
            activitySortpages.add(activitySortpage);
        }
        return activitySortpages;
    }

    @Override
    /**
     * @return true if initialize finished successfully
     * @author feaaaaaa
     * @date 2020.08.14
     * @throws JpaObjectRetrievalFailureException if cnt exceeds max id of activity
     */
    public Boolean initActivity() {

        List<ActivitySortpage> activitySortpages = new ArrayList<>();
        //add all the activitySortpage into cache
        initActivityById(1000, activitySortpages);
        initActivityById(2000, activitySortpages);
        initActivityById(3000, activitySortpages);
        initActivityById(4000, activitySortpages);
        initActivityById(5000, activitySortpages);
        initActivityById(6000, activitySortpages);
        initActivityById(7000, activitySortpages);
        initActivityById(8000, activitySortpages);
        initActivityById(9000, activitySortpages);
        initActivityById(10000, activitySortpages);
        initActivityById(11000, activitySortpages);
        initActivityById(12000, activitySortpages);
        //add to searchNull
        redisUtil.lSet("searchNull", activitySortpages);
        //add home page to cache
        findActivityByCategoryHome();
        return true;
    }

    /**
     * add 1000 activitySortpage into cache
     *
     * @param cnt the max id that add into cache
     * @throws JpaObjectRetrievalFailureException if cnt exceeds max id of activity
     * @author feaaaaaa
     * @date 2020.08.14
     */
    public void initActivityById(int cnt, List<ActivitySortpage> activitySortpages) {
        int basic = Math.max(1, cnt - 1000);
        for (int i = basic; i < cnt; ++i) {
            activitySortpages.add(findActivityAndActitem(i));
        }
    }

    /**
     * @return true if clear clear successfully
     * @author feaaaaaa
     * @date 2020.08.14
     */
    @Override
    public Boolean clear() {
//        if(cacheName==null||cacheName.equals("")||cacheName.equals("null"))
//            idSetCache.evictCache();
//        idSetCache.evictCache(cacheName);
        return true;
    }


    //    public void initActivityByCity(String city) {
//        String cacheName = "idSet" + city;
//        city = "%" + city + "%";
//        List<Integer> result = activityDao.findAllIdByTitleOrVenueOrActor(city, city, city);
//        idSetCache.addOrUpdateCache(cacheName, result);
//        System.out.println(cacheName + " add into cache");
//    }
}
