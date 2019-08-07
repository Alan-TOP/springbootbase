package com.alan.springbootbase.repository;

import com.alan.springbootbase.entity.SysUser;
import javafx.stage.StageStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @description:
 * @author: Alan
 * @create: 2019-08-06 17:54
 **/
public interface SysUserRepository extends JpaRepository<SysUser,Integer> {

    List<SysUser> findByUsername(String name);

    List<SysUser> findByUsernameLike(String name);

    List<SysUser> findByUsernameStartingWith(String name);

    List<SysUser> findByUsernameEndingWith(String name);

    List<SysUser> findByUsernameContaining(String name);

    @Query(value = "select * from sys_users  where email like CONCAT('%',?1,'%')",nativeQuery = true)
    List<SysUser> findLikeEmail(String email);

    @Query(value = "select * from sys_users  where phone_num like CONCAT('%',:phoneNum,'%')",nativeQuery = true)
    List<SysUser> findLikePhone(@Param("phoneNum") String phoneNum);
}
