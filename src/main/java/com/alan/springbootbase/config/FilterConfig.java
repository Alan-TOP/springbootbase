package com.alan.springbootbase.config;

import com.alan.springbootbase.filter.IPFilter;
import com.alan.springbootbase.filter.MyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @Description
 * 用于配置Filter，并设置Filter执行顺序
 * @date 2020年03月20日 10:27
 */
@Configuration
public class FilterConfig {

    @Value("${com.alan.title}")
    private String title;

    /**
     *SpringBoot中使用Servlet,Filter,Listener的三种方式
     *原文：https://www.cnblogs.com/Eternally-dream/p/9781294.html
     *1. 使用Bean的实现方式：
     *  首先创建一个Servlet，一个Filter,一个Listener
     *  然后直接在SpringBoot的启动类中将上面几个方法注册为Bean
     *2. 实现ServletContextInitializer接口
     *  首先创建一个Servlet，一个Filter,一个Listener
     *  直接在SpringBoot的启动类中实现ServletContextInitializer，重写onStartup()方法也可以使用Servlet.Filter,Listener
     *3. 使用注解的方式
     *     SpringBoot官方推荐的方式，也是三种方式中最简单的方式，
     *     只需要在SpringBoot的启动类上添加@ServletComponentScan注解，
     *     在DemoServlet类上添加@WebServlet(name = "demoServlet", urlPatterns = "/demoServlet")注解，
     *     DemoFilter类上添加@WebFilter(urlPatterns = "/*", dispatcherTypes = DispatcherType.FORWARD)（拦截所有请求），
     *     DemoListener类上添加@WebListener即可，运行结果与1相同。
     *     注意：****注解@Order(int) ，配合 @WebFilter 注解使用，用于多个过滤器时定义执行顺序，值越小越先执行。
     *
     *
     * *******问题：**********
     * 原文：https://cloud.tencent.com/developer/article/1513212
     *  方案3中说，使用@Order注解指定一个int值，越小越先执行。很多博客文章都是这么说的，但你真正的试了吗？真的可以使用这个注解指定顺序吗？答案是否定的。
     *
     *   经过测试，发现 @Order 注解指定 int 值没有起作用，是无效的。为啥？因为看源码发现 @WebFilter 修饰的过滤器在加载时，
     * 没有使用 @Order 注解，而是使用的类名来实现自定义Filter顺序，详细的可以参考这篇或者是这篇
     *
     *   所以这种方式下想定义Filter的顺序，就必须限定 Filter 的类名，比如刚才那个 Filter 叫 ReqResFilter ，
     * 假如我们现在新写了一个 Filter 叫 AlibabaFilter ，那么顺序就是 AlibabaFilter > ReqResFilter 。
     *
     * 所以这种方式虽然实现起来简单，只需要注解，但自定义顺序就必须要限定类名，使用类名达到排序效果了。
     *
     * 如果要实现自定义顺序，就用方案1。
     *
     */

    @Bean
    public FilterRegistrationBean reqResFilter1() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        IPFilter ipFilter = new IPFilter();

        filterRegistrationBean.setFilter(ipFilter);
        //配置过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //设置init参数:如果想直接在Filter中 使用 @Value 注解注入再用参数接收配置文件数据
        //但是,很遗憾,这样是注入不进来的,因为 我们的Filter 并不是一个bean,也就是说并没有纳入到spring容器中进行管理,也就无从谈起自动装配了
        //一个优雅的解决方案是什么呢? 我们可以利用配置 filter 时,传递给它初始化参数:如下
        // 原文：https://blog.csdn.net/itguangit/article/details/78349033
        filterRegistrationBean.addInitParameter("title",title);
        //设置过滤器名称
        filterRegistrationBean.setName("ipFilter");
        //执行次序
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean reqResFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        MyFilter myFilter = new MyFilter();
        filterRegistrationBean.setFilter(myFilter);

        //配置多个过滤规则
        List<String> urls = new ArrayList<>();
        urls.add("/rest/*");
        urls.add("/post/*");
        filterRegistrationBean.setUrlPatterns(urls);

        //设置过滤器名称
        filterRegistrationBean.setName("myFilter");
        //执行次序
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
