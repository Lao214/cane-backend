package com.example.integratedHub.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourStar;
import com.example.integratedHub.entity.BResourStarFolder;
import com.example.integratedHub.entity.BResourView;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.service.BResourService;
import com.example.integratedHub.service.BResourStarFolderService;
import com.example.integratedHub.service.BResourStarService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源收藏文件夹表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/resourStarFolder")
public class BResourStarFolderController {

    @Autowired
    private BResourStarFolderService bResourStarFolderService;

    @Autowired
    private BResourStarService bResourStarService;

    @Autowired
    private BResourService bResourService;


    /**
     * @description 获取我的收藏夹
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/8/14 19:45
     */
    @GetMapping("getMyStarFolder")
    public Result getMyStarFolder(HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        QueryWrapper<BResourStarFolder> bResourStarFolderQueryWrapper = new QueryWrapper<>();
        bResourStarFolderQueryWrapper.eq("username",username);
        List<BResourStarFolder> myStarFolderList = bResourStarFolderService.list(bResourStarFolderQueryWrapper);

        return Result.success().data("myStarFolderList", myStarFolderList);
    }

    /**
     * @description 我新增一个收藏夹
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/8/14 19:45
     */
    @PostMapping("addStarFolder")
    public Result addStarFolder(@RequestBody BResourStarFolder bResourStarFolder, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        cn.hutool.core.lang.UUID uuid = UUID.randomUUID();
        // 转换为字符串并移除所有的连字符
        String uuidWithoutHyphens = uuid.toString().replace("-", "");
        bResourStarFolder.setStarFolderKey(uuidWithoutHyphens);

        bResourStarFolder.setUsername(username);
        bResourStarFolder.setCreateTime(new Date());
        bResourStarFolder.setIsPublic("private");
        bResourStarFolder.setStarCount(0);
        boolean save = bResourStarFolderService.save(bResourStarFolder);
        if(save) {
            return Result.success().data("one",bResourStarFolder);
        } else  {
            return Result.error().msg("添加失败");
        }
    }

    @PostMapping("updateStarFolder")
    public Result updateStarFolder(@RequestBody BResourStarFolder bResourStarFolder, HttpServletRequest request) {
        // 从请求头获取 token 并解析出用户名
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        boolean save = bResourStarFolderService.updateById(bResourStarFolder);
        if(save) {
            return Result.success().data("one",bResourStarFolder);
        } else  {
            return Result.error().msg("修改失败");
        }
    }

    @DeleteMapping("removeStarFolder/{starFolderKey}")
    @Transactional
    public Result removeStarFolder(@PathVariable String starFolderKey, HttpServletRequest request) {
        QueryWrapper<BResourStarFolder> bResourStarFolderQueryWrapper = new QueryWrapper<>();
        bResourStarFolderQueryWrapper.eq("star_folder_key",starFolderKey);
        boolean remove = bResourStarFolderService.remove(bResourStarFolderQueryWrapper);
        if(remove) {
            QueryWrapper<BResourStar> bResourStarQueryWrapper = new QueryWrapper<>();
            bResourStarQueryWrapper.eq("star_folder_key", starFolderKey);
            List<BResourStar> list = bResourStarService.list(bResourStarQueryWrapper);
            List<String> keyList = new ArrayList<>();
            for (BResourStar bResourStar : list) {
               keyList.add(bResourStar.getStarResourKey());
            }
            // 批量减少资源的收藏数量
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("resour_key", keyList)
                    .setSql("star_count = star_count - 1");
            bResourService.update(updateWrapper);

            bResourStarService.remove(bResourStarQueryWrapper);

            return Result.success();
        } else  {
            return Result.error().msg("移除失败");
        }
    }


}

