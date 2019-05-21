package com.alan.springbootbase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class HelloRestController {

    Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    @RequestMapping("hello")
    public String hello(){
        logger.debug("rest hello");
        return "hello Alan";
    }
}
