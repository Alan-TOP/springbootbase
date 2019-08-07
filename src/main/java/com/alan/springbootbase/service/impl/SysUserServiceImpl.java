package com.alan.springbootbase.service.impl;

import com.alan.springbootbase.entity.SysUser;
import com.alan.springbootbase.repository.SysUserRepository;
import com.alan.springbootbase.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

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

    @Override
    public SysUser findOne(int id) {
        return sysUserRepository.getOne(id);
    }

    @Override
    public SysUser saveEntity(SysUser sysUser) {
        return sysUserRepository.save(sysUser);
    }

    @Override
    public SysUser updateEntity(SysUser sysUser) {
        return sysUserRepository.save(sysUser);
    }

    @Override
    public void deleteEntyty(SysUser sysUser) {
        sysUserRepository.delete(sysUser);
    }

    @Override
    public void deleteById(int id) {
        sysUserRepository.deleteById(id);
    }
}
