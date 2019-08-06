package com.alan.springbootbase.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("rest")
@Slf4j
public class HelloRestController {

    @Value("${com.alan.title}")
    private String title;


    /**
     * 测试方法
     * @return
     */
    @RequestMapping("hello")
    public String hello(){
        log.info("rest hello");
        System.out.println("rest hello");
        return "hello "+title;
    }

    @PostMapping(value = "/getPost",produces = "application/json;charset=UTF-8")
    public String getPost(@RequestBody String resStr) {
        JSONObject reqJson=new JSONObject(resStr);
        return reqJson.getString("name");
    }

    /**
     * XML转json数据
     * @return
     */
    @RequestMapping("aa")
    public  String  putData(){

        String xml=getMsg();
        System.out.println("xml:"+xml);

        JSONObject xmlJSONObj = XML.toJSONObject(xml);
        /*
        //该方式报错了，暂未找到解决方法
        XMLSerializer aaaaaa=new XMLSerializer();
        String aa= aaaaaa.read(xml).toString();*/

        System.out.println("aa:"+xmlJSONObj.getJSONObject("Service").getJSONObject("Service_Body").getJSONObject("request").getString("KEY_LABEL"));
        System.out.println("aa:"+xmlJSONObj.toString());
        /*
         * JSONObject js=JSONObject.fromObject(aa); System.out.println(js.toString());
         */
        return xmlJSONObj.toString();
    }

    /**
     * 获取XML字符串
     * @return
     */
    private String getMsg() {
        StringBuffer sb = new StringBuffer();
        //必须,消息报文发送的时间戳(14位)(YYYYMMDDHHmmss)
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String now=df.format(new Date());

        sb.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" );
        sb.append( "<Service>" );
        sb.append( "<Service_Header>" );
        sb.append( "<service_sn>").append(now).append("</service_sn>");
        sb.append( "<service_id>").append(now).append("</service_id>");
        sb.append( "<branch_id>").append(now).append("</branch_id>");
        sb.append( "<requester_id>").append(now).append("</requester_id>");
        sb.append( "<channel_id>").append(now).append("</channel_id>");
        sb.append( "<version_id>").append(now).append("</version_id>");
        sb.append( "<service_time>").append(now).append("</service_time>");
        sb.append( "</Service_Header>");
        sb.append( "<Service_Body>");
        sb.append( "<ext_attributes></ext_attributes>");
        sb.append( "<request>");
        sb.append( "<KEY_LABEL>X817FU01</KEY_LABEL>");
        sb.append( "</request>");
        sb.append( "</Service_Body>");
        sb.append( "</Service>");

        return sb.toString();
    }
}
