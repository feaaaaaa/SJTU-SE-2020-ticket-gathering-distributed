package com.oligei.ticketgathering.service;

import com.alibaba.fastjson.JSONObject;
import com.oligei.ticketgathering.dto.DetailInfo;

public interface ActitemService {
    DetailInfo findActivityAndActitemDetail(Integer id, Integer userId);
}
