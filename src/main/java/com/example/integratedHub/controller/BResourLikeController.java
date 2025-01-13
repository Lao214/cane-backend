package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.integratedHub.entity.*;
import com.example.integratedHub.service.BResourLikeService;
import com.example.integratedHub.service.BResourService;

import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/resourLike")
public class BResourLikeController {

    @Autowired
    private BResourService bResourService;

    @Autowired
    private BResourLikeService bResourLikeService;


    @GetMapping("checkIsLike/{resourKey}")
    public Result checkIsStar(@PathVariable String resourKey, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.success().data("isLike", false);
        }

        String username = JwtUtil.getMemberIdByJwtToken(token).get("username");

        // 如果解析出的用户名为空，直接返回未标星
        if (username == null || username.isEmpty()) {
            return Result.success().data("isLike", false);
        }

        // 检查是否已标星
        BResourLike one = bResourLikeService.getOne(new QueryWrapper<BResourLike>().eq("resour_key", resourKey).eq("username", username));

        if(one != null) {
            return Result.success().data("isLike", true);
        } else  {
            return Result.success().data("isLike", false);
        }
    }

    @PostMapping("addResourLike")
    @Transactional(rollbackFor = Exception.class)
    public Result addUser(@RequestBody BResourLike bResourLike, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        bResourLike.setCreateTime(new Date());
        bResourLike.setUsername(username);
        // 保存资源浏览记录
        boolean save = bResourLikeService.save(bResourLike);
        if (save) {
            // 直接在数据库中执行 viewCount 的递增操作
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", bResourLike.getResourKey())
                    .setSql("like_count = like_count + 1");
            boolean updateResult = bResourService.update(updateWrapper);
            if (updateResult) {
                return Result.success().msg("点赞成功");
            } else {
                return Result.error().msg("点赞失败");
            }
        } else {
            return Result.error().msg("点赞失败");
        }
    }


    @DeleteMapping("cancelLike/{resourKey}")
    @Transactional
    public Result cancelStar(@PathVariable String resourKey, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        QueryWrapper<BResourLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("resour_key",resourKey);
        boolean remove = bResourLikeService.remove(queryWrapper);
        if(remove) {
            // 直接在数据库中执行 BResour 的递增操作
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", resourKey)
                    .setSql("like_count = like_count - 1");
            bResourService.update(updateWrapper);

            return Result.success();
        } else  {
            return Result.error().msg("取消点赞失败");
        }
    }


}

