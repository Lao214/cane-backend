package com.example.integratedHub.auth;/*
 *@title SecurityLoginService
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 09:39
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class SecurityLoginService implements UserDetailsService {

    @Autowired
    private BUserService bUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<BUser> usersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据用户名称查询用户信息
        usersLambdaQueryWrapper.eq(BUser::getUsername, username);
        BUser users = bUserService.getOne(usersLambdaQueryWrapper);
        // users==null登录失败，users！=null登陆成功
        if(Objects.isNull(users)) {
            throw new UsernameNotFoundException("用户名错误！");
        }
        // 查询数据库获取用户权限信息
        List<String> authorityList = bUserService.getAuthority(users.getId());
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(users.getUsername());
        loginUser.setPassword(users.getPassword());
        loginUser.setAuthorityList(authorityList);
        loginUser.setNickname(users.getNickname());
        loginUser.setAvatar(users.getAvatar());
        loginUser.setId(users.getId() + "");
        loginUser.setRealname(users.getRealname());
        loginUser.setUnit(users.getUnit());
        loginUser.setFactory(users.getFactory());
        return loginUser;
    }

}