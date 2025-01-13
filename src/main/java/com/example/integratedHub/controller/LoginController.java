package com.example.integratedHub.controller;/*
 *@title LoginController
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 09:57
 */

import com.example.integratedHub.auth.LoginService;
import com.example.integratedHub.entity.BLoginRecord;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.service.BLoginRecordService;
import com.example.integratedHub.utils.IpUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/security")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private BLoginRecordService bLoginRecordService;

    /*
     * 登录接口
     * */
    @PostMapping("/login")
    public Result Login(@RequestBody BUser users, HttpServletRequest request) {
        // 认证通过，返回给前端jjwt
        String jjwtStr = loginService.Login(users);
        String ipAddr = IpUtil.getIpAddr(request);
        if(!ipAddr.equals("0:0:0:0:0:0:0:1")) {
           BLoginRecord bLoginRecord = new BLoginRecord();
           bLoginRecord.setLoginIp(ipAddr);
           bLoginRecord.setUsername(users.getUsername());
           bLoginRecord.setCreateTime(new Date());
           bLoginRecord.setRoute(users.getRoute());
           bLoginRecordService.save(bLoginRecord);
        }
        return Result.success().data("token", jjwtStr);
    }


        /*
     * 退出登录接口与
     * */
    @GetMapping("/logout")
    public Result loginOut() {
        String msg = loginService.loginOut();
        return Result.success().msg(msg);
    }


}
