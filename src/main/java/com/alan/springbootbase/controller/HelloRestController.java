package com.alan.springbootbase.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rest")
public class HelloRestController {

    @RequestMapping("hello")
    public String hello(){

        return "hello Alan";
    }
}
