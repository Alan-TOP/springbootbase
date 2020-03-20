package com.alan.springbootbase.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @Description 白名单设置，filter过滤
 * @date 2020年03月19日 21:24
 */
public class IPUtil {

    private static List<String> ipList = new ArrayList<>();


    //实际业务中该列表是在数据库中读取的
    static {
        ipList.add("192.168.1.102");
        //本机的ip
        ipList.add("0:0:0:0:0:0:0:1");
    }

    public static List<String> getIpList() {
        return ipList;
    }
}
