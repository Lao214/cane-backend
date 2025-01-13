package com.example.integratedHub.controller;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourFile;
import com.example.integratedHub.entity.Vo.CountEveryTypeVo;
import com.example.integratedHub.entity.Vo.NewQueryVo;
import com.example.integratedHub.entity.Vo.ResourQueryVo;
import com.example.integratedHub.service.BResourFileService;
import com.example.integratedHub.service.BResourService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.MinioUtil;
import com.example.integratedHub.utils.RedisService;
import com.example.integratedHub.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
@RestController
@RequestMapping("/resour")
public class BResourController {

    @Autowired
    private BResourService bResourService;

    @Autowired
    private BResourFileService bResourFileService;

    @Autowired
    RedisService redisService;

    @Autowired
    MinioUtil minioUtil;

    @Value("${minio.bucketName}")
    private String bucketName;

    @GetMapping("getHomeResourList")
    public Result getHomeResourList() {
        QueryWrapper<BResour> queryWrapper = new QueryWrapper<>();
        // 根据创建时间查出最新上线的四个资源
        queryWrapper.orderByDesc("create_time");
        queryWrapper.last("limit 4");
        List<BResour> newList = bResourService.list(queryWrapper);

        LocalDateTime now = LocalDateTime.now(); // 获取当前时间

        for (BResour resour : newList) {
            Date createTimeDate = resour.getCreateTime(); // 假设 BResour 有一个 getCreateTime() 方法

            if (createTimeDate != null) {
                // 将 Date 转换为 LocalDateTime
                LocalDateTime createTime = createTimeDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

                // 检查 createTime 是否在三天之内
                if (ChronoUnit.DAYS.between(createTime, now) <= 3) {
                    resour.setIsNew("new"); // 假设 BResour 有一个 setIsNew() 方法
                }
            }
        }

        // 根据使用人数查出热门推荐的四个资源
        QueryWrapper<BResour> queryWrapperHot = new QueryWrapper<>();
        queryWrapperHot.orderByDesc("view_count");
        queryWrapperHot.last("limit 4");
        List<BResour> hotList = bResourService.list(queryWrapperHot);
        return Result.success().data("newList",newList).data("hotList",hotList);
    }

    @GetMapping("getResourList/{page}/{limit}")
    public Result getResourList(@PathVariable Long page, @PathVariable Long limit, ResourQueryVo resourQueryVo) {
        if (resourQueryVo.getResourType().equals("全部")) {
           resourQueryVo.setResourType("");
        }
        if(resourQueryVo.getResourFirstType().equals("全部")) {
            resourQueryVo.setResourFirstType("");
        }
        // 创建page对象
        Page<BResour> pageParam = new Page<>(page,limit);
        // 调用service方法
        IPage<BResour> pageModel = bResourService.selectPage(pageParam,resourQueryVo);
        CountEveryTypeVo everyTypeVo =  bResourService.countEveryType(resourQueryVo);
        return Result.success().data("data",pageModel);
    }

    @GetMapping("countTypes")
    public Result countTypes(ResourQueryVo resourQueryVo) {
        resourQueryVo.setResourType("");
        if(resourQueryVo.getResourFirstType().equals("全部")) {
            resourQueryVo.setResourFirstType("");
        }
        CountEveryTypeVo everyTypeVo =  bResourService.countEveryType(resourQueryVo);
        return Result.success().data("everyTypeVo",everyTypeVo);
    }

    @GetMapping("getPageList/{page}/{limit}")
    public Result list(@PathVariable Long page, @PathVariable Long limit, NewQueryVo newQueryVo) {
        //创建page对象
        Page<BResour> pageParam = new Page<>(page,limit);
        //调用service方法
        IPage<BResour> pageModel = bResourService.selectPage(pageParam,newQueryVo);
        QueryWrapper<BResour> queryWrapper = new QueryWrapper<>();
        int count = bResourService.count(queryWrapper);
        return Result.success().data("data",pageModel).data("count",count);
    }

