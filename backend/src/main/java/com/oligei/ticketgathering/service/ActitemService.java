package com.oligei.ticketgathering.service;

import com.alibaba.fastjson.JSONObject;

public interface ActitemService {
    JSONObject findActivityAndActitemDetail(Integer id, Integer userId);
}
