package com.alan.springbootbase.utils.json;

import com.alibaba.fastjson.JSON;

/**
 * @author Alan
 * @Description
 * @date 2020年03月23日 10:38
 */
public class FastJsonUtil {

        public static String bean2Json(Object obj) {
            return JSON.toJSONString(obj);
        }

        public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
            return JSON.parseObject(jsonStr, objClass);
        }

}
