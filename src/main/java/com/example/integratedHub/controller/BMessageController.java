package com.example.integratedHub.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BComment;
import com.example.integratedHub.entity.BMessage;
import com.example.integratedHub.service.BCommentService;
import com.example.integratedHub.service.BMessageService;
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
 * 消息表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-25
 */
@RestController
@RequestMapping("message")
public class BMessageController {

    @Autowired
    private BMessageService bMessageService;

    @Autowired
    private BCommentService bCommentService;

    @GetMapping("list/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, BMessage bMessage, HttpServletRequest request) {
        //创建page对象
        Page<BMessage> pageParam = new Page<>(page,limit);

        IPage<BMessage> pageModel = bMessageService.selectPage(pageParam,bMessage);
        return Result.success().data("list",pageModel);
    }

    /**
     * 获取我的消息列表
     * @param page
     * @param limit
     * @param bMessage
     * @param request
     * @return
     */
    @GetMapping("myMessageList/{page}/{limit}")
    public Result myMessageList(@PathVariable Long page, @PathVariable Long limit, BMessage bMessage, HttpServletRequest request) {
        //创建page对象
        Page<BMessage> pageParam = new Page<>(page,limit);
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        // 根据我的工号查询我的消息
        bMessage.setReceiver(username);
        //调用service方法
        IPage<BMessage> pageModel = bMessageService.selectPage(pageParam,bMessage);
        return Result.success().data("list",pageModel);
    }


    @GetMapping("getSystemNotifications")
    @Transactional
    public Result getSystemNotifications(HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        QueryWrapper<BMessage> queryWrapper = new QueryWrapper<>();
        // 根据我的工号查询我的消息
        queryWrapper.eq("receiver",username);
        queryWrapper.last("limit 12");
        List<BMessage> list = bMessageService.list(queryWrapper);
        return Result.success().data("list",list);
    }

    /**
     * 获取我的未读消息数
     * @param request
     * @return
     */
    @GetMapping("getMyUnreadMessage")
    public Result myMessageList( HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        QueryWrapper<BMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",username);
        queryWrapper.eq("is_read",0);
        int count = bMessageService.count(queryWrapper);
        return Result.success().data("count",count);
    }

    /**
     * @description 修改消息
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/8/25 15:17
     */
    @PostMapping("updateMessage")
    public Result updateMessage(@RequestBody BMessage bMessage,HttpServletRequest request) {
        boolean is_Success = bMessageService.updateById(bMessage);
        if(is_Success) {
            return Result.success().data("one",bMessage);
        } else {
            return Result.error();
        }
    }


    /**
     * @description 新增消息
     * @author echoes
     * @param[1] null
     * @throws
     * @return
     * @time 2024/8/25 15:17
     */
    @PostMapping("addMessage")
    public Result addMessage(@RequestBody BMessage bMessage,HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        bMessage.setSender(username);
        // 0 表示未读
        bMessage.setIsRead(0);
        bMessage.setCreateTime(new Date());
        boolean is_Success = bMessageService.save(bMessage);
        if(is_Success) {
            return Result.success().data("one",bMessage);
        } else {
            return Result.error();
        }
    }

    @GetMapping("getMyUnread")
    public Result getMyUnread(HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 查出未读通知数量
        QueryWrapper<BMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("receiver",username);
        queryWrapper.eq("is_read",0);
        int count = bMessageService.count(queryWrapper);

        int count2 = 0;
        List<String> ids = bCommentService.getMyCommentIds(username);
        if(ids != null) {
            QueryWrapper<BComment> bCommentQueryWrapper = new QueryWrapper<>();
            bCommentQueryWrapper.ne("username",username);
            bCommentQueryWrapper.eq("is_read",0);
            bCommentQueryWrapper.in("parent_id",ids);
            count2 = bCommentService.count(bCommentQueryWrapper);
        }
        // count 是系统通知的计数，count2是"回复我的"计数
        return Result.success().data("count",count).data("count2",count2);
    }

    @PostMapping("readReply")
    public Result readReply(@RequestBody List<BComment> bComments) {
        boolean up = bCommentService.updateBatchById(bComments);
        if(up) {
            return Result.success();
        } else  {
            return Result.error().msg("更新消息状态失败");
        }
    }

    @PostMapping("readNotification")
    public Result readNotification(@RequestBody List<BMessage> bMessageList) {
        boolean up = bMessageService.updateBatchById(bMessageList);
        if(up) {
            return Result.success();
        } else  {
            return Result.error().msg("更新消息状态失败");
        }
    }


}

