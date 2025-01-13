package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourStar;
import com.example.integratedHub.entity.BResourStarFolder;
import com.example.integratedHub.service.BResourService;
import com.example.integratedHub.service.BResourStarFolderService;
import com.example.integratedHub.service.BResourStarService;

import com.example.integratedHub.utils.JwtUtil;
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
 * 收藏夹 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/resourStar")
public class BResourStarController {

    @Autowired
    private BResourStarService bResourStarService;

    @Autowired
    private BResourService bResourService;

    @Autowired
    private BResourStarFolderService bResourStarFolderService;

    @GetMapping("getStarByKey/{starFolderKey}/{page}/{limit}")
    public Result getStarByKey(@PathVariable String starFolderKey,@PathVariable Long page, @PathVariable Long limit, HttpServletRequest request) {
        // 创建page对象
        Page<BResourStar> pageParam = new Page<>(page,limit);
        IPage<BResourStar> list = bResourStarService.getStarByKey(pageParam,starFolderKey);
        return Result.success().data("list", list);
    }

    @GetMapping("checkIsStar/{resourKey}")
    public Result checkIsStar(@PathVariable String resourKey, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.success().data("isStar", false);
        }

        String username = JwtUtil.getMemberIdByJwtToken(token).get("username");

        // 如果解析出的用户名为空，直接返回未标星
        if (username == null || username.isEmpty()) {
            return Result.success().data("isStar", false);
        }

        // 检查是否已标星
        BResourStar one = bResourStarService.getOne(new QueryWrapper<BResourStar>().eq("star_resour_key", resourKey).eq("username", username));

        if(one != null) {
            return Result.success().data("isStar", true).data("starFolderKey", one.getStarFolderKey());
        } else  {
            return Result.success().data("isStar", false);
        }
    }

    @PostMapping("star")
    @Transactional
    public Result star(@RequestBody BResourStar bResourStar, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        bResourStar.setUsername(username);
        bResourStar.setCreateTime(new Date());
        boolean save = bResourStarService.save(bResourStar);
        if(save) {
            // 直接在数据库中执行 BResour 的递增操作
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", bResourStar.getStarResourKey())
                    .setSql("star_count = star_count + 1");
            bResourService.update(updateWrapper);

            // 直接在数据库中执行 BResourStarFolder 的递增操作
            UpdateWrapper<BResourStarFolder> bResourStarFolderUpdateWrapper = new UpdateWrapper<>();
            bResourStarFolderUpdateWrapper.eq("star_folder_key", bResourStar.getStarFolderKey())
                    .setSql("star_count = star_count + 1");
            bResourStarFolderService.update(bResourStarFolderUpdateWrapper);

            return Result.success().data("one",bResourStar);
        } else  {
            return Result.error().msg("收藏失败");
        }
    }

    @DeleteMapping("cancelStar/{resourKey}/{starFolderKey}")
    @Transactional
    public Result cancelStar(@PathVariable String resourKey,@PathVariable String starFolderKey, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        QueryWrapper<BResourStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("star_resour_key",resourKey);
        boolean remove = bResourStarService.remove(queryWrapper);
        if(remove) {

            // 直接在数据库中执行 BResour 的递增操作
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", resourKey)
                    .setSql("star_count = star_count - 1");
            bResourService.update(updateWrapper);

            // 直接在数据库中执行 BResourStarFolder 的递增操作
            UpdateWrapper<BResourStarFolder> bResourStarFolderUpdateWrapper = new UpdateWrapper<>();
            bResourStarFolderUpdateWrapper.eq("star_folder_key", starFolderKey)
                    .setSql("star_count = star_count - 1");
            bResourStarFolderService.update(bResourStarFolderUpdateWrapper);

            return Result.success();
        } else  {
            return Result.error().msg("取消收藏失败");
        }
    }

}

