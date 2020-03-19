package com.alan.springbootbase.controller;

import com.alan.springbootbase.entity.SysUser;
import com.alan.springbootbase.service.SysUserRedisService;
import com.alan.springbootbase.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: JPA增删改查
 * @author: Alan
 * @create: 2019-08-07 10:20
 *
 *
 *
 **/
@RestController
@RequestMapping("/redis/user")
public class SysUserRedisController {

    @Autowired
    private SysUserRedisService sysUserRedisService;

    @GetMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUserRedisService.findAll();
    }

    @GetMapping("/findByName")
    public List<SysUser> findByName(){

        return sysUserRedisService.findByName("Alan");
    }

    @GetMapping("/findLikeName")
    public List<SysUser> findLikeName(){
        return sysUserRedisService.findLikeName("qq");
    }

    @GetMapping("/findByUsernameStartingWith")
    public List<SysUser> findByUsernameStartingWith(String name){
        return sysUserRedisService.findByUsernameStartingWith("q");
    }

    @GetMapping("/findByUsernameEndingWith")
    public List<SysUser> findByUsernameEndingWith(String name){
        return sysUserRedisService.findByUsernameEndingWith("w");
    }

    @GetMapping("/findByUsernameContaining")
    public List<SysUser> findByUsernameContaining(String name){
        return sysUserRedisService.findByUsernameContaining("s");
    }

    @GetMapping("/findLikeEmail")
    public List<SysUser> findLikeEmail(){

        return sysUserRedisService.findLikeEmail("ss");
    }

    @GetMapping("/findLikePhone")
    public List<SysUser> findLikePhone(){

        return sysUserRedisService.findLikePhone("q");
    }

    @GetMapping("/findOne")
    public SysUser findOne(int id){
        return sysUserRedisService.findOne(id);
    }

    @GetMapping("/save")
    public SysUser saveEntity(){
        SysUser sysUser =new SysUser();
        sysUser.setCode("code");
        sysUser.setUsername("username");
        sysUser.setPassWord("password");
        sysUser.setEmail("ansssss@163.com");
        sysUser.setPhoneNum("phonenum");
        return sysUserRedisService.saveEntity(sysUser);
    }

    @GetMapping("/update")
    public SysUser updateEntity(SysUser sysUser){

        sysUser.setId(sysUser.getId());
        sysUser.setCode("aaaa");
        sysUser.setEmail("uyuyuyuyu@163.com");
        return sysUserRedisService.updateEntity(sysUser);
    }

    @GetMapping("/delete")
    public void deleteEntyty(SysUser sysUser){
        sysUserRedisService.deleteEntyty(sysUser);
    }

    @GetMapping("/deleteById")
    public void deleteById(int id){
        sysUserRedisService.deleteById(id);
    }
}
