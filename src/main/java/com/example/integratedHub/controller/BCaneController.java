package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCane;
import com.example.integratedHub.entity.BCaneCategory;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BCaneCategoryService;
import com.example.integratedHub.service.BCaneService;
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
 * 甘蔗表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2025-01-14
 */
@RestController
@RequestMapping("/cane")
public class BCaneController {

    @Autowired
    private BCaneService bCaneService;


    @Autowired
    private BCaneCategoryService bCaneCategoryService;


    @PostMapping("/addCane")
    public Result addCane(@RequestBody BCane bCane, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date =  new Date();
        bCane.setCreateBy(username);
        bCane.setUpdateBy(username);
        bCane.setCreateTime(date);
        bCane.setUpdateTime(date);
        boolean save = bCaneService.save(bCane);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @PostMapping("/updateCane")
    public Result updateCane(@RequestBody BCane bCane, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        Date date = new Date();
        bCane.setUpdateBy(username);
        bCane.setUpdateTime(date);
        boolean save = bCaneService.updateById(bCane);
        if(save) {
            return Result.success();
        } else  {
            return Result.error().msg("修改失败");
        }
    }

    @GetMapping("getCane/{page}/{limit}")
    public Result getCane(@PathVariable Long page, @PathVariable Long limit, BCane bCane, HttpServletRequest request) {
        //创建page对象
        Page<BCane> pageParam = new Page<>(page,limit);
        //获取请求头token字符串
//        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        //String username = JwtHelper.getUsername(token);
        //调用service方法
        IPage<BCane> pageModel = bCaneService.selectPage(pageParam,bCane);
        return Result.success().data("data",pageModel);
    }

    @GetMapping("getCaneById/{id}")
    public Result getCaneById(@PathVariable Integer id, HttpServletRequest request) {
        BCane bCane = bCaneService.getById(id);
        return Result.success().data("data",bCane);
    }

    @GetMapping("getCaneByIdWithQinBen/{id}")
    public Result getCaneByIdWithQinBen(@PathVariable Integer id, HttpServletRequest request) {
        BCane bCane = bCaneService.getById(id);
        if(bCane.getParentId() != null && bCane.getParentId() > 0) {
            BCane father = bCaneService.getById(bCane.getParentId());
            bCane.setFatherName(father.getCaneName());
        } else  {
            bCane.setFatherName("无父本");
        }

        if(bCane.getCategoryId() != null && bCane.getCategoryId() > 0) {
            BCaneCategory caneCategory = bCaneCategoryService.getById(bCane.getCategoryId());
            bCane.setCategoryName(caneCategory.getCategoryName());
        }

        if(bCane.getMotherId() != null && bCane.getMotherId() > 0) {
            BCane mother = bCaneService.getById(bCane.getMotherId());
            bCane.setMotherName(mother.getCaneName());
        } else {
            bCane.setMotherName("无母本");
        }

        return Result.success().data("data",bCane);
    }


    @DeleteMapping("delCane/{id}")
    public Result removeCategoryById(@PathVariable Integer id, HttpServletRequest request) {
        // 获取请求头token字符串
        boolean b = bCaneService.removeById(id);
        if(b) {
            return Result.success();
        } else  {
            return Result.error().msg("删除失败");
        }
    }

    @GetMapping("getOptions")
    public Result getOptions(HttpServletRequest request) {
        // 获取请求头token字符串
        QueryWrapper<BCane> bCaneQueryWrapper = new QueryWrapper<>();
        bCaneQueryWrapper.select("id as value","cane_name as label");
        List<BCane> list = bCaneService.list(bCaneQueryWrapper);
        return Result.success().data("data",list);
    }


}

