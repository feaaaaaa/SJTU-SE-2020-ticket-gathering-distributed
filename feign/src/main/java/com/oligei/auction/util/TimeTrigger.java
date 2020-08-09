package com.oligei.auction.util;

import com.oligei.auction.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TimeTrigger {

    @Autowired
    AuctionService auctionService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void flushAuctions(){
        auctionService.flushActions();
    }
}
