package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BRole;
import com.example.integratedHub.entity.Vo.AssginRoleVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.service.BRoleService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-13
 */
@RestController
@RequestMapping("/role")
public class BRoleController {

    @Autowired
    private BRoleService bRoleService;

    @GetMapping("toAssign/{userId}")
    public Result toAssign(@PathVariable String userId) {
        Map<String,Object> roleMap = bRoleService.getRolesByUserId(userId);
        return Result.success().data("data",roleMap);
    }

    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        bRoleService.doAssign(assginRoleVo);
        return Result.success();
    }

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        //创建page对象
        Page<BRole> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BRole> pageModel = bRoleService.selectPage(pageParam,userQueryVo);
        return Result.success().data("data",pageModel);
    }
}

