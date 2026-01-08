package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BWumingBase;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BCaneService;
import com.example.integratedHub.service.BWumingBaseService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 苏运浩
 * @since 2025-04-14
 */
@RestController
@RequestMapping("/wumingBase")
public class BWumingBaseController {

    @Autowired
    private BWumingBaseService bWumingBaseService;

    @GetMapping("getPageData/{page}/{limit}")
    public Result getData(@PathVariable Long page, @PathVariable Long limit, BWumingBase bWumingBase, HttpServletRequest request) {
        //创建page对象
        Page<BWumingBase> pageParam = new Page<>(page,limit);
        //获取请求头token字符串
//        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        //String username = JwtHelper.getUsername(token);
        //调用service方法
        IPage<BWumingBase> pageModel = bWumingBaseService.selectPage(pageParam,bWumingBase);
        return Result.success().data("data",pageModel);
    }

    @PostMapping("/addData")
    public Result addData(@RequestBody BWumingBase bWumingBase, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");

        boolean save = bWumingBaseService.save(bWumingBase);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @PostMapping("/updateData")
    public Result updateData(@RequestBody BWumingBase bWumingBase, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date = new Date();
        boolean save = bWumingBaseService.updateById(bWumingBase);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("修改失败");
        }
    }

    @GetMapping("getDataById/{id}")
    public Result getDataById(@PathVariable Integer id, HttpServletRequest request) {
        BWumingBase bWumingBase = bWumingBaseService.getById(id);
        return Result.success().data("data",bWumingBase);
    }

    @DeleteMapping("delData/{id}")
    public Result delData(@PathVariable Integer id, HttpServletRequest request) {
        // 获取请求头token字符串
        boolean b = bWumingBaseService.removeById(id);
        if(b) {
            return Result.success();
        } else  {
            return Result.error().msg("删除失败");
        }
    }

}

