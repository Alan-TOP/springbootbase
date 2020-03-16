package com.alan.springbootbase.controller;

import com.alan.springbootbase.entity.SysUser;
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
 **/
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUserService.findAll();
    }

    @GetMapping("/findByName")
    public List<SysUser> findByName(){

        return sysUserService.findByName("Alan");
    }

    @GetMapping("/findLikeName")
    public List<SysUser> findLikeName(){
        return sysUserService.findLikeName("qq");
    }

    @GetMapping("/findByUsernameStartingWith")
    public List<SysUser> findByUsernameStartingWith(String name){
        return sysUserService.findByUsernameStartingWith("q");
    }

    @GetMapping("/findByUsernameEndingWith")
    public List<SysUser> findByUsernameEndingWith(String name){
        return sysUserService.findByUsernameEndingWith("w");
    }

    @GetMapping("/findByUsernameContaining")
    public List<SysUser> findByUsernameContaining(String name){
        return sysUserService.findByUsernameContaining("s");
    }

    @GetMapping("/findLikeEmail")
    public List<SysUser> findLikeEmail(){

        return sysUserService.findLikeEmail("ss");
    }

    @GetMapping("/findLikePhone")
    public List<SysUser> findLikePhone(){

        return sysUserService.findLikePhone("q");
    }

    @GetMapping("/findOne")
    public SysUser findOne(int id){
        return sysUserService.findOne(id);
    }

    @GetMapping("/save")
    public SysUser saveEntity(){
        SysUser sysUser =new SysUser();
        sysUser.setCode("code");
        sysUser.setUsername("username");
        sysUser.setPassWord("password");
        sysUser.setEmail("ansssss@163.com");
        sysUser.setPhoneNum("phonenum");
        return sysUserService.saveEntity(sysUser);
    }

    @GetMapping("/update")
    public SysUser updateEntity(SysUser sysUser){

        sysUser.setId(sysUser.getId());
        sysUser.setCode("aaaa");
        sysUser.setEmail("uyuyuyuyu@163.com");
        return sysUserService.updateEntity(sysUser);
    }

    @GetMapping("/delete")
    public void deleteEntyty(SysUser sysUser){
        sysUserService.deleteEntyty(sysUser);
    }

    @GetMapping("/deleteById")
    public void deleteById(int id){
        sysUserService.deleteById(id);
    }
}
