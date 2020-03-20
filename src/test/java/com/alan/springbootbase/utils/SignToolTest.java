package com.alan.springbootbase.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Alan
 * @Description
 * @date 2020年03月20日 16:48
 */
public class SignToolTest {

    @Test
    public void sign() {
        System.out.println(SignTool.Sign("qqqq","wwwwwwwwwww"));
    }

    @Test
    public void isEqualsSign() {
        System.out.println(SignTool.isEqualsSign("qqqq","wwwwwwwwwww","628fceeb49e509fd55c8ff81676028a6"));
    }
}