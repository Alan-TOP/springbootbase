package com.alan.springbootbase.utils.json;


import com.alan.springbootbase.entity.TeamPost;
import com.alan.springbootbase.entity.UserPost;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * @Description
 * @date 2020年03月24日 09:13
 */
public class JsonToolsTest {


    @Test
    public void jsonTools(){
        //String result=JsonTools.bean2Json(beanLode());
        String result=JsonTools.jsonFormatter(jsonStrLode());
        System.out.println("result:"+result);
    }













    // **************** Gson 测试类****************
    @Test
    public void gsonJsonBean2Json(){
        String result=GsonUtil.bean2Json(beanLode());
        System.out.println("result:"+result);
    }

    @Test
    public void gsonJson2Bean(){
        TeamPost result =GsonUtil.json2Bean(jsonStrLode(),TeamPost.class);
        System.out.println("result:"+result);
    }

    @Test
    public void jsonFormatter(){
        String result = GsonUtil.jsonFormatter(jsonStrLode());
        System.out.println("result:"+result);
    }


    // ****************FastJson测试类****************

    @Test
    public void fastJsonBean2Json(){

        String result=FastJsonUtil.bean2Json(beanLode());
        System.out.println("result:"+result);
    }


    @Test
    public void fastJsonJson2Bean(){
        TeamPost result =FastJsonUtil.json2Bean(jsonStrLode(),TeamPost.class);
        System.out.println("result:"+result);
    }

    // ****************Jackson测试类****************
    @Test
    public void jacksonBean2Json(){
        String result=JacksonUtil.bean2Json(beanLode());
        System.out.println("result:"+result);
    }

    @Test
    public void jacksonJson2Bean(){
        TeamPost result =JacksonUtil.json2Bean(jsonStrLode(),TeamPost.class);
        System.out.println("result:"+result);
    }



    // ****************JsonLib测试类****************
    @Test
    public void jsonLibBean2Json(){
        String result=JsonLibUtil.bean2Json(beanLode());
        System.out.println("result:"+result);
    }

    @Test
    public void jsonLibJson2Bean(){
        TeamPost result =JsonLibUtil.json2Bean(jsonStrLode(),TeamPost.class);
        System.out.println("result:"+result);
    }





    // ****************初始化数据组建****************
    /**
     * 组建Json串
     * @return
     */
    private String jsonStrLode(){
        return JSON.toJSONString(beanLode());
    }

    /**
     * 组建对象
     * @return
     */
    private TeamPost beanLode() {
        UserPost user1 = new UserPost();
        user1.setName("小丁");
        user1.setAge(40);
        user1.setGender("男");
        user1.setMotto("看俺防你一杆！");

        UserPost user2 = new UserPost();
        user2.setName("潘晓婷");
        user2.setAge(18);
        user2.setGender("女");
        user2.setMotto("动作要优雅！");

        UserPost user3 = new UserPost();
        user3.setName("邓沙利文");
        user3.setAge(24);
        user3.setGender("男");
        user3.setMotto("就是这么牛逼！");

        List<UserPost> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        List<String> honorList = new ArrayList<>();
        honorList.add("速度最快");
        honorList.add("高度最高");
        honorList.add("合作最默契");

        TeamPost team = new TeamPost();
        team.setId(1);
        team.setTeamName("地表最强战队");
        team.setHonors(honorList);
        team.setTeamMembers(userList);
        return team;
    }
}