package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneCategory;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BCaneCategoryService;
import com.example.integratedHub.utils.CategoryHelper;
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
 * 甘蔗分类表 前端控制器
 * </p>
 *
 * @author 苏运浩
 * @since 2025-01-14
 */
@RestController
@RequestMapping("/category")
public class BCaneCategoryController {


    @Autowired
    private BCaneCategoryService bCaneCategoryService;

    @GetMapping("getCategory/{page}/{limit}")
    public Result getCategory(@PathVariable Long page, @PathVariable Long limit, NewQueryVo newQueryVo, HttpServletRequest request) {
        //创建page对象
        Page<BCaneCategory> pageParam = new Page<>(page,limit);
        //获取请求头token字符串
//        String token = request.getHeader("token");

        //从token字符串获取用户名称（id）
        //String username = JwtHelper.getUsername(token);
        //调用service方法
        IPage<BCaneCategory> pageModel = bCaneCategoryService.selectPage(pageParam,newQueryVo);
        return Result.success().data("data",pageModel);
    }

//    @ApiOperation("获取分类树")
    @GetMapping("getCategoryTree")
    public Result getCategoryTree(NewQueryVo newQueryVo, HttpServletRequest request) {
        QueryWrapper<BCaneCategory> bCourseTypeQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(newQueryVo.getKeyword())) {
            bCourseTypeQueryWrapper.like("category_name",newQueryVo.getKeyword());
        }
        bCourseTypeQueryWrapper.select("id,parent_id,create_time,category_level,category_name as label,id as value");
        List<BCaneCategory> list = bCaneCategoryService.list(bCourseTypeQueryWrapper);
        List<BCaneCategory> TypeTree = CategoryHelper.bulidTree(list);
        return Result.success().data("data",TypeTree);
    }

//    @GetMapping("getCategoryTreeForSearch")
//    public Result getCategoryTreeForSearch(HttpServletRequest request) {
//        QueryWrapper<BCaneCategory> bCourseTypeQueryWrapper = new QueryWrapper<>();
//        bCourseTypeQueryWrapper.select("id,name,parent_id,create_time,category_level,category_name as label,id as value");
//        List<BCaneCategory> list = bCaneCategoryService.list(bCourseTypeQueryWrapper);
//        List<BCaneCategory> TypeTree = CategoryHelper.bulidTree(list);
//        return Result.success().data("data",TypeTree);
//    }

//    @ApiOperation("addCourseType")
    @PostMapping("addCategory")
    public Result addCategory(@RequestBody BCaneCategory bCaneCategory, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        //创建page对象
        bCaneCategory.setCreateTime(new Date());
        bCaneCategory.setCreateBy(username);
        bCaneCategory.setUpdateBy(username);
        boolean save = bCaneCategoryService.save(bCaneCategory);
        if(save) {
            return Result.success().data("one",bCaneCategory);
        } else  {
            return Result.error().msg("添加失败");
        }
    }

//    @ApiOperation("updateCourseType")
    @PostMapping("updateCategory")
    public Result updateCategory(@RequestBody BCaneCategory bCaneCategory, HttpServletRequest request) {
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        bCaneCategory.setUpdateTime(new Date());
        bCaneCategory.setUpdateBy(username);
        boolean save = bCaneCategoryService.updateById(bCaneCategory);
        if(save) {
            return Result.success().data("one",bCaneCategory);
        } else  {
            return Result.error().msg("修改失败");
        }
    }

    @DeleteMapping("removeCategoryById/{id}")
    public Result removeCategoryById(@PathVariable Integer id, HttpServletRequest request) {
        // 获取请求头token字符串
        boolean b = bCaneCategoryService.removeById(id);
        if(b) {
            return Result.success();
        } else  {
            return Result.error().msg("删除失败");
        }
    }

}

