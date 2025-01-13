package com.example.integratedHub.filter;/*
 *@title JwtSecurityFilter
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 10:45
 */

import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 校验token是否失效，如果失效，直接放行.后面还有其他过滤链条会进行拦截
        if (!JwtUtil.checkToken(request.getHeader("inhub-token"))) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(request);
        String userId = memberIdByJwtToken.get("username");
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(userId);
        // 校验用户信息
        if (Objects.isNull(loginUser)) {
            throw new UsernameNotFoundException("用户未登录");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        loginUser.getAuthorityList().forEach(authority -> grantedAuthorities.add(new SimpleGrantedAuthority(authority)));
        // 存入 SecurityContextHolder   参数一：用户信息   参数二：  参数三：权限认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
