package com.oligei.ticketgathering.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.dao.ActitemDao;
import com.oligei.ticketgathering.dao.ActivityDao;
import com.oligei.ticketgathering.dao.VisitedRelationshipDao;
import com.oligei.ticketgathering.dto.DetailInfo;
import com.oligei.ticketgathering.entity.mysql.Actitem;
import com.oligei.ticketgathering.entity.mysql.Activity;
import com.oligei.ticketgathering.service.ActitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActitemServiceImpl implements ActitemService {

    @Autowired
    private ActitemDao actitemDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private VisitedRelationshipDao visitedRelationshipDao;

    @Override
    /**
     * @param id
     * @param userId
     * @return com.oligei.ticketgathering.dto.DetailInfo
     * @author Cui Shaojie
     * @date 2020/8/18
     * @Throws NullPointerException Actitem Not Found
     * @Throws NullPointerException Actitem Not Found
     */
    public DetailInfo findActivityAndActitemDetail(Integer id, Integer userId) {
        Actitem actitem = actitemDao.findOneById(id);
        if (actitem == null)
            throw new NullPointerException("Actitem Not Found");
        Activity activity = activityDao.findOneById(actitem.getActivityId());
        if (activity == null)
            throw new NullPointerException("Activity Not Found");

        DetailInfo detailInfo = new DetailInfo(actitem.getActivityId(), activity.getTitle(), activity.getActor(), activity.getTimescale(),
                activity.getVenue(), activity.getActivityIcon(), activity.getDescription(), actitem.getWebsite(), actitem.getPrice());
        visitedRelationshipDao.saveVisitedHistory(userId, actitem.getActivityId());
        return detailInfo;
    }
}
