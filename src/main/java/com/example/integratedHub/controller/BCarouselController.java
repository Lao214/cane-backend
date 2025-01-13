package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BCarousel;
import com.example.integratedHub.entity.BContact;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.enumVo.BannerStatusEnum;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BCarouselService;
import com.example.integratedHub.service.BUserService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.RedisService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 公告走马灯 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-06
 */
@RestController
@RequestMapping("/carousel")
public class BCarouselController {


    @Autowired
    RedisService redisService;

    @Autowired
    BUserService bUserService;

    @Autowired
    BCarouselService bCarouselService;


    @PostMapping("/addCarousel")
//    @Transactional(rollbackFor = Exception.class)
    public Result addContact(@RequestBody BCarousel bCarousel, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);
        bCarousel.setCreateTime(new Date());
        bCarousel.setCreator(username);
        bCarousel.setCreatorName(loginUser.getNickname());
        boolean save = bCarouselService.save(bCarousel);
        if(save) {
            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @PostMapping("/updateCarousel")
//    @Transactional(rollbackFor = Exception.class)
    public Result updateCarousel(@RequestBody BCarousel bCarousel, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        boolean update = bCarouselService.updateById(bCarousel);
        if(update) {
            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @GetMapping("getCarousel/{id}")
    public Result getCarousel(@PathVariable String id, HttpServletRequest request) {
        QueryWrapper<BCarousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        BCarousel one = bCarouselService.getOne(queryWrapper);
        return Result.success().data("one",one);
    }

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, NewQueryVo newQueryVo) {
        //创建page对象
        Page<BCarousel> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BCarousel> pageModel = bCarouselService.selectPage(pageParam,newQueryVo);
        QueryWrapper<BCarousel> queryWrapper = new QueryWrapper<>();
        int count = bCarouselService.count(queryWrapper);
        return Result.success().data("data",pageModel).data("count",count);
    }

    @GetMapping("getCarouselList")
    public Result getCarouselList() {
        QueryWrapper<BCarousel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("carousel_status", BannerStatusEnum.PUBLISHED.getStatus());
        List<BCarousel> list = bCarouselService.list(queryWrapper);
        return Result.success().data("data",list);
    }

}

