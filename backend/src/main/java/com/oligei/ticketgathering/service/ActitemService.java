package com.oligei.ticketgathering.service;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.dto.DetailInfo;

public interface ActitemService {

    /**
     * get detail information about the activity in the website in service layer
     */
    DetailInfo findActivityAndActitemDetail(Integer id, Integer userId);
}
