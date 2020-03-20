package com.alan.springbootbase.filter;

import com.alan.springbootbase.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;


/**
 * @author Alan
 * @Description Filter过滤器
 * 白名单过滤
 *  @Component
 *  无需添加此注解，在启动类添加@ServletComponentScan注解后，会自动将带有@WebFilter的注解进行注入！
 *  @Order()指定过滤器的执行顺序,值越大越靠后执行
 *
 * @date 2020年03月19日 21:25
 */
//@WebFilter(filterName = "IPFilter",urlPatterns = "/*")
//@Order(10)
@Slf4j
public class IPFilter implements Filter {

    private String title;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取FilterConfig配置初始化参数
        title = filterConfig.getInitParameter("title");
        System.out.println("IPFilter init..." +title);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        String ip = req.getRemoteAddr();
        List<String> ipList = IPUtil.getIpList();
        log.info("IPFilter（"+title+"）:"+ip);
        if (!ipList.contains(ip)) {
            log.info("非法ip");
            resp.getWriter().write("非法ip");
            return;
        }
        filterChain.doFilter(req, resp);
    }
}
