package com.example.integratedHub.auth;/*
 *@title MyAccessDeniedHandler
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/11 14:39
 */

import com.alibaba.fastjson.JSON;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(Result.error().msg(ErrorCode.UN_ACCESS.getMsg()).code(ErrorCode.UN_ACCESS.getCode())));
    }
}
