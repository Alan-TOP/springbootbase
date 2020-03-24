package com.alan.springbootbase.utils.json;

import net.sf.json.JSONObject;

/**
 * @author Alan
 * @Description
 * @date 2020年03月23日 10:42
 */
public class JsonLibUtil {

    /**
     *
     * @param obj
     * @return
     */
    public static String bean2Json(Object obj) {
        JSONObject jsonObject = JSONObject.fromObject(obj);
        return jsonObject.toString();
    }

    /**
     *
     * @param jsonStr
     * @param objClass
     * @param <T>
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
        return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr), objClass);
    }
}
