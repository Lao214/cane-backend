package com.example.integratedHub.auth.permissionHandler;/*
 *@title AndPerHandlers
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 15:20
 */

import com.example.integratedHub.entity.securityVo.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("AndPerHandlers")
public class AndPerHandlers {
    /*
     * 自定义权限校验
     *
     * 当同满足两个权限
     * authorities1 第一个权限
     * authorities2 第二个权限
     * */
    public boolean hasAnyAuthority(String authorities1, String authorities2) {
        // 获取权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> authorityList = loginUser.getAuthorityList();

        // 权限校验
        boolean contains = authorityList.contains(authorities1);
        boolean contains1 = authorityList.contains(authorities2);
        // 两个权限不一样，并且用户存在这两个权限
        return !authorities1.equals(authorities2) && contains && contains1;
    }

    /**
     * 用法： @PreAuthorize("@AndPerHandlers.hasAnyAuthority('ADD_USER','DELETE_USER')")
     */

}
