package com.alan.springbootbase.enums;

/**
 * @author Alan
 * @Description
 * @date 2020年03月20日 14:18
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    UNKONW_ERROR(-1, "未知错误"),
    ;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
