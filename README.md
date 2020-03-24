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



