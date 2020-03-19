package com.alan.springbootbase.service.impl;

import com.alan.springbootbase.entity.SysUser;
import com.alan.springbootbase.repository.SysUserRepository;
import com.alan.springbootbase.service.SysUserRedisService;
import com.alan.springbootbase.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysUserRedisServiceImpl implements SysUserRedisService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public List<SysUser> findByName(String name) {
        return sysUserRepository.findByUsername(name);
    }

    @Override
    public List<SysUser> findLikeName(String name) {
        return sysUserRepository.findByUsernameLike(name);
    }

    @Override
    public List<SysUser> findByUsernameStartingWith(String name) {
        return sysUserRepository.findByUsernameStartingWith(name);
    }

    @Override
    public List<SysUser> findByUsernameEndingWith(String name) {
        return sysUserRepository.findByUsernameEndingWith(name);
    }

    @Override
    public List<SysUser> findByUsernameContaining(String name) {
        return sysUserRepository.findByUsernameContaining(name);
    }

    @Override
    public List<SysUser> findLikeEmail(String email) {
        return sysUserRepository.findLikeEmail(email);
    }

    @Override
    public List<SysUser> findLikePhone(String phone) {
        return sysUserRepository.findLikePhone(phone);
    }


    /**
     * 以下设计Redis的集成
     * @param id
     * @return
     */

    @Cacheable(value = "redisCache",key="#id")
    @Override
    public SysUser findOne(int id) {
        log.info("查询数据库：findOne");
        return sysUserRepository.getOne(id);
    }

    @CachePut(value = "redisCache",key="#sysUser.id")
    @Override
    public SysUser saveEntity(SysUser sysUser) {
        log.info("查询数据库：saveEntity");
        return sysUserRepository.save(sysUser);
    }
    @CachePut(value = "redisCache",key="#sysUser.id")
    @Override
    public SysUser updateEntity(SysUser sysUser) {

        log.info("查询数据库：updateEntity");
        return sysUserRepository.save(sysUser);
    }

    @CacheEvict(value = "redisCache",key="#sysUser.id")
    @Override
    public void deleteEntyty(SysUser sysUser) {
        log.info("查询数据库：deleteEntyty");
        sysUserRepository.delete(sysUser);
    }
    @CacheEvict(value = "redisCache",key="#id")
    @Override
    public void deleteById(int id) {

        log.info("查询数据库：deleteById");
        sysUserRepository.deleteById(id);
    }
}
