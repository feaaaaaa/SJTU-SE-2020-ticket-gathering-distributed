package com.oligei.auction.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.auction.dto.AuctionListItem;
import com.oligei.auction.entity.Auction;
import com.oligei.auction.service.AuctionService;
import com.oligei.auction.util.msgutils.Msg;
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

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AuctionControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @MockBean
    private AuctionService auctionService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void addAuction() throws Exception {
        when(auctionService.save(8,"2020-07-28 08:57:00","2020-02-22",680,1000,5)).thenReturn(true);
        MvcResult result = mockMvc.perform(
                post("/Auction/add")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("actitemid", "8")
                        .param("ddl","2020-07-28 08:57:00")
                        .param("showtime","2020-02-22")
                        .param("initprice","680")
                        .param("orderprice","1000")
                        .param("amount","5")
        )
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println("result content"+resultContent);
        JSONObject jsonObject = om.readValue(resultContent, new TypeReference<JSONObject>() {});
        assertTrue(jsonObject.getBooleanValue("data"));
//        assertEquals(true,resultContent);
    }

    @Test
    @Rollback
    void getAuctions() throws Exception{
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Showtime = null,Ddl = null;
        try{
            Showtime=format1.parse("2020-1-1");
            Ddl=format2.parse("2020-10-1 12:12:12");
        } catch (ParseException e){
            e.printStackTrace();
        }

        List<AuctionListItem> auctionListItems = new ArrayList<>();
        AuctionListItem auctionListItem1 = new AuctionListItem(1,Ddl,500,Showtime,5,"title1","actor1","venue1",0,"icon1");
        AuctionListItem auctionListItem2 = new AuctionListItem(2,Ddl,500,Showtime,5,"title2","actor2","venue2",0,"icon2");

        auctionListItems.add(auctionListItem1);
        auctionListItems.add(auctionListItem2);

        when(auctionService.getAvailableAuctions()).thenReturn(auctionListItems);
        MvcResult result = mockMvc.perform(get("/Auction/get").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String resultContent = result.getResponse().getContentAsString();
        System.out.println(resultContent);
        JSONObject jsonObject = om.readValue(resultContent, new TypeReference<JSONObject>() {});
        assertEquals(auctionListItems.size(),jsonObject.getJSONArray("data").size());
    }

    @Test
    @Rollback
    void joinAuction() throws Exception{
        when(auctionService.joinAuction(1,1,1000)).thenReturn(1);
        MvcResult result = mockMvc.perform(
                post("/Auction/join")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("auctionid","1")
                    .param("userid","1")
                    .param("price","1000")
        )
                .andExpect(status().isOk())
                .andReturn();
        String resultContent = result.getResponse().getContentAsString();
        JSONObject jsonObject = om.readValue(resultContent, new TypeReference<JSONObject>() {});
        assertEquals(1,jsonObject.get("data"));
    }
}