    @GetMapping("editGetOne/{id}")
    public Result list(@PathVariable Long id) {
        QueryWrapper<BResour> bResourQueryWrapper = new QueryWrapper<>();
        bResourQueryWrapper.eq("id",id);
        BResour one = bResourService.getOne(bResourQueryWrapper);
        QueryWrapper<BResourFile> bResourFileQueryWrapper = new QueryWrapper<>();
        bResourFileQueryWrapper.eq("resour_key",one.getResourKey());
        List<BResourFile> list = bResourFileService.list(bResourFileQueryWrapper);
        List<BResourFile> imgList = new ArrayList<>();
        List<BResourFile> fileList = new ArrayList<>();
        for (BResourFile bResourFile :list) {
            if(bResourFile.getResourFileType().equals("内容")) {
                imgList.add(bResourFile);
            } else  {
                fileList.add(bResourFile);
            }
        }
        return Result.success().data("data",one).data("imgList",imgList).data("fileList",fileList);
    }

    @GetMapping("getOne/{resourKey}")
    public Result getOne(@PathVariable String resourKey) {
        QueryWrapper<BResour> bResourQueryWrapper = new QueryWrapper<>();
        bResourQueryWrapper.eq("resour_key",resourKey);
        BResour one = bResourService.getOne(bResourQueryWrapper);
        QueryWrapper<BResourFile> bResourFileQueryWrapper = new QueryWrapper<>();
        bResourFileQueryWrapper.eq("resour_key",one.getResourKey());
        List<BResourFile> list = bResourFileService.list(bResourFileQueryWrapper);
        List<BResourFile> imgList = new ArrayList<>();
        List<BResourFile> fileList = new ArrayList<>();
        one.setDownloadCount(0);
        for (BResourFile bResourFile :list) {
            if(bResourFile.getResourFileType().equals("内容")) {
                imgList.add(bResourFile);
            } else  {
                fileList.add(bResourFile);
                one.setDownloadCount(one.getDownloadCount() + bResourFile.getDownloadCount());
            }
        }
        return Result.success().data("data",one).data("imgList",imgList).data("fileList",fileList);
    }

    @PostMapping("/addResour")
//    @Transactional(rollbackFor = Exception.class)
    public Result addResour(@RequestBody BResour bResour, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        cn.hutool.core.lang.UUID uuid = UUID.randomUUID();
        // 转换为字符串并移除所有的连字符
        String uuidWithoutHyphens = uuid.toString().replace("-", "");
        bResour.setResourKey(uuidWithoutHyphens);
        bResour.setCreateTime(new Date());
        bResour.setCreator(username);
        boolean save = bResourService.save(bResour);
        if(save) {
            return Result.success().msg("添加成功").data("data",bResour);
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @PostMapping("/updateResour")
    public Result updateResour(@RequestBody BResour bResour, HttpServletRequest request) {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        boolean update = bResourService.updateById(bResour);
        if(update) {
            return Result.success().msg("添加成功").data("data",bResour);
        } else  {
            return Result.error().msg("添加成功");
        }
    }

    @DeleteMapping("removeResour/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result removeResour(@PathVariable Long id, HttpServletRequest request) throws Exception {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        BResour byId = bResourService.getById(id);
        QueryWrapper<BResourFile> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("resour_key",byId.getResourKey());
        List<BResourFile> list = bResourFileService.list(queryWrapper);
        List<Long> isDeleteList = new ArrayList<>();
        for (BResourFile bResourFile : list) {
            String filePath = bResourFile.getResourFilePath();
            isDeleteList.add(bResourFile.getId());
            // 使用lastIndexOf()方法找到最后一个斜杠的索引
            int lastIndex = filePath.lastIndexOf("/");
            // 使用substring()方法提取最后一个斜杠后的部分
            String fileNameDel = filePath.substring(lastIndex + 1);
            minioUtil.removeObject(bucketName,fileNameDel);
        }
        bResourFileService.removeByIds(isDeleteList);
        bResourService.removeById(id);

        return Result.success();

    }
}

