package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCaneCategory;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.service.BCaneCategoryService;
import com.example.integratedHub.utils.CategoryHelper;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 甘蔗分类表 前端控制器
 * </p>
 *
 * @author 劳威锟
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
    public Result getTypeTree(NewQueryVo newQueryVo, HttpServletRequest request) {
        QueryWrapper<BCaneCategory> bCourseTypeQueryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(newQueryVo.getKeyword())) {
            bCourseTypeQueryWrapper.like("name",newQueryVo.getKeyword());
        }
        bCourseTypeQueryWrapper.select("id,name,parent_id,create_time,type_level,name as label,name as value");
        List<BCaneCategory> list = bCaneCategoryService.list(bCourseTypeQueryWrapper);
        List<BCaneCategory> TypeTree = CategoryHelper.bulidTree(list);
        return Result.success().data("data",TypeTree);
    }

//    @ApiOperation("addCourseType")
    @PostMapping("addCategory")
    public Result addCategory(@RequestBody BCaneCategory bCaneCategory, HttpServletRequest request) {
        //创建page对象
        bCaneCategory.setCreateTime(new Date());
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
        //创建page对象
        bCaneCategory.setCreateTime(new Date());
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

