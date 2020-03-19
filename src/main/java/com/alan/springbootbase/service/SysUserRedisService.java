package com.alan.springbootbase.service;

import com.alan.springbootbase.entity.SysUser;

import java.util.List;

/**
 * @description:
 * @author: Alan
 * @create: 2019-08-06 17:55
 **/
public interface SysUserRedisService {

    List<SysUser> findAll();

    List<SysUser> findByName(String name);

    List<SysUser> findLikeName(String name);

    List<SysUser> findByUsernameStartingWith(String name);

    List<SysUser> findByUsernameEndingWith(String name);

    List<SysUser> findByUsernameContaining(String name);

    List<SysUser> findLikeEmail(String email);

    List<SysUser> findLikePhone(String phone);

    SysUser findOne(int id);

    SysUser saveEntity(SysUser sysUser);

    SysUser updateEntity(SysUser sysUser);

    void deleteEntyty(SysUser sysUser);

    void deleteById(int id);

}
