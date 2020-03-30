# springbootbase

简介：
基于springboot搭建一个基础开发框架
该项目主要用于总结规整springboot所涉及到的相关技术



目前项目已实现集成如下：


#restController



[TOC]



# Java几种常用JSON库性能比较

原文：

- https://blog.csdn.net/jiyueqianxue/article/details/83377181
- https://www.cnblogs.com/javalyy/p/10723634.html

JSON不管是在Web开发还是服务器开发中是相当常见的数据传输格式，一般情况我们对于JSON解析构造的性能并不需要过于关心，除非是在性能要求比较高的系统。

目前对于Java开源的JSON类库有很多种，下面我们取4个常用的JSON库进行性能测试对比， 同时根据测试结果分析如果根据实际应用场景选择最合适的JSON库。

这4个JSON类库分别为：Gson，FastJson，Jackson，Json-lib。

## 简单介绍

选择一个合适的JSON库要从多个方面进行考虑：

1. 字符串解析成JSON性能
2. 字符串解析成JavaBean性能
3. JavaBean构造JSON性能
4. 集合构造JSON性能
5. 易用性

先简单介绍下四个类库的身份背景

### Gson

项目地址：https://github.com/google/gson

Gson是目前功能最全的Json解析神器，Gson当初是为因应Google公司内部需求而由Google自行研发而来，但自从在2008年五月公开发布第一版后已被许多公司或用户应用。 Gson的应用主要为toJson与fromJson两个转换函数，无依赖，不需要例外额外的jar，能够直接跑在JDK上。 在使用这种对象转换之前，需先创建好对象的类型以及其成员才能成功的将JSON字符串成功转换成相对应的对象。 类里面只要有get和set方法，Gson完全可以实现复杂类型的json到bean或bean到json的转换，是JSON解析的神器。

### FastJson

项目地址：https://github.com/alibaba/fastjson

Fastjson是一个Java语言编写的高性能的JSON处理器,由阿里巴巴公司开发。无依赖，不需要例外额外的jar，能够直接跑在JDK上。 FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。 FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。

```
FastJson对于json格式字符串的解析主要用到了一下三个类：
		（1）JSON：fastJson的解析器，用于JSON格式字符串与JSON对象及javaBean之间的转换。
    （2）JSONObject：fastJson提供的json对象。
    （3）JSONArray：fastJson提供json数组对象。
```



### Jackson

项目地址：https://github.com/FasterXML/jackson

Jackson是当前用的比较广泛的，用来序列化和反序列化json的Java开源框架。Jackson社区相对比较活跃，更新速度也比较快， 从Github中的统计来看，Jackson是最流行的json解析器之一，Spring MVC的默认json解析器便是Jackson。

Jackson优点很多：

1. Jackson 所依赖的jar包较少，简单易用。
2. 与其他 Java 的 json 的框架 Gson 等相比，Jackson 解析大的 json 文件速度比较快。
3. Jackson 运行时占用内存比较低，性能比较好
4. Jackson 有灵活的 API，可以很容易进行扩展和定制。

目前最新版本是2.9.4，Jackson 的核心模块由三部分组成：

1. jackson-core 核心包，提供基于”流模式”解析的相关 API，它包括 JsonPaser 和 JsonGenerator。Jackson 内部实现正是通过高性能的流模式 API 的 JsonGenerator 和 JsonParser 来生成和解析 json。
2. jackson-annotations 注解包，提供标准注解功能；
3. jackson-databind 数据绑定包，提供基于”对象绑定” 解析的相关 API（ ObjectMapper ）和”树模型” 解析的相关 API（JsonNode）；基于”对象绑定” 解析的 API 和”树模型”解析的 API 依赖基于”流模式”解析的 API。

为什么Jackson的介绍这么长啊？因为它也是本人的最爱。

### Json-lib

项目地址：http://json-lib.sourceforge.net/index.html

json-lib最开始的也是应用最广泛的json解析工具，json-lib 不好的地方确实是依赖于很多第三方包，对于复杂类型的转换，json-lib对于json转换成bean还有缺陷， 比如一个类里面会出现另一个类的list或者map集合，json-lib从json到bean的转换就会出现问题。json-lib在功能和性能上面都不能满足现在互联网化的需求。

## 总结

Json-lib在数据量在10W时OOM了，内存开到1G都不行，所以直接Pass了。 

从上面图表可以看到：

字符串>>>解析成>>>JavaBean：当数据量较少时首选FastJson，数据量较大使用Jackson。但是Jackson无法堆一个对象集合进行解析，只能转成一个Map集合，这点Gson和FastJson处理的比较好。

字符串>>>解析成>>>JSON：当数据量较少时首选FastJson，数据量较大使用Jackson。

JavaBean>>>构造>>>JSON：当数据量较少时选择Gson，数据量较大可使用Jackson。

集合>>>构造>>>JSON：首先Jackson，其次Fastjson。

