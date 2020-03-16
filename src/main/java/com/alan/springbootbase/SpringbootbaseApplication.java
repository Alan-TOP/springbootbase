package com.alan.springbootbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @EnableCaching 开启缓存功能，放在相应配置类或启动类上
 * @CacheConfig 缓存配置，设置缓存名称
 * @Cacheable 执行方法前先查询缓存，有则直接返回缓存数据，否则查询数据再将数据放入缓存；也可以放类上，表示该类所有方法都支持缓存
 * @CachePut 执行新增或更新方法后，将数据放入缓存中（每次都会执行，保持缓存和数据始终一致）
 * @CacheEvict 清除缓存
 * @Caching 组合多个缓存操作
 * */
@EnableCaching
@SpringBootApplication
@ServletComponentScan(basePackages = "com.alan")
public class SpringbootbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootbaseApplication.class, args);
    }

}
