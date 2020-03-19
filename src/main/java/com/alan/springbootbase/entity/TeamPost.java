package com.alan.springbootbase.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Alan
 * @Description 用于Post请求前后台数据交互
 * @date 2020年03月19日 14:44
 */
@Data
public class TeamPost {


    /** id */
    private Integer id;

    /** 小组名字 */
    private String teamName;

    /** 所获称号 */
    private List<String> honors;

    /** 小组成员 */
    private List<UserPost> teamMembers;

    /**
     * 重写toString
     * @return
     */
    @Override
    public String toString() {
        // 遍历出小组所获荣耀
        StringBuffer sbHonors = new StringBuffer("荣耀start----\n");
        for (String honor : honors) {
            sbHonors.append(honor);
            sbHonors.append("\n");
        }
        sbHonors.append("荣耀end----\n");

        // 遍历出小组成员
        StringBuffer sbMembers = new StringBuffer("成员start----\n");
        for (UserPost user : teamMembers) {
            sbMembers.append(user.toString());
            sbMembers.append("\n");
        }
        sbMembers.append("成员end----\n");

        return "小组id:" + id + "\n" + "小组名字:" + teamName + "\n" + "小组所获荣誉:"
                + sbHonors + "\n" + "小组成员:" + sbMembers;
    }
}
