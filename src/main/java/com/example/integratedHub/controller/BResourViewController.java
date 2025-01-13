package com.example.integratedHub.controller;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourView;
import com.example.integratedHub.service.BResourService;
import com.example.integratedHub.service.BResourViewService;
import com.example.integratedHub.utils.IpUtil;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 资源查询 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-14
 */
@RestController
@RequestMapping("/resourView")
public class BResourViewController {

    @Autowired
    private BResourService bResourService;

    @Autowired
    private BResourViewService bResourViewService;



    @PostMapping("addResourView")
    @Transactional(rollbackFor = Exception.class)
    public Result addUser(@RequestBody BResourView bResourView, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        //获取IP地址
        String ipAddress = IpUtil.getIpAddr(request);
        if(ipAddress.equals("0:0:0:0:0:0:0:1")) {
            return Result.success();
        }
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
            bResourView.setUsername(username);
        } else {
            bResourView.setUsername("游客" + ipAddress);
        }
        bResourView.setCreateTime(new Date());
        // 保存资源浏览记录
        boolean save = bResourViewService.save(bResourView);
        if (save) {
            // 直接在数据库中执行 viewCount 的递增操作
            UpdateWrapper<BResour> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", bResourView.getResourKey())
                    .setSql("view_count = view_count + 1");
            boolean updateResult = bResourService.update(updateWrapper);
            if (updateResult) {
                return Result.success().msg("浏览成功");
            } else {
                return Result.error().msg("更新浏览次数失败");
            }
        } else {
            return Result.error().msg("保存浏览记录失败");
        }
    }

}

