package com.example.integratedHub.auth;/*
 *@title JwtAuthenticationEntryPoint
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/15 16:26
 */

import com.alibaba.fastjson.JSON;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
//        log.info("未认证：" + authException);
        //返回响应
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(Result.error().msg(ErrorCode.TOKEN_IS_EXPIRED.getMsg()).code(ErrorCode.TOKEN_IS_EXPIRED.getCode())));
    }
}

