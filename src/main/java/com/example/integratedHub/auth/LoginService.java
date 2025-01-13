package com.example.integratedHub.auth;/*
 *@title LoginService
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 10:36
 */


import com.alibaba.fastjson.JSON;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisService redisService;



    public String Login(BUser users) {
        // 进行用户认证,会调用之前写的SecurityLoginService中认证方法
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword());
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            // 认证通过：authenticate!=null  认证不通过：authenticate==null
            if (Objects.isNull(authenticate)){
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            // 认证通过，生成jjwt
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
            // 添加用户id 和 字符串”inHub“ 生成jjwt
            String jwtToken = JwtUtil.getJwtToken(loginUser.getUsername(), loginUser.getId());
            // 将用户信息存入redis中，用户id作为键
            redisService.setCacheObject(loginUser.getUsername(), loginUser);
            return jwtToken;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("用户名或密码错误");
        }
    }

    public String loginOut() {
        // SecurityContextHolder中获取userId
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        // 删除redis中用户信息缓存
        redisService.deleteObject(principal.getUsername());
        return principal.getUsername() + "已成功退出登录";
    }
}