上面是从性能角度分析四种JSON类库，从易用性角度来分析的话，FastJson的API设计的最简单，最方便使用，直接使用JSON的两个静态方法即可完成四种操作；而Gson和Jackson都需要new一个对象，虽然这个对象可以复用，但是在实际使用过程中还需要用一个全局变量来保存改变量，同时API设计的也不是很好理解，对于FastJson来说复杂的API是因为他支持流式解析，适合对JSON进行大量且复杂的操作，但是实际应用中对于JSON的操作都是简单的解析成JavaBean，然后JavaBean序列化成JSON字符串即可，复杂的操作很少。 

下面从我自己实际的应用场景出发，考虑该如何选择合适的JSON类库。 

应用场景：游戏服务器，基本是对客户端发送过来的JSON格式字符串解析成JavaBean，然后将封装好的指令转成JSON字符串返回给客户端，这里考虑到JavaBean转成JSON与集合转成JSON的性能差异，所以直接使用集合进行转成JSON，避免使用JavaBean。 

考虑上述场景适合使用FastJson进行JSON字符串解析，Jackson将集合转成JSON格式字符串。 



# Java多线程系列

- https://blog.csdn.net/weixin_44797490/article/details/91006241
- https://www.cnblogs.com/wgblog-code/p/11928825.html
- https://blog.csdn.net/renlianggee/article/details/90029746



# webservice相关

- https://blog.csdn.net/sujin_/article/details/83865124
- https://blog.csdn.net/wangyuanjun008/article/details/79121687
- https://www.cnblogs.com/javabg/p/11091145.html
- https://www.cnblogs.com/wuyongyin/p/11850585.html （推荐）
- https://www.cnblogs.com/qlqwjy/p/10567378.html	（不错推荐）webservice访问的几种方式
- https://blog.csdn.net/qq_31183297/article/details/79522746 （推荐）
- https://blog.csdn.net/u011165335/article/details/51345224
- 
- https://blog.csdn.net/menghuanzhiming/article/details/78475785
- https://blog.csdn.net/csdn_gia/article/details/54863549
- 
- http://blog.sina.com.cn/s/blog_4c925dca0100wz3v.html
- https://blog.csdn.net/ufocode/article/details/42814855
- https://cloud.tencent.com/developer/article/1148263
- https://blog.csdn.net/linjinhuo/article/details/78777694
- http://www.blogjava.net/zjhiphop/archive/2009/04/29/webservice.html
- https://blog.csdn.net/pengjwhx/article/details/83383959（推荐）
- https://blog.csdn.net/huanying33333/article/details/97107272
- https://blog.csdn.net/hgx_suiyuesusu/article/details/88918192

```
	wsimport生成本地代理的方式（简单--类似于SDK访问）
 	CXF动态调用:(推荐这种)   springboot 中多采用该技术 推荐动态调用，不要生成类那种，不灵活
 	通过axis包访问(简单)
  
  1、通过axis2将WebService提供的wsdl文件生成对应的java类，这样就可以相当调用本地类一样调用webService提供的接口。
    优点:调用简单，无需自己编写太多的东西。
    缺点:大部分情况根据对应的webService生成的服务中地址是固定的，不易更改，而且生成的代码过于庞大 ，不便于阅读。同时必须得有webservice对应的的wsdl文件，不太可控。
    2、通过RPC方式调用（推荐使用）
    优点:自己编写部分调用代码，可灵活更换调用的路径，适合分布式部署的服务器，只需要知道webservice服务的方法名、命名空间、以及对应的参数就好。
    缺点:部分特殊情况下可能可以调用成功，但是无法获取返回值。稍后会进行说明。
    3、通过HttpURLConnection进行调用，可用于补充第二种方法的不足之处。
    优点：补充RPC方式的不足，代码编写量较少。
    缺点：(C语言的WebService服务)大部分时候要自己编写输入的报文头，自己解析返回的报文。需要事先抓包查看报文。
    4、通过httpclient调用。
    和HttpURLConnection原理一样，只是用不同方法实现。优缺点也差不多。
    
    
1. 生成客户端调用方式
注意：该种方式使用简单，但一些关键的元素在代码生成时写死到生成代码中，不方便维护，所以仅用于测试。
（1）Wsimport命令介绍
Wsimport就是jdk（1.6版本之后）提供的的一个工具，他的作用就是根据WSDL地址生成客户端代码；
Wsimport位置JAVA_HOME/bin
Wsimport常用的参数：
-s，生成java文件的
-d，生成class文件的，默认的参数
-p，指定包名的，如果不加该参数，默认包名就是wsdl文档中的命名空间的倒序；
Wsimport仅支持SOAP1.1客户端的生成；
2. service编程调用方式
注意：
（1）该种方式可以自定义关键元素，方便以后维护，是一种标准的开发方式；
（2）这种方式同样需要wsimport生成客户端代码,只不过仅需要将服务接口类引入即可，例如如果需要<wsdl:port name="MobileCodeWSSoap" binding="tns:MobileCodeWSSoap">端口服务，则需要将生成的MobileCodeWSSoap.class类引入；
3. HttpURLConnection调用方式
开发步骤：
第一步：创建服务地址
第二步：打开一个通向服务地址的连接
第三步：设置参数

4. Ajax调用方式（存在跨域问题）
跨域解决参考：http://www.cnblogs.com/zhangzt/p/5966185.html
jsonp无法实现跨域，因为webservice服务的响应格式为xml格式；

```

