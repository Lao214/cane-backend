package com.example.integratedHub.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BContact;
import com.example.integratedHub.entity.BNews;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BContactService;
import com.example.integratedHub.service.BNewsService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.RedisService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 联系我们 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-29
 */
@RestController
@RequestMapping("/contact")
public class BContactController {

    @Autowired
    private BContactService bContactService;

    @Autowired
    RedisService redisService;

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, NewQueryVo newQueryVo) {
        //创建page对象
        Page<BContact> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BContact> pageModel = bContactService.selectPage(pageParam,newQueryVo);
        QueryWrapper<BContact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_read","未读");
        int count = bContactService.count(queryWrapper);
        return Result.success().data("data",pageModel).data("count",count);
    }

    @PostMapping("/addContact")
    @Transactional(rollbackFor = Exception.class)
    public Result addContact(@RequestBody BContact bContact, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 获取redis中用户信息
        bContact.setUsername(username);
        bContact.setCreateTime(new Date());
        bContact.setContactTitle("管理员你好！");
        boolean save = bContactService.save(bContact);
        if(save) {
            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @GetMapping("getMsg/{id}")
    public Result getMsg(@PathVariable String id, HttpServletRequest request) {
        QueryWrapper<BContact> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        BContact one = bContactService.getOne(queryWrapper);
        return Result.success().data("one",one);
    }

    @DeleteMapping("/delContact/{id}")
    public Result delContact(@PathVariable String id, HttpServletRequest request) {
       bContactService.removeById(id);
        return Result.success();
    }

    @GetMapping("readed/{id}")
    public Result readed(@PathVariable Long id, HttpServletRequest request) {
        BContact bContact = new BContact();
        bContact.setId(id);
        bContact.setIsRead("已读");
        bContactService.updateById(bContact);
        return Result.success();
    }
}

