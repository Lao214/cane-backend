package com.example.integratedHub.entity.securityVo;/*
 *@title LoginUser
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 09:41
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {

    /*
     * 自定义用户对象
     * */
//    private BUser users;
    private String id;

    private String username;

    private String password;

    private String nickname;

    private String realname;

    private String factory;

    private String unit;

    private String avatar;

    private boolean enabled;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private List<String> authorityList;


    public LoginUser(String username, String password, String nickname, List<String> authorityList) {
        this.username = username;
        this.password = password;
        this.authorityList = authorityList;
        this.nickname = nickname;
    }


    /*
     * 权限信息
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /*
     * 密码
     * */
    @Override
    public String getPassword() {
        return this.password;
    }

    /*
     * 用户名
     * */
    @Override
    public String getUsername() {
        return this.username;
    }

    /*
     * 表示判断账户是否过期
     * */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
     * 表示判断账户是否被锁定
     * */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
     * 表示凭证{密码}是否过期
     * */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     * 是否可用
     * */
    @Override
    public boolean isEnabled() {
        return true;
    }
}