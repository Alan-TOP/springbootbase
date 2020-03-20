package com.alan.springbootbase.entity;

import lombok.Data;


/**
 * @author Alan
 * @Description
 * @date 2020年03月20日 16:19
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;
}
