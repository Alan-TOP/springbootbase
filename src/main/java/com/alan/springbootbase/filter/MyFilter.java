package com.alan.springbootbase.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Alan
 * @create: 2019-08-06 14:31
 **/
@WebFilter(filterName = "myFilter",urlPatterns = "/rest/*")
@Slf4j
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        log.info("Filter:"+req.getRequestURL());
        if ("admin".equals("admin")) {
            chain.doFilter(request,response);
        } else {
            resp.sendRedirect("/index.html");
            return;
        }
    }

    @Override
    public void destroy() {
    }
}
