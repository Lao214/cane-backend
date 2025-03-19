package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneSensitivity;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BCaneSensitivityService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 敏感性指标数据库 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2025-03-19
 */
@RestController
@RequestMapping("/caneSensitivity")
public class BCaneSensitivityController {

    @Autowired
    private BCaneSensitivityService bCaneSensitivityService;

    @PostMapping("/addCane")
    public Result addCane(@RequestBody BCaneSensitivity bCaneSensitivity, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date =  new Date();
        bCaneSensitivity.setCreateTime(date);
        boolean save = bCaneSensitivityService.save(bCaneSensitivity);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @PostMapping("/addCaneBatch")
    public Result addCaneBatch(@RequestBody List<BCaneSensitivity> bCaneList, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date =  new Date();
        for (BCaneSensitivity bCane :bCaneList) {
            bCane.setCreateTime(date);
        }
        boolean save = bCaneSensitivityService.saveBatch(bCaneList);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @PostMapping("/updateCane")
    public Result updateCane(@RequestBody BCaneSensitivity bCaneSensitivity, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        boolean save = bCaneSensitivityService.updateById(bCaneSensitivity);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("修改失败");
        }
    }

    @PostMapping("viewCane")
    public Result viewCane(@RequestBody BCaneSensitivity bCaneSensitivity) {
        // 假设 bCane 至少包含 id 字段，用于识别要更新的记录
        if (bCaneSensitivity.getId() == null) {
            return Result.error().msg("ID不能为空");
        }

        // 从数据库中获取现有的记录
        BCaneSensitivity existingBCane = bCaneSensitivityService.getById(bCaneSensitivity.getId());
        if (existingBCane == null) {
            return Result.error().msg("记录不存在");
        }

        // 对 view_count 进行加一操作
        existingBCane.setViewCount(existingBCane.getViewCount() + 1);

        // 更新数据库中的记录
        boolean save = bCaneSensitivityService.updateById(existingBCane);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("更新失败");
        }
    }

    @GetMapping("getCaneById/{id}")
    public Result getCaneById(@PathVariable Integer id, HttpServletRequest request) {
        BCaneSensitivity bCane = bCaneSensitivityService.getById(id);
        return Result.success().data("data",bCane);
    }

    @DeleteMapping("delCane/{id}")
    public Result removeCategoryById(@PathVariable Integer id, HttpServletRequest request) {
        // 获取请求头token字符串
        boolean b = bCaneSensitivityService.removeById(id);
        if(b) {
            return Result.success();
        } else  {
            return Result.error().msg("删除失败");
        }
    }

    @GetMapping("getCane/{page}/{limit}")
    public Result getCane(@PathVariable Long page, @PathVariable Long limit, BCaneSensitivity bCane, HttpServletRequest request) {
        // 创建page对象
        Page<BCaneSensitivity> pageParam = new Page<>(page,limit);
        // 获取请求头token字符串
        // String token = request.getHeader("token");

        // 从token字符串获取用户名称（id）
        // String username = JwtHelper.getUsername(token);
        // 调用service方法
        IPage<BCaneSensitivity> pageModel = bCaneSensitivityService.selectPage(pageParam,bCane);
        return Result.success().data("data",pageModel);
    }

}

