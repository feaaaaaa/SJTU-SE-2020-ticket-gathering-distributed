package com.oligei.ticketgathering.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.ticketgathering.entity.info.OrderInfo;
import com.oligei.ticketgathering.service.OrderService;
import com.oligei.ticketgathering.util.msgutils.Msg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class OrderControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getUserOrder() throws Exception {
        int existedUserId = 1;
        int noneExistedUserId = 10;
        int illegalUserId = -1;

        List<OrderInfo> existedUserOrder = new ArrayList<>();
        OrderInfo tmp1 = new OrderInfo(1, existedUserId, 3, 100, 3, "2020-01-01",
                "2020-01-01 16:00:00", "title1", "venue1", "image1");
        OrderInfo tmp2 = new OrderInfo(3, existedUserId, 9, 200, 2, "2020-01-01",
                "2020-01-01 16:00:00", "title2", "venue2", "image2");
        OrderInfo tmp3 = new OrderInfo(5, existedUserId, 6, 300, 1, "2020-01-01",
                "2020-01-01 16:00:00", "title3", "venue3", "image3");
        existedUserOrder.add(tmp1);
        existedUserOrder.add(tmp2);
        existedUserOrder.add(tmp3);

        when(orderService.getUserOrder(existedUserId)).thenReturn(existedUserOrder);
        when(orderService.getUserOrder(noneExistedUserId)).thenThrow(new NullPointerException("Order not found"));
        when(orderService.getUserOrder(illegalUserId)).thenThrow(new InvalidDataAccessApiUsageException("using illegal userId"));

        System.out.println("using existed userId to test");
        MvcResult result = mockMvc.perform(get("/Order/GetOrderInfoByUser?userId=1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        JSONObject test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        assertEquals(3, test.getJSONArray("data").size());
        assertEquals(1, Integer.parseInt(test.getJSONArray("data").getJSONObject(0).getString("orderId")));

        System.out.println("using none existed userId to test");
        result = mockMvc.perform(get("/Order/GetOrderInfoByUser?userId=10").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        System.out.println(test);
        assertNull(test.getJSONArray("data"));
        assertEquals(201, test.get("status"));

        System.out.println("using none illegal userId to test");
        result = mockMvc.perform(get("/Order/GetOrderInfoByUser?userId=-1").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        System.out.println(test);
        assertNull(test.getJSONArray("data"));
        assertEquals(201, test.get("status"));
    }


    @Test
    void addOrder() throws Exception {
        when(orderService.addOrder(1, 1, 100, 0, 2,
                "2020-01-01", "2020-01-01 00:00:00")).thenReturn(true);
        when(orderService.addOrder(1, 2, 100, 0, 2,
                "2020-01-01", "2020-01-01 00:00:00")).thenThrow(new ArrayIndexOutOfBoundsException("no actitem found"));
        when(orderService.addOrder(1, 3, 100, 0, 2,
                "2020-01-01", "2020-01-01 00:00:00")).thenThrow(new ArithmeticException("the repository is zero"));
        when(orderService.addOrder(1, 4, 100, 0, 2,
                "2020-01-01", "2020-01-01 00:00:00")).thenThrow(new ArithmeticException("the repository is zero"));

        MvcResult result = mockMvc.perform(get("/Order/addOrder?userId=1&actitemId=1&initPrice=100&orderPrice=0&amount=2&showtime=2020-01-01&orderTime=2020-01-01 00:00:00").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        JSONObject test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        assertEquals(true,test.get("data"));

        result = mockMvc.perform(get("/Order/addOrder?userId=1&actitemId=2&initPrice=100&orderPrice=0&amount=2&showtime=2020-01-01&orderTime=2020-01-01 00:00:00").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        assertEquals(false,test.get("data"));

        result = mockMvc.perform(get("/Order/addOrder?userId=1&actitemId=3&initPrice=100&orderPrice=0&amount=2&showtime=2020-01-01&orderTime=2020-01-01 00:00:00").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        assertEquals(false,test.get("data"));

        result = mockMvc.perform(get("/Order/addOrder?userId=1&actitemId=4&initPrice=100&orderPrice=0&amount=2&showtime=2020-01-01&orderTime=2020-01-01 00:00:00").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        resultContent = result.getResponse().getContentAsString();
        test = om.readValue(resultContent, new TypeReference<JSONObject>() {
        });
        assertEquals(false,test.get("data"));
    }

}
