package com.oligei.auction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oligei.auction.service.AuctionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@Transactional
class AuctionControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper om = new ObjectMapper();

    @Autowired
    AuctionService auctionService;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Rollback
    void addAuction() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        String auction_string = "{\"actitemid\":8,\"ddl\":\"2020-07-28 08:57:00\",\"showtime\":\"2020-02-22\",\"initprice\":680\"\",\"orderprice\":1000,\"amount\":5}";
//        Auction auction = mapper.readValue(auction_string,Auction.class);
//        MvcResult result = mockMvc.perform(post("/Auction/add").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(mapper.writeValueAsString(auction)))
//                .andExpect(status().isOk())
//                .andReturn();
//        String resultContent = result.getResponse().getContentAsString();
//        assertEquals(true,resultContent);
    }

    @Test
    void getAuctions() throws Exception{
    }

    @Test
    void joinAuction() throws Exception{
    }
}
