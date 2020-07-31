package com.oligei.ticket_gathering.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.ticket_gathering.dto.ActivitySortpage;
import com.oligei.ticket_gathering.entity.mysql.User;
import com.oligei.ticket_gathering.service.ActivityService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ActivityControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private  ActivityService activityService;

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void search() throws Exception {

        System.out.println("Reasonable Value");
        MvcResult result = mockMvc.perform(get("/Activity/search?search=周杰伦演唱会").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        int resultLength=result.getResponse().getContentLength();
        System.out.println(resultLength);
        assertNotEquals("[]", resultContent);
        assertTrue(resultLength > -1);

        System.out.println("Uneasonable Value");
        MvcResult result2 = mockMvc.perform(get("/Activity/search?search=123456").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent2 = result2.getResponse().getContentAsString();
        System.out.println(resultContent2);
        int resultLength2 = result2.getResponse().getContentLength();
        System.out.println(resultLength2);
        assertEquals("[]", resultContent2);
        assertTrue(resultLength2 > -1);

        System.out.println("Reasonable and Uneasonable Value");
        MvcResult result3 = mockMvc.perform(get("/Activity/search?search=周杰伦123456").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent3 = result3.getResponse().getContentAsString();
        System.out.println(resultContent3);
        int resultLength3 = result3.getResponse().getContentLength();
        System.out.println(resultLength3);
        assertNotEquals("[]", resultContent3);
        assertTrue(resultLength3 > -1);
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

    @Test
    @Rollback
    void selectSearch() throws Exception{
        MvcResult result; String resultContent;

        result = mockMvc.perform(get("/Activity/FindActivityByCategory?type=category&name=话剧歌剧&city=成都")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);

        result = mockMvc.perform(get("/Activity/FindActivityByCategory?type=subcategory&name=音乐剧&city=成都")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);

        result = mockMvc.perform(get("/Activity/FindActivityByCategory?type=123&name=全部&city=成都")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);

        result = mockMvc.perform(get("/Activity/FindActivityByCategory?type=category&name=话剧歌剧&city=全国")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);

        result = mockMvc.perform(get("/Activity/FindActivityByCategory?type=subcategory&name=音乐剧&city=全国")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        assertNotNull(resultContent);
    }
}
