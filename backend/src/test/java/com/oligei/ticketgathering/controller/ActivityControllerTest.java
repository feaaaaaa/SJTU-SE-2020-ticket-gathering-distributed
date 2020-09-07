package com.oligei.ticketgathering.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.internal.cglib.proxy.$InvocationHandler;
import com.oligei.ticketgathering.service.ActivityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ActivityControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private ActivityService activityService;

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
    }

    JSONObject searchMock(String search, Integer page) throws Exception {
        MvcResult result = mockMvc.perform(get("/Activity/search?search="+search+"&page="+page)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                        .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void search(){
        JSONObject msg = null;

        System.out.println("Reasonable Value");
        try {
            msg = searchMock("周杰伦",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Combined Value");
        try {
            msg = searchMock("周杰伦天津",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Unreasonable Value");
        try {
            msg = searchMock("螺狮粉",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject searchPageNumMock(String search) throws Exception{
        MvcResult result = mockMvc.perform(get("/Activity/searchPageNum?search="+search)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void searchPageNum() {
        JSONObject msg = null;

        System.out.println("Reasonable Value");
        try {
            msg = searchPageNumMock("周杰伦");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Combined Value");
        try {
            msg = searchPageNumMock("周杰伦天津");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Unreasonable Value");
        try {
            msg = searchPageNumMock("螺狮粉");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    void recommendOnContent() throws Exception{
        MvcResult result = mockMvc.perform(get("/Activity/RecommendOnContent?userId=1&activityId=10")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);
    }

    JSONObject selectSearchMock(String type, String name, String city, Integer page) throws Exception{
        MvcResult result = mockMvc.perform(get("/Activity/FindActivityByCategory?type="+type
                                                +"&name="+name+"&city="+city+"&page="+page)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                                    .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void selectSearch() throws Exception{
        JSONObject msg = null;

        try {
            msg = selectSearchMock("category","话剧歌剧","成都",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            msg = selectSearchMock("category","话剧歌剧","全国",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            msg = selectSearchMock("subcategory","音乐剧","全国",1);
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject selectSearchPageNumMock(String type, String name, String city) throws Exception{
        MvcResult result = mockMvc.perform(get("/Activity/FindActivityByCategoryPageNum?type="+type
                +"&name="+name+"&city="+city)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void selectSearchPageNum() {
        JSONObject msg = null;

        try {
            msg = selectSearchPageNumMock("category","话剧歌剧","成都");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            msg = selectSearchPageNumMock("category","话剧歌剧","全国");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            msg = selectSearchPageNumMock("subcategory","音乐剧","全国");
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    void findActivityByCategoryHome() {
        try {
            MvcResult result = mockMvc.perform(get("/Activity/FindActivityByCategoryHome")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            JSONObject msg = om.readValue(resultContent, new TypeReference<JSONObject>() {});
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    void add() {
        String activity = "[1,lll,null,4,5,6,7,null,null,test]";
        when(activityService.add(activity)).thenReturn(true);
        try {
            MvcResult result = mockMvc.perform(get("/Activity/add?activity="+activity)
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk()).andReturn();
            String resultContent = result.getResponse().getContentAsString();
            JSONObject msg = om.readValue(resultContent, new TypeReference<JSONObject>() {});
            assertEquals(200,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
