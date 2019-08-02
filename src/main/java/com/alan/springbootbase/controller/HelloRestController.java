package com.alan.springbootbase.controller;

import net.sf.json.xml.XMLSerializer;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("rest")
public class HelloRestController {

    Logger logger = LoggerFactory.getLogger(HelloRestController.class);

    @RequestMapping("hello")
    public String hello(){
        logger.debug("rest hello");
        return "hello Alan";
    }

    @RequestMapping("aa")
    public  String  putData(){

        String xml=getMsg();
        System.out.println("xml:"+xml);
        //XMLSerializer aaaaaa=new XMLSerializer();
        JSONObject xmlJSONObj = XML.toJSONObject(xml);
       // String aa= aaaaaa.read(xml).toString();
        System.out.println("aa:"+xmlJSONObj.getJSONObject("Service").getJSONObject("Service_Body").getJSONObject("request").getString("KEY_LABEL"));
        System.out.println("aa:"+xmlJSONObj.toString());
        /*
         * JSONObject js=JSONObject.fromObject(aa); System.out.println(js.toString());
         */
        return xmlJSONObj.toString();
    }

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
