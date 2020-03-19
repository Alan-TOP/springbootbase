package com.alan.springbootbase.controller;

import com.alan.springbootbase.entity.TeamPost;
import com.alan.springbootbase.entity.UserPost;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Alan
 * @Description
 * @date 2020年03月19日 14:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostControllerTest {

    @Test
    public void contextLoads() {
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
        System.out.println(JSON.toJSONString(team));
//		System.out.println(JSON.toJSONString(team, true));
    }
}