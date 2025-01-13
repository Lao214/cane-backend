package com.example.integratedHub.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.*;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.entity.enumVo.RoleEnum;
import com.example.integratedHub.service.BMessageService;
import com.example.integratedHub.service.BNewsService;
import com.example.integratedHub.service.BNewsViewService;
import com.example.integratedHub.service.BUserService;
import com.example.integratedHub.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-05-24
 */
@RestController
@RequestMapping("/news")
public class BNewsController {

    @Autowired
    private BNewsService bNewsService;

    @Autowired
    private BNewsViewService bNewsViewService;

    @Autowired
    private BUserService bUserService;

    @Autowired
    private BMessageService bMessageService;

    @Autowired
    RedisService redisService;

    @Autowired
    MinioUtil minioUtil;

    @Value("${minio.bucketName}")
    private String bucketName;

    @GetMapping("getNew/{id}")
    public Result getNew(@PathVariable String id) {
        BNews news = bNewsService.getById(id);
        return Result.success().data("data",news);
    }

    @GetMapping("getNewByKey/{newKey}")
    public Result getNewByKey(@PathVariable String newKey) {
        QueryWrapper<BNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("new_key",newKey);
        BNews news = bNewsService.getOne(queryWrapper);
        return Result.success().data("data",news);
    }

    @PostMapping("/addNew")
    @Transactional(rollbackFor = Exception.class)
    public Result addNew(@RequestBody BNews bNews, HttpServletRequest request) {
        UUID uuid = UUID.randomUUID();
        // 转换为字符串并移除所有的连字符
        String uuidWithoutHyphens = uuid.toString().replace("-", "");
        bNews.setNewKey(uuidWithoutHyphens);
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        try {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            String username = memberIdByJwtToken.get("username");
            bNews.setCreateUsername(username);
            boolean save = bNewsService.save(bNews);
            if(save) {
                if(bNews.getStatus().equals("已发布") && bNews.getNewType().toString().equals("2")) {
                    // 说明是一个发布的公告
                    QueryWrapper<BUser> bUserQueryWrapper = new QueryWrapper<>();
                    bUserQueryWrapper.select("id,username");
                    List<BUser> list = bUserService.list(bUserQueryWrapper);
                    List<BMessage> bMessages = new ArrayList<>();
                    for (BUser bUser: list) {
                        BMessage bMessage = new BMessage();
                        bMessage.setMsgTitle(bNews.getNewTitle());
                        bMessage.setMsgSub(bNews.getNewSub());
                        // 无需存具体内容
                        //bMessage.setMsgContent(bNews.getNewContent());
                        bMessage.setCreateTime(new Date());
                        bMessage.setConfirmRoute(bNews.getNewKey());
                        bMessage.setSender("system");
                        bMessage.setReceiver(bUser.getUsername());
                        bMessage.setSenderName("系统");
                        bMessage.setMsgType(1);
                        bMessages.add(bMessage);
                    }
                    bMessageService.saveBatch(bMessages);
                }
                return Result.success().msg("添加成功").data("one",bNews);
            } else  {
                return Result.error().msg("添加成功");
            }
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getMostSpecificCause().getMessage();
            if (errorMessage.contains("Duplicate entry '1' for key")) {
                return Result.error().msg("添加失败: 可能为标题重名错误");
            } else if (errorMessage.contains("foreign key constraint")) {
                return Result.error().msg("添加失败: e");
            } else if (errorMessage.contains("not null constraint")) {
                return Result.error().msg("添加失败: e2");
            } else {
                return Result.error().msg("添加失败: e3");
            }
        } catch (Exception e) {
            // 捕捉其他异常
            return Result.error().msg("添加失败: " + e.getMessage());
        }

    }

    @PostMapping("/updateNew")
    @Transactional
    public Result updateNew(@RequestBody BNews bNews) {
        bNews.setUpdateTime(new Date());
        try {
            boolean save = bNewsService.updateById(bNews);
            if(save) {
                if(StringUtils.isNotEmpty(bNews.getStatus())) {
                    if(bNews.getStatus().equals("已发布") && bNews.getNewType().toString().equals("2")) {
                        // 说明是一个发布的公告
                        QueryWrapper<BUser> bUserQueryWrapper = new QueryWrapper<>();
                        bUserQueryWrapper.select("id,username");
                        List<BUser> list = bUserService.list(bUserQueryWrapper);
                        List<BMessage> bMessages = new ArrayList<>();
                        for (BUser bUser: list) {
                            BMessage bMessage = new BMessage();
                            bMessage.setMsgTitle(bNews.getNewTitle());
                            bMessage.setMsgSub(bNews.getNewSub());
                            // 无需存具体内容
                            //bMessage.setMsgContent(bNews.getNewContent());
                            bMessage.setCreateTime(new Date());
                            bMessage.setConfirmRoute(bNews.getNewKey());
                            bMessage.setSender("system");
                            bMessage.setReceiver(bUser.getUsername());
                            bMessage.setSenderName("系统");
                            bMessage.setMsgType(1);
                            bMessages.add(bMessage);
                        }
                        bMessageService.saveBatch(bMessages);
                    }
                }
                return Result.success().msg("修改成功");
            } else  {
                return Result.error().msg("修改成功");
            }
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getMostSpecificCause().getMessage();
            if (errorMessage.contains("Duplicate entry '1' for key")) {
                return Result.error().msg("添加失败: 可能为标题重名错误");
            } else if (errorMessage.contains("foreign key constraint")) {
                return Result.error().msg("添加失败: e");
            } else if (errorMessage.contains("not null constraint")) {
                return Result.error().msg("添加失败: e2");
            } else {
                return Result.error().msg("添加失败: e3");
            }
        } catch (Exception e) {
            // 捕捉其他异常
            return Result.error().msg("添加失败: " + e.getMessage());
        }
    }

    @PostMapping("/viewNew")
    @Transactional(rollbackFor = Exception.class)
    public Result viewNew(@RequestBody BNews bNews, HttpServletRequest request) {
        BNewsView bNewsView = new BNewsView();
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (!StringUtils.isBlank(token)) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            String username = memberIdByJwtToken.get("username");
            bNewsView.setUsername(username);
        }
        boolean save = bNewsService.updateById(bNews);
        if(save) {
            bNewsView.setCreateTime(new Date());
            bNewsView.setNewId(bNews.getId());
            bNewsViewService.save(bNewsView);
            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @GetMapping("getHomeNews")
    public Result getHomeNews() {
        QueryWrapper<BNews> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("new_key,id,new_title,DATE(create_time) as create_time_str");
        queryWrapper.eq("status","已发布");
        queryWrapper.eq("new_type", 0);
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 10");
        List<BNews> list = bNewsService.list(queryWrapper);
        // list 是最新点评，list2是相关资讯
        QueryWrapper<BNews> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.select("new_key,id,new_title,DATE(create_time) as create_time_str");
        queryWrapper2.eq("status","已发布");
        queryWrapper2.eq("new_type", 1);
        queryWrapper2.orderByDesc("id");
        queryWrapper2.last("limit 10");
        List<BNews> list2 = bNewsService.list(queryWrapper2);
        return Result.success().data("list",list).data("list2",list2);
    }

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, NewQueryVo newQueryVo) {
        //创建page对象
        Page<BNews> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BNews> pageModel = bNewsService.selectPage(pageParam,newQueryVo);
        return Result.success().data("data",pageModel);
    }


    @DeleteMapping("removeById/{id}")
    @Transactional
    public Result remove(@PathVariable String id) {
        QueryWrapper<BNews> bNewsQueryWrapper = new QueryWrapper<>();
        bNewsQueryWrapper.eq("id",id);
        BNews one = bNewsService.getOne(bNewsQueryWrapper);
        boolean is_Success = bNewsService.removeById(id);
        if(is_Success) {
            if(one.getNewType().toString().equals("2")) {
                QueryWrapper<BMessage> bMessageQueryWrapper = new QueryWrapper<>();
                bMessageQueryWrapper.eq("msg_title",one.getNewTitle());
                bMessageService.remove(bMessageQueryWrapper);
            }
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/insertImgToNew")
    @Transactional(rollbackFor = Exception.class)
    public Result uploadFileToResour(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
        return Result.success().data("url",bucket01.getMinIoUrl());

    }


    @PostMapping("/removeImgListInNew")
    public Result removeImgListInNew(@RequestBody List<String> fileList, HttpServletRequest request) throws Exception {

        for (String url : fileList) {
            String filePath = url;
            // 使用lastIndexOf()方法找到最后一个斜杠的索引
            int lastIndex = filePath.lastIndexOf("/");
            // 使用substring()方法提取最后一个斜杠后的部分
            String fileName = filePath.substring(lastIndex + 1);
            minioUtil.removeObject(bucketName,fileName);
        }

        return Result.success();

    }


}

