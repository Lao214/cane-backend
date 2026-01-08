package com.example.integratedHub.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BUser;
import com.example.integratedHub.entity.BUserRole;
import com.example.integratedHub.entity.Vo.ChangePassVo;
import com.example.integratedHub.entity.Vo.UserQueryVo;
import com.example.integratedHub.entity.enumVo.ErrorCode;
import com.example.integratedHub.entity.enumVo.RoleEnum;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BUserRoleService;
import com.example.integratedHub.service.BUserService;
import com.example.integratedHub.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 苏运浩
 * @since 2023-02-18
 */
@RestController
    @RequestMapping("/user")
public class BUserController {

//    @Autowired
//    DbUserDetailsManager dbUserDetailsManager;

    @Autowired
    BUserService bUserService;

    @Autowired
    BUserRoleService bUserRoleService;

    @Autowired
    RedisService redisService;


    @Autowired
    MinioUtil minioUtil;

    @Value("${minio.bucketName}")
    private String bucketName;


    @PostMapping("/changeAvatar")
    @Transactional(rollbackFor = Exception.class)
    public Result uploadFileToResour(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        String userId = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
            userId = memberIdByJwtToken.get("userId");
        }
        LoginUser loginUser = redisService.getCacheObject(username);
        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
        BUser buser = new BUser();
        buser.setId(Long.valueOf(userId));
        buser.setAvatar(bucket01.getMinIoUrl());
        // 先删后改
        BUser byId = bUserService.getById(userId);
        String oldAvatar = byId.getAvatar();
        if(!oldAvatar.equals("http://43.136.93.160:9000/hfe/默认头像-1.png")) {
            // 使用lastIndexOf()方法找到最后一个斜杠的索引
            int lastIndex = oldAvatar.lastIndexOf("/");
            // 使用substring()方法提取最后一个斜杠后的部分
            String fileNameDel = oldAvatar.substring(lastIndex + 1);
            minioUtil.removeObject(bucketName,fileNameDel);
        }
        boolean update = bUserService.updateById(buser);
        if(update) {
            loginUser.setAvatar(buser.getAvatar());
            // 将用户信息修改覆盖之前的用户信息，用户username作为键
            redisService.setCacheObject(loginUser.getUsername(), loginUser);
            return Result.success().data("data",buser);
        } else  {
            return Result.error().msg("上传失败，请稍后再试");
        }
    }


    @GetMapping("getUser/{id}")
    public Result getUser(@PathVariable String id) {
        BUser user = bUserService.getById(id);
        return Result.success().data("data",user);
    }

    /**
     * @description:  获取用户信息
     * @param:
     * @return:
     * @author 苏运浩
     * @date: 2023/7/31 2:31 PM
     */
//    @PreAuthorize("hasAnyAuthority('ADD_K')")
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);
        if (Objects.isNull(loginUser)) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        // 根据token获取ID
        try {
            loginUser.setPassword("");
            return Result.success().data("one",loginUser);
        }catch (Exception e) {
            return Result.error().code(ErrorCode.TOKEN_VALID.getCode()).msg(ErrorCode.TOKEN_VALID.getMsg());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADD_USER')")
    @PostMapping("/addUser")
    @Transactional(rollbackFor = Exception.class)
    public Result addUser(@RequestBody BUser bUser) {
        // 创建密码解析器
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        // 对密码进行加密
        String bpePassword = bCryptPasswordEncoder.encode(bUser.getPassword());
        bUser.setPassword(bpePassword);
        bUser.setAvatar("http://43.136.93.160:9000/hfe/默认头像-1.png");
        boolean save = bUserService.save(bUser);
        if(save) {
            BUserRole bUserRole = new BUserRole();
            bUserRole.setUserId(bUser.getId());
            bUserRole.setRoleId(RoleEnum.GENERAL_USER.getRoleId());
            bUserRoleService.save(bUserRole);

//            BResourStarFolder bResourStarFolder = new BResourStarFolder();
//            bResourStarFolder.setUsername(bUser.getUsername());
//            bResourStarFolder.setStarFolderName("默认收藏夹");
            cn.hutool.core.lang.UUID uuid = UUID.randomUUID();
            // 转换为字符串并移除所有的连字符
            String uuidWithoutHyphens = uuid.toString().replace("-", "");
//            bResourStarFolder.setStarFolderKey(uuidWithoutHyphens);
//            bResourStarFolder.setCreateTime(new Date());
//            bResourStarFolder.setIsPublic("private");
//            bResourStarFolderService.save(bResourStarFolder);

            return Result.success().msg("添加成功");
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @PostMapping("update")
    public Result update(@RequestBody BUser user) {
        boolean is_Success = bUserService.updateById(user);
        if(is_Success) {
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("updateMyself")
    public Result updateMyself(@RequestBody BUser user,HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);
        if(!user.getId().toString().equals(loginUser.getId())) {
            return Result.error().msg("令牌信息错误，请重新登录");
        }
        boolean is_Success = bUserService.updateById(user);
        if(is_Success) {
            loginUser.setNickname(user.getNickname());
            loginUser.setUnit(user.getUnit());
            loginUser.setFactory(user.getFactory());
            // 将用户信息修改覆盖之前的用户信息，用户username作为键
            redisService.setCacheObject(loginUser.getUsername(), loginUser);
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("/updateInCenter")
    public Result updateUser(@RequestBody BUser bUser,HttpServletRequest request) {
        // 创建密码解析器
        if(StringUtils.isNotBlank(bUser.getNewPassword())) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            // 对密码进行加密
            String bpePassword = bCryptPasswordEncoder.encode(bUser.getNewPassword());
            bUser.setPassword(bpePassword);
        }
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
//        String username = memberIdByJwtToken.get("username");
        Long userId = Long.valueOf(memberIdByJwtToken.get("userId"));
        bUser.setId(userId);
        bUserService.updateById(bUser);
        return Result.success();
    }


    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, UserQueryVo userQueryVo) {
        //创建page对象
        Page<BUser> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BUser> pageModel = bUserService.selectPage(pageParam,userQueryVo);
        return Result.success().data("data",pageModel);
    }

    @DeleteMapping("remove/{id}")
    @Transactional
    public Result remove(@PathVariable String id) {
        boolean is_Success = bUserService.removeById(id);
        if(is_Success) {
            QueryWrapper<BUserRole> bUserRoleQueryWrapper = new QueryWrapper<>();
            bUserRoleQueryWrapper.eq("user_id",id);
            bUserRoleService.remove(bUserRoleQueryWrapper);
            return Result.success();
        } else {
            return Result.error();
        }
    }

    @PostMapping("changePassword")
    public Result changePassword(@RequestBody ChangePassVo changePassVo, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        if (token == null) {
            return Result.error().code(ErrorCode.TOKEN_NOT_EXIST.getCode()).msg(ErrorCode.TOKEN_NOT_EXIST.getMsg());
        }
        Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
        String username = memberIdByJwtToken.get("username");
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);

        String oldPassword = loginUser.getPassword();

        // 创建密码解析器
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        boolean matches = bCryptPasswordEncoder.matches(changePassVo.getOldPass(), oldPassword);

        if(matches) {
            BUser bUser = new BUser();
            bUser.setUsername(username);
            bUser.setId(Long.valueOf(memberIdByJwtToken.get("userId")));
            String newPassword = bCryptPasswordEncoder.encode(changePassVo.getNewPass());
            bUser.setPassword(newPassword);
            bUserService.updateById(bUser);
            return Result.success().msg("修改成功");
        } else  {
            return Result.error().msg("旧密码错误，请输入正确的旧密码");
        }
    }

}

