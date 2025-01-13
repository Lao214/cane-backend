package com.example.integratedHub.auth;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.example.integratedHub.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *@title MyAuthenticationSuccessHandler(认证成功处理)
 *@description 创建MyAuthenticationSuccessHandler类实现AuthenticationSuccessHandler接口，用于认证成功的处理
 *@author echoes
 *@version 1.0
 *@create 2024/5/11 14:24
 **/


@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final StringRedisTemplate stringRedisTemplate;

    public MyAuthenticationSuccessHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取请求的链接
        String requestURI = request.getRequestURI();

        Result result = Result.success().msg("认证成功！");

        // 如果请求链接是 '/login'，生成token，返回给前端并保存到redis缓存中
        if ("/login".equals(requestURI)) {
            //通过UUID生成token字符串,并将其以string类型的数据保存在redis缓存中，key为token，value为username
            String token= UUID.randomUUID().toString().replaceAll("-","");
            stringRedisTemplate.opsForValue().set(token, authentication.getName(),43200, TimeUnit.SECONDS);
            // 执行相应的逻辑，例如记录日志、发送通知等
            log.info( authentication.getName() + " is login");
            result.data("token",token);
        }

        //返回响应
        response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSON.toJSONString(result));
    }

}
