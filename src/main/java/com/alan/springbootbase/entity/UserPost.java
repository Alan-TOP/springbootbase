package com.alan.springbootbase.entity;

import lombok.Data;

/**
 * @author Alan
 * @Description 用于Post请求前后台数据交互
 * @date 2020年03月19日 14:42
 */
@Data
public class UserPost {

    /**
     * 关于@JsonAlias注解和@JsonProperty注解
     *
     * 序列化是指：把对象》》》转换为》》》》》字节序列的过程，
     * 反序列化是指：把字节序列》》》恢复为》》》对象的过程
     *
     * 关于@JsonProperty
     * 这个注解提供了序列化和反序列化过程中该java属性所对应的名称
     * 关于@JsonAlias
     * 这个注解只只在反序列化时起作用，指定该java属性可以接受的更多名称
     *
     *
     * 结论①：@JsonAlias注解，实现:json转模型时，使json中的特定key能转化为特定的模型属性;但是模型转json时，
     *                对应的转换后的key仍然与属性名一致，见：上图示例中的name字段的请求与响应。
     *                以下图进一步说明：
     *
     *                @JsonAlias(value = {"name123", "Name"})
     *                private String name;
     *
     *             此时，json字符串转换为模型时，json中key为Name或为name123或为name的都能识别。
     *                  但是模型转json时应的转换后的key仍然与属性名一致为name
     *
     * 结论②：@JsonProperty注解，实现：json转模型时，使json中的特定key能转化为指定的模型属性；同样的，模
     *                型转json时，对应的转换后的key为指定的key，见：示例中的motto字段的请求与响应。
     *                以下图进一步说明：
     *                  @JsonProperty("MOTTO")
     *                  private String motto;
     *
     *                此时，json字符串转换为模型时，key为MOTTO的能识别，但key为motto的不能识别。
     *                     模型转json时应的转换后的key仍然为指定的key(MOTTO)
     *
     * 结论③：@JsonAlias注解需要依赖于setter、getter，而@JsonProperty注解不需要。
     *
     * 结论④：在不考虑上述两个注解的一般情况下，key与属性匹配时,默认大小写敏感。
     *
     * 结论⑤：有多个相同的key的json字符串中，转换为模型时，会以相同的几个key中，排在最后的那个key的值给模
     *                型属性复制，因为setter会覆盖原来的值。见示例中的gender属性。
     *
     * 结论⑥：后端@RequestBody注解对应的类在将HTTP的输入流(含请求体)装配到目标类(即:@RequestBody后面
     *                的类)时，会根据json字符串中的key来匹配对应实体类的属性，如果匹配一致且json中的该key对应的值
     *                符合(或可转换为)实体类的对应属性的类型要求时，会调用实体类的setter方法将值赋给该属性。
     *
     */




    /** 姓名 */
    private String name;

    /** 年龄 */
    private Integer age;

    /** 性别 */
    private String gender;

    /** 座右铭 */
    private String motto;

    @Override
    public String toString() {

        return age + "岁" + gender + "人[" + name + "]的座右铭居然是: " + motto + "!!!";
    }
}
