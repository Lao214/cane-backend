package com.example.integratedHub.auth;

import com.alibaba.fastjson.JSON;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@title MyAuthenticationEntryPoint （未认证处理）
 *@description 创建MyAuthenticationEntryPoint类实现AuthenticationEntryPoint接口，用于未认证的处理
 *@author echoes
 *@version 1.0
 *@create 2024/5/11 14:20
 **/

@Slf4j
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        log.info("未认证：" + authException);
        //返回响应
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(Result.error().msg(ErrorCode.UN_AUTH.getMsg()).code(ErrorCode.UN_AUTH.getCode())));
    }

}
