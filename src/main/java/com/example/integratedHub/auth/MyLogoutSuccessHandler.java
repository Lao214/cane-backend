package com.example.integratedHub.auth;

import com.alibaba.fastjson.JSON;
import com.example.integratedHub.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@title MyLogoutSuccessHandler（退出登录成功处理）
 *@description 创建MyLogoutSuccessHandler类实现LogoutSuccessHandler接口，用于退出登录的处理
 *@author echoes
 *@version 1.0
 *@create 2024/5/11 14:36
 */

@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(JSON.toJSONString(Result.success().msg("注销成功！")));
    }

}
