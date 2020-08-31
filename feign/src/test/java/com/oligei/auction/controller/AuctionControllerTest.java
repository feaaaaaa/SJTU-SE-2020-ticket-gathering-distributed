package com.oligei.auction.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.auction.util.TimeFormatter;
import com.oligei.auction.util.msgutils.Msg;
import org.aspectj.weaver.IUnwovenClassFile;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AuctionControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();
    private TimeFormatter timeFormatter;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private Integer actitemid1 = 36;
    private String ddl1 = "2021-08-26 06:00:00";
    private String ddl2 = "timestamp wrong format";
    private String showtime1 = "2020-12-30";
    private String showtime2 = "date wrong format";
    private Integer initprice1 = 680;
    private Integer orderprice1 = 780;
    private Integer amount1 = 2;
    private Integer userid1 = 1;
    private Integer auctionid1 = 1;
    private Integer auctionid2 = -1;
    private Integer price1 = 100;

    JSONObject addAuctionMock(Integer actitemid, String ddl, String showtime, Integer initprice, Integer orderprice, Integer amount) throws Exception{
        MvcResult result = mockMvc.perform(get("/Auction/add?actitemid="+actitemid
                +"&ddl="+ddl
                +"&showtime="+showtime
                +"&initprice="+initprice
                +"&orderprice="+orderprice
                +"&amount="+amount)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void addAuction() {

        System.out.println("Wrong Timestamp Format");
        try {
            JSONObject msg = addAuctionMock(actitemid1,ddl2,showtime1,initprice1,orderprice1,amount1);
            assertEquals(202,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Wrong Date Format");
        try {
            JSONObject msg = addAuctionMock(actitemid1,ddl1,showtime2,initprice1,orderprice1,amount1);
            assertEquals(202,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject canEnterMock(Integer userid, Integer auctionid) throws Exception{
        MvcResult result = mockMvc.perform(get("/Auction/canEnter?userid="+userid+"&auctionid="+auctionid)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void canEnter() {
        System.out.println("Auction ID Not Available");
        try {
            JSONObject msg = canEnterMock(userid1,auctionid2);
            assertEquals(202,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject depositMock(Integer userid, Integer auctionid) throws Exception{
        MvcResult result = mockMvc.perform(get("/Auction/deposit?userid="+userid+"&auctionid="+auctionid)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void deposit() {
        System.out.println("Auction ID Not Available");
        try {
            JSONObject msg = depositMock(userid1,auctionid2);
            assertEquals(202,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JSONObject getPriceMock(Integer userid, Integer auctionid) throws Exception{
        MvcResult result = mockMvc.perform(get("/Auction/getPrice?"+"auctionid="+auctionid)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void getPrice() {
        System.out.println("Auction ID Not Available");
        try {
            JSONObject msg = getPriceMock(userid1,auctionid2);
            assertEquals(201,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback
    void getAuctions() {
    }

    JSONObject joinAuctionMock(Integer auctionid, Integer userid, Integer price) throws Exception{
        MvcResult result = mockMvc.perform(get("/Auction/join?"+"auctionid="+auctionid
                +"&userid="+userid
                +"&price="+price)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        return om.readValue(resultContent, new TypeReference<JSONObject>() {});
    }

    @Test
    @Rollback
    void joinAuction() {
        System.out.println("Auction ID Not Available");
        try {
            JSONObject msg = joinAuctionMock(auctionid2,userid1,price1);
            assertEquals(201,msg.get("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
