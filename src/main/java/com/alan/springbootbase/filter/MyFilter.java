package com.alan.springbootbase.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

/**
 * @description:
 * Filter过滤器的设置与使用
 *
 * 实现了计算请求耗时
 *
 * @author: Alan
 * @create: 2019-08-06 14:31
 **/
//@WebFilter(filterName = "myFilter",urlPatterns = "/*")
//@Order(5)
@Slf4j
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        //请求时的系统时间
        LocalTime timeStart = LocalTime.now();
        log.info("Filter:"+req.getRequestURL()+" ----- time start : "+timeStart);

        //用于业务判断，此处为测试代码
        if ("admin".equals("admin")) {
            chain.doFilter(request,response);
        } else {
            resp.sendRedirect("/index.html");
            return;
        }

        //响应时的系统时间
        LocalTime timeEnd = LocalTime.now();
        log.info("Filter:"+req.getRequestURL()+" ----- time end : "+timeEnd);

        //计算请求响应耗时
        Duration total = Duration.between(timeStart, timeEnd);
        System.out.println("请求耗时：" + total.toMillis());
    }

    @Override
    public void destroy() {
    }
}
