package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneQa;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BCaneQaService;
import com.example.integratedHub.service.BUserService;
import com.example.integratedHub.utils.IpUtil;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 专家答疑问答表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@RestController
@RequestMapping("/qa")
public class BCaneQaController {

    @Autowired
    private BCaneQaService bCaneQaService;

    @Autowired
    private BUserService bUserService;

    @GetMapping("getCaneQa/{page}/{limit}")
    public Result getCaneQa(@PathVariable Long page, @PathVariable Long limit, BCaneQa bCaneQa, HttpServletRequest request) {
        //创建page对象
        Page<BCaneQa> pageParam = new Page<>(page,limit);
        //获取请求头token字符串
//        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        //String username = JwtHelper.getUsername(token);
        //调用service方法
        IPage<BCaneQa> pageModel = bCaneQaService.selectPage(pageParam,bCaneQa);
        return Result.success().data("data",pageModel);
    }

    @PostMapping("/addCaneQa")
    public Result addCaneQa(@RequestBody BCaneQa bCaneQa, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            String ipAddr = IpUtil.getIpAddr(request);
            Date date =  new Date();
            bCaneQa.setAskUser("游客" + ipAddr);
            bCaneQa.setAskNickname("游客" + ipAddr);
            bCaneQa.setAskTime(date);
            bCaneQa.setAskAvatar("http://10.134.149.222:9000/hfe/默认头像-1.png");
            boolean save = bCaneQaService.save(bCaneQa);
            if(save) {
                return Result.success();
            } else  {
                return Result.error().msg("添加失败");
            }
        } else {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            String username = memberIdByJwtToken.get("username");
            Date date =  new Date();
            bCaneQa.setAskUser(username);
            QueryWrapper<BUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",username);
            BUser one = bUserService.getOne(queryWrapper);
            bCaneQa.setAskNickname(one.getNickname());
            bCaneQa.setAskTime(date);
            bCaneQa.setAskAvatar(one.getAvatar());
            boolean save = bCaneQaService.save(bCaneQa);
            if(save) {
                return Result.success();
            } else  {
                return Result.error().msg("添加失败");
            }
        }
    }

    @PostMapping("/updateCaneQa")
    public Result updateCaneQa(@RequestBody BCaneQa bCaneQa, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date =  new Date();
        bCaneQa.setAnswerUser(username);
        QueryWrapper<BUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        BUser one = bUserService.getOne(queryWrapper);
        bCaneQa.setAnswerNickname(one.getNickname());
        bCaneQa.setAnswerTime(date);
        bCaneQa.setIsAnswered("是");
        bCaneQa.setAnswerAvatar(one.getAvatar());
        boolean save = bCaneQaService.save(bCaneQa);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @GetMapping("getCaneQaById/{id}")
    public Result getCaneQaById(@PathVariable Integer id, HttpServletRequest request) {
        BCaneQa bCaneQa = bCaneQaService.getById(id);
        return Result.success().data("data",bCaneQa);
    }

}

