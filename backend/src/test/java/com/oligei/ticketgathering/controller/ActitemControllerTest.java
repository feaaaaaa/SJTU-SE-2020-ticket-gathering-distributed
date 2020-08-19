package com.oligei.ticketgathering.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.ticketgathering.dto.DetailInfo;
import com.oligei.ticketgathering.service.ActitemService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ActitemControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @MockBean
    ActitemService actitemService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void getDetail() throws Exception{
        DetailInfo detailInfo = new DetailInfo(1,"title","actor","timescale","venue","activityicon","descrption","website",null);
        when(actitemService.findActivityAndActitemDetail(1,1)).thenReturn(detailInfo);
        MvcResult result = mockMvc.perform(
                get("/Actitem/detail")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("actitemid","1")
                        .param("userid","1")

        )
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = om.readValue(resultContent, new TypeReference<JSONObject>() {});
        assertNotNull(jsonObject.get("data"));
    }
}
