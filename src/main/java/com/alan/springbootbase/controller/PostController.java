package com.alan.springbootbase.controller;

import com.alan.springbootbase.entity.TeamPost;
import com.alan.springbootbase.entity.UserPost;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Alan
 * @Description 用于Post请求前后台数据交互
 * 前后端数据交互
 * @date 2020年03月19日 14:45
 */

@RestController
@RequestMapping("post")
@Slf4j
public class PostController {



    /**
     * @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的)；
     * GET方式无请求体，所以使用@RequestBody接收数据时，前端不能使用GET方式提交数据，而是用POST方式进行提交。在后端的同一个接收方法里，
     * @RequestBody与@RequestParam()可以同时使用，
     * @RequestBody最多只能有一个，而@RequestParam()可以有多个。
     *
     * 注：一个请求，只有一个RequestBody；一个请求，可以有多个RequestParam。
     *
     * 注：当同时使用@RequestParam（）和@RequestBody时，@RequestParam（）指定的参数可以是普通元素、
     *        数组、集合、对象等等(即:当，@RequestBody 与@RequestParam()可以同时使用时，原SpringMVC接收
     *        参数的机制不变，只不过RequestBody 接收的是请求体里面的数据；而RequestParam接收的是key-value
     *        里面的参数，所以它会被切面进行处理从而可以用普通元素、数组、集合、对象等接收)。
     *        即：如果参数时放在请求体中，传入后台的话，那么后台要用@RequestBody才能接收到；如果不是放在
     *               请求体中的话，那么后台接收前台传过来的参数时，要用@RequestParam来接收，或则形参前
     *               什么也不写也能接收。
     *
     * 注：如果参数前写了@RequestParam(xxx)，那么前端必须有对应的xxx名字才行(不管其是否有值，当然可以通
     *        过设置该注解的required属性来调节是否必须传)，如果没有xxx名的话，那么请求会出错，报400。
     *
     * 注：如果参数前不写@RequestParam(xxx)的话，那么就前端可以有可以没有对应的xxx名字才行，如果有xxx名
     *        的话，那么就会自动匹配；没有的话，请求也能正确发送。
     *        追注：这里与feign消费服务时不同；feign消费服务时，如果参数前什么也不写，那么会被默认是
     *                   @RequestBody的。
     *
     * 如果后端参数是一个对象，且该参数前是以@RequestBody修饰的，那么前端传递json参数时，必须满足以下要求：
     *
     * 后端@RequestBody注解对应的类在将HTTP的输入流(含请求体)装配到目标类(即：@RequestBody后面的类)时，
     * 会根据json字符串中的key来匹配对应实体类的属性，如果匹配一致且json中的该key对应的值符合(或可转换为)，
     * 这一条我会在下面详细分析，其他的都可简单略过，但是本文末的核心逻辑代码以及几个结论一定要看！ 
     * 实体类的对应属性的类型要求时,会调用实体类的setter方法将值赋给该属性。
     *
     * json字符串中，如果value为""的话，后端对应属性如果是String类型的，那么接受到的就是""，如果是后端属性的类型是Integer、Double等类型，
     * 那么接收到的就是null。
     *
     * json字符串中，如果value为null的话，后端对应收到的就是null。
     *
     * 如果某个参数没有value的话，在传json字符串给后端时，要么干脆就不把该字段写到json字符串中；
     * 要么写value时， 必须有值，null  或""都行。千万不能有类似"stature":，这样的写法，
     *
     * https://blog.csdn.net/justry_deng/article/details/80972817
     */


    @PostMapping(value = "/getPost",produces = "application/json;charset=UTF-8")
    public String getPost(@RequestBody String resStr) {
        JSONObject reqJson=new JSONObject(resStr);
        return reqJson.getString("name")+"+++++"+reqJson.getString("code");
    }

    /**
     * 直接以String接收前端传过来的json数据
     *
     * localhost:8081/post/mytest0
     *
     *{
     * 	"name":"Alan",
     * 	"age":"18",
     * 	"gender":"男",
     * 	"motto":"我是一只小小鸟......."
     * }
     * @param jsonString
     *            json格式的字符串
     * @return json格式的字符串
     * @date 2018年7月9日 下午1:24:13
     */
    @RequestMapping("mytest0")
    public String myTestController0(@RequestBody String jsonString) {
        System.out.println(jsonString);
        return jsonString;
    }

    /**
     * 以较简单的User对象接收前端传过来的json数据 (SpringMVC会智能的将符合要求的数据装配进该User对象中)
     *
     * localhost:8081/post/mytest1
     * {
     * 	"name":"Alan",
     * 	"age":"18",
     * 	"gender":"男",
     * 	"motto":"我是一只小小鸟......."
     * }
     *
     * @param user
     *            用户实体类模型
     * @return User重写后的toString
     * @date 2018年7月9日 下午1:29:47
     */
    @RequestMapping("mytest1")
    public String myTestController1(@RequestBody UserPost user) {
        System.out.println(user.toString());
        return user.toString();
    }

