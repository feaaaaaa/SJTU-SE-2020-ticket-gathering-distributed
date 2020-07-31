package com.oligei.ticket_gathering.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public interface ActitemService {
    JSONObject findActivityAndActitemDetail(Integer id, Integer userId);
}
