package com.oligei.ticket_gathering.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @Value("${server.port}")
    String port;
    @RequestMapping("/")
    public String index() {
        return "Hello there from"+ port;
    }

}