    /**
     * 以较复杂的Team对象接收前端传过来的json数据 (SpringMVC会智能的将符合要求的数据装配进该Teamr对象中)
     * 注:如果后端@RequestBody后的对象，持有了集合等,当前端向传参 令该对象持有的该集合为空时,json字符串中,
     * 对应位置应该形如"teamMembers":[]这么写;即:传递的json字符串中必须要有key，否者请求会出错
     *
     * localhost:8081/post/mytest2
     *
     * {
     *     "honors": [
     *         "速度最快",
     *         "高度最高",
     *         "合作最默契"
     *     ],
     *     "id": 1,
     *     "teamMembers": [
     *         {
     *             "age": 40,
     *             "gender": "男",
     *             "motto": "看俺防你一杆！",
     *             "name": "小丁"
     *         },
     *         {
     *             "age": 18,
     *             "gender": "女",
     *             "motto": "动作要优雅！",
     *             "name": "潘晓婷"
     *         },
     *         {
     *             "age": 24,
     *             "gender": "男",
     *             "motto": "就是这么牛逼！",
     *             "name": "邓沙利文"
     *         }
     *     ],
     *     "teamName": "地表最强战队"
     * }
     *
     * @param team
     *            团队实体类模型
     * @return Team重写后的toString
     * @date 2018年7月9日 下午1:30:46
     */
    @RequestMapping("mytest2")
    public String myTestController2(@RequestBody TeamPost team) {
        System.out.println(team.toString());
        return team.toString();
    }

    /**
     * @RequestBody与简单的@RequestParam()同时使用
     *
     * localhost:8081/post/mytest3?token=admin
     *
     * {
     * 	"name":"Alan",
     * 	"age":"18",
     * 	"gender":"男",
     * 	"motto":"我是一只小小鸟......."
     * }
     * @param user
     *            用户实体类模型
     * @date 2018年7月6日 上午2:11:40
     */
    @RequestMapping("mytest3")
    public String myTestController3(@RequestBody UserPost user, @RequestParam("token") String token) {
        System.out.println(user.toString());
        System.out.println(token);
        return token + ">>>" + user.toString();
    }

    /**
     * @RequestBody装配请求体重的信息;
     * 第二个参数不加注解,装配url中的参数信息
     *
     * localhost:8081/post/mytest4?name=zhangsan&age=22&gender=女&motto=一只小小鸟.......
     *
     * {
     * 	"name":"Alan",
     * 	"age":"18",
     * 	"gender":"男",
     * 	"motto":"我是一只小小鸟......."
     * }
     *
     * @param user1
     *            用户实体类模型
     * @param user2
     *
     * @return
     * @date 2018年7月9日 下午2:24:53
     */
    @RequestMapping("mytest4")
    public String myTestController4(@RequestBody UserPost user1,  UserPost user2) {
        System.out.println(user1.toString());
        System.out.println(user2.toString());
        return user2.toString() + "\n" + user1.toString();
    }

    /**
     * @RequestBody与复杂的@RequestParam()同时使用 注:这里 以集合 或者 以数组 接收数据都可以
     *
     * @param user
     *            用户实体类模型
     * @param arrays
     *            从'key-value'中获取到的集合数组
     * @return
     * @date 2018年7月9日 下午1:34:33
     */
    @RequestMapping("mytest5")
    public String myTestController5(@RequestBody UserPost user, @RequestParam("arrays") List<String> arrays) {
        System.out.println(user.toString());
        StringBuffer sb = new StringBuffer();
        for (String array : arrays) {
            sb.append(array);
            sb.append("  ");
            System.out.println(array);
        }
        return sb.toString() + user.toString();
    }

    /**
     * 如果参数前没有@RequestParam()，那么前端调用该请求时,这个参数可写可不写;
     * 注:写了该参数，那么会装配到;如果没写该参数,也不会请求出错
     * 注:如果参数前写了@RequestParam()，那么前端调用该请求时,这个参数的参数名，参数自可以没有可以为空;
     *
     * @param user
     *            用户实体类模型
     * @param token
     *            参数token
     * @return
     * @date 2018年7月9日 下午1:38:54
     */
    @RequestMapping("mytest6")
    public String myTestController6(@RequestBody UserPost user, String token) {
        System.out.println(user.toString());
        return token + ">>>" + user.toString();
    }


    /**
     * @RequestBody与前端传过来的json数据的匹配规则
     * 声明：根据不同的Content-Type等情况,Spring-MVC会采取不同的HttpMessageConverter实现来进行信息转换解析。
     *           下面介绍的是最常用的：前端以Content-Type 为application/json,传递json字符串数据;后端以@RequestBody
     *           模型接收数据的情况。
     *
     * 解析json数据大体流程概述：
     *         Http传递请求体信息，最终会被封装进com.fasterxml.jackson.core.json.UTF8StreamJsonParser中
     * (提示：Spring采用CharacterEncodingFilter设置了默认编码为UTF-8)，然后在public class BeanDeserializer extends
     *  BeanDeserializerBase implements java.io.Serializable中，通过 public Object deserializeFromObject(JsonParser p, DeserializationContext ctxt)
     * throws IOException方法进行解析。
     *
     * 核心逻辑分析示例：
     *         假设前端传的json串是这样的： {"name1":"邓沙利文","age":123,"mot":"我是一只小小小小鸟~"}
     * 后端的模型只有name和age属性，以及对应的setter/getter方法；给出一般用到的deserializeFromObject(JsonParser p, DeserializationContext ctxt)
     * 方法的核心逻辑
     * ————————————————
     * 原文链接：https://blog.csdn.net/justry_deng/article/details/80972817
     */
}
