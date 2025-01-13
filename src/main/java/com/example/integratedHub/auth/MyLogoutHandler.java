package com.example.integratedHub.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *@title MyLogoutSuccessHandler（退出登录成功处理）
 *@description 创建MyLogoutSuccessHandler类实现LogoutSuccessHandler接口，用于退出登录的处理
 *@author echoes
 *@version 1.0
 *@create 2024/5/11 14:36
 */

@Slf4j
public class MyLogoutHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 在用户注销时执行的操作，比如记录日志、清理缓存等
        // 这里只是一个示例，你可以根据需求来实现具体的逻辑
        System.out.println("User " + authentication.getName() + " is logging out.");

        // 清理用户相关的数据等操作

    }

}
