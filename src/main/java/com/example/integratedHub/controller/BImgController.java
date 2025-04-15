package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneQa;
import com.example.integratedHub.entity.BImg;
import com.example.integratedHub.service.BImgService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/img")
public class BImgController {


    @Autowired
    private BImgService bImgService;

    @GetMapping("getImgList/{page}/{limit}")
    public Result getImgList(@PathVariable Long page, @PathVariable Long limit, BImg bImg, HttpServletRequest request) {
        //创建page对象
        Page<BImg> pageParam = new Page<>(page,limit);
        //获取请求头token字符串
        //String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        //String username = JwtHelper.getUsername(token);
        //调用service方法
        IPage<BImg> pageModel = bImgService.selectPage(pageParam,bImg);
        return Result.success().data("data",pageModel);
    }
}

