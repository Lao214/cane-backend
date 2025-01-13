package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourFile;
import com.example.integratedHub.entity.BResourFileDownload;
import com.example.integratedHub.service.BResourFileDownloadService;
import com.example.integratedHub.service.BResourFileService;
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
 * 资源附件下载记录表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-15
 */
@RestController
@RequestMapping("/resourFileDownload")
public class BResourFileDownloadController {

    @Autowired
    private BResourFileDownloadService bResourFileDownloadService;

    @Autowired
    private BResourFileService bResourFileService;

    @PostMapping("/downloadRecord")
    @Transactional
    public Result downloadRecord(@RequestBody BResourFileDownload bResourFileDownload, HttpServletRequest request) {
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
            bResourFileDownload.setUsername(username);
        } else {
            bResourFileDownload.setUsername("游客" + ipAddress);
        }
        bResourFileDownload.setCreateTime(new Date());
        boolean save = bResourFileDownloadService.save(bResourFileDownload);
        if(save) {
            // 直接在数据库中执行 viewCount 的递增操作
            UpdateWrapper<BResourFile> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", bResourFileDownload.getResourFileId())
                    .setSql("download_count = download_count + 1");
            boolean updateResult = bResourFileService.update(updateWrapper);
            if (updateResult) {
                return Result.success().msg("记录成功");
            } else {
                return Result.success().msg("点赞失败");
            }
        } else  {
            return Result.success();
        }
    }

    @PostMapping("/downloadRecordAll")
    @Transactional
    public Result downloadRecordAll(@RequestBody BResourFileDownload bResourFileDownload, HttpServletRequest request) {
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
            bResourFileDownload.setUsername(username);
        } else {
            bResourFileDownload.setUsername("游客" + ipAddress);
        }
        bResourFileDownload.setCreateTime(new Date());
        bResourFileDownload.setResourFileId(0l);
        boolean save = bResourFileDownloadService.save(bResourFileDownload);
        if(save) {
            // 直接在数据库中执行 viewCount 的递增操作
            UpdateWrapper<BResourFile> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("resour_key", bResourFileDownload.getResourKey()).eq("resour_file_type","附件")
                    .setSql("download_count = download_count + 1");
            boolean updateResult = bResourFileService.update(updateWrapper);
            if (updateResult) {
                return Result.success().msg("记录成功");
            } else {
                return Result.success().msg("点赞失败");
            }
        } else  {
            return Result.success();
        }

    }
}

