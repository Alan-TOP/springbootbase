package com.alan.springbootbase.controller;

import com.alan.springbootbase.entity.ResultVO;
import com.alan.springbootbase.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo class
 * 用于基本的RestController类型的返回
 * @author Alan
 * @date 2019/10/31
 *
 * 获取配合文件值
 * 获取post请求数据
 * Socket
 * XML转json数据
 *
 */
@RestController
@RequestMapping("rest")
@Slf4j
public class HelloRestController {

    @Value("${com.alan.title}")
    private String title;

    /**
     * 测试方法
     * @return
     */
    @RequestMapping("hello")
    public ResultVO hello(){
        log.info("rest hello");
        System.out.println("rest hello");
        return ResultUtil.success("hello "+title);
    }



}
