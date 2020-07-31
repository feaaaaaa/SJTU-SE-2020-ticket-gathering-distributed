package com.oligei.ticket_gathering.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.ticket_gathering.entity.info.OrderInfo;
import com.oligei.ticket_gathering.service.OrderService;
import org.aspectj.lang.annotation.Before;
import org.json.JSONString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void getOrderInfoByUser() throws Exception {
        System.out.println("using existed userId to test");
        MvcResult result = mockMvc.perform(get("/Order/GetOrderInfoByUser?userId=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
//        System.out.println(resultContent);
        List<OrderInfo> test = om.readValue(resultContent, new TypeReference<List<OrderInfo>>() {});
//        System.out.println(test.get(0).getOrderId());
        assertEquals(1,test.get(0).getOrderId());
        assertEquals(3,test.get(1).getOrderId());
    }

    @Test
    @Rollback
    void addOrder() throws Exception {
        MvcResult result = mockMvc.perform(get("/Order/addOrder?userId=2&actitemId=10&price=180&amount=2&showtime=2020-12-30&orderTime=2020-02-15 15:00:00").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        assertEquals("true",resultContent);
    }

}