package com.example.integratedHub.controller;

//import cn.dev33.satoken.stp.StpUtil;
//import cn.hutool.json.JSONUtil;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.integratedHub.entity.BCarousel;
import com.example.integratedHub.entity.BResour;
import com.example.integratedHub.entity.BResourFile;
import com.example.integratedHub.entity.enumVo.BannerStatusEnum;
import com.example.integratedHub.entity.securityVo.LoginUser;
import com.example.integratedHub.service.BCarouselService;
import com.example.integratedHub.service.BResourFileService;
import com.example.integratedHub.service.BResourService;
import com.example.integratedHub.utils.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author peterlin
 * @version 1.0
 * @description: TODO
 * @date 2023/8/10 10:00 PM
 */
@RestController
@RequestMapping("/minIO")
public class MinIOController {

    @Autowired
    MinioUtil minioUtil;

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    RedisService redisService;


    @Autowired
    private BCarouselService bCarouselService;

    @Autowired
    private BResourFileService bResourFileService;

    @Autowired
    private BResourService bResourService;



    @PostMapping("/uploadCarousel")
    public Result uploadCarousel(@RequestParam("file")MultipartFile file, @RequestParam("id") Long id, HttpServletRequest request) throws Exception {
        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);

        if(id == null || id == 0) {
            BCarousel bCarousel = new BCarousel();
            bCarousel.setCreateTime(new Date());
            bCarousel.setBannerImg(bucket01.getMinIoUrl());
            bCarousel.setCarouselStatus(BannerStatusEnum.UN_PUBLISHED.getStatus());
            bCarousel.setIsText("否");
            bCarousel.setTextPosition("水平靠左垂直居中");
            bCarousel.setCreator(loginUser.getUsername());
            bCarousel.setCreatorName(loginUser.getNickname());
            boolean save = bCarouselService.save(bCarousel);
            if(save) {
                return Result.success().data("data",bCarousel);
            } else  {
                return Result.error().msg("上传失败，请稍后再试");
            }
        } else {
            QueryWrapper<BCarousel> bCarouselQueryWrapper = new QueryWrapper<>();
            bCarouselQueryWrapper.eq("id", id);
            BCarousel one;
            one = bCarouselService.getById(id);
            String filePath = one.getBannerImg();
            // 使用lastIndexOf()方法找到最后一个斜杠的索引
            int lastIndex = filePath.lastIndexOf("/");
            // 使用substring()方法提取最后一个斜杠后的部分
            String fileName = filePath.substring(lastIndex + 1);
            minioUtil.removeObject(bucketName,fileName);
            // 先删后改
            one.setBannerImg(bucket01.getMinIoUrl());
            boolean update = bCarouselService.updateById(one);
            if(update) {
                return Result.success().data("data",one);
            } else  {
                return Result.error().msg("上传失败，请稍后再试");
            }
        }
    }

    @DeleteMapping("/delCarousel/{id}/{bannerImg}")
    @Transactional
    public Result delCarousel(@PathVariable Long id,@PathVariable String bannerImg) throws Exception {
        boolean remove = bCarouselService.removeById(id);
        if(remove) {
            minioUtil.removeObject(bucketName,bannerImg);
            return Result.success();
        }
        return  Result.error();
    }


    @PostMapping("/uploadReasourFile")
    @Transactional
    public Result uploadReasourFile(@RequestParam("file")MultipartFile file, @RequestParam("id") Long id,@RequestParam("fileName") String fileName,
                                    @RequestParam("fileType") String fileType,@RequestParam("fileId") Long fileId, HttpServletRequest request) throws Exception {
        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }
        // 获取redis中用户信息
        LoginUser loginUser = redisService.getCacheObject(username);


        if(id == null || id == 0) {
            BResour bResour = new BResour();
            cn.hutool.core.lang.UUID uuid = UUID.randomUUID();
            // 转换为字符串并移除所有的连字符
            String uuidWithoutHyphens = uuid.toString().replace("-", "");
            bResour.setResourKey(uuidWithoutHyphens);
            bResour.setCreateTime(new Date());
            bResour.setCreator(loginUser.getUsername());
            boolean save = bResourService.save(bResour);
            if(save) {
                BResourFile bResourFile = new BResourFile();
                bResourFile.setResourFileName(fileName);
                bResourFile.setResourFileType(fileType);
                bResourFile.setResourKey(uuidWithoutHyphens);
                bResourFile.setCreateTime(new Date());
                bResourFile.setCreator(loginUser.getUsername());
                bResourFile.setResourFilePath(bucket01.getMinIoUrl());
                bResourFileService.save(bResourFile);
                return Result.success().data("data",bResour).data("dataFile",bResourFile).data("fileType",fileType);
            } else  {
                return Result.error().msg("上传失败，请稍后再试");
            }
        } else {
            QueryWrapper<BResour> bCarouselQueryWrapper = new QueryWrapper<>();
            bCarouselQueryWrapper.eq("id", id);
            BResour one;
            one = bResourService.getById(id);

            if(fileId == null || fileId == 0) {
                BResourFile bResourFile = new BResourFile();
                bResourFile.setResourFileName(fileName);
                bResourFile.setResourFileType(fileType);
                bResourFile.setResourKey(one.getResourKey());
                bResourFile.setCreateTime(new Date());
                bResourFile.setCreator(loginUser.getUsername());
                bResourFile.setResourFilePath(bucket01.getMinIoUrl());
                boolean save = bResourFileService.save(bResourFile);
                if(save) {
                    return Result.success().data("data",one).data("dataFile",bResourFile).data("fileType",fileType);
                } else  {
                    return Result.error().msg("上传失败，请稍后再试");
                }
            } else {
                QueryWrapper<BResourFile> bResourFileQueryWrapper = new QueryWrapper<>();
                bResourFileQueryWrapper.eq("id", fileId);
                BResourFile oneFile;
                oneFile = bResourFileService.getById(id);

                String filePath = oneFile.getResourFilePath();
                // 使用lastIndexOf()方法找到最后一个斜杠的索引
                int lastIndex = filePath.lastIndexOf("/");
                // 使用substring()方法提取最后一个斜杠后的部分
                String fileNameDel = filePath.substring(lastIndex + 1);
                minioUtil.removeObject(bucketName,fileNameDel);
                // 先删后改
                oneFile.setResourFilePath(bucket01.getMinIoUrl());
                boolean update = bResourFileService.updateById(oneFile);
                if(update) {
                    return Result.success().data("data",one).data("dataFile",oneFile).data("fileType",fileType);
                } else  {
                    return Result.error().msg("上传失败，请稍后再试");
                }
            }
        }
    }

    @PostMapping("/uploadFileToResour")
    @Transactional(rollbackFor = Exception.class)
    public Result uploadFileToResour(@RequestParam("file")MultipartFile file, @RequestParam("resourKey") String resourKey, HttpServletRequest request) throws Exception {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
        BResourFile bResourFile = new BResourFile();
        bResourFile.setCreator(username);
        bResourFile.setResourKey(resourKey);
        bResourFile.setCreateTime(new Date());
        bResourFile.setResourFileType("附件");
        bResourFile.setResourFilePath(bucket01.getMinIoUrl());
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        bResourFile.setResourFileName(originalFilename);
        boolean save = bResourFileService.save(bResourFile);
        if(save) {
            return Result.success().data("data",bResourFile);
        } else  {
            return Result.error().msg("上传失败，请稍后再试");
        }
    }


//    @PostMapping("/uploadOpenClassResource")
//    public Result uploadOpenClassResource(@RequestParam("file")MultipartFile file,@RequestParam("openClassKey") String openClassKey
//            ,@RequestParam("resourceName") String resourceName,@RequestParam("resourceType") String resourceType) throws Exception {
//        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
//        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
//        BOpenClassResource bOpenClassResource = new BOpenClassResource();
//        bOpenClassResource.setOpenClassKey(openClassKey);
//        bOpenClassResource.setResourceType(resourceType);
//        bOpenClassResource.setFilePath(bucket01.getMinIoUrl());
//        bOpenClassResource.setResourceName(resourceName);
//        String uuid = UUID.randomUUID().toString();
//        bOpenClassResource.setFileKey(uuid.replaceAll("-","*"));
//        boolean save = bOpenClassResourceService.save(bOpenClassResource);
//        if(save) {
//            return Result.ok(bOpenClassResource);
//        } else  {
//            return Result.fail().message("上传失败，请稍后再试");
//        }
//    }
//
//
//    @PostMapping("/uploadTask")
//    public Result uploadTask(@RequestParam("file")MultipartFile file, @RequestParam("courseKey") String courseKey
//            , @RequestParam("taskKey") String taskKey, HttpServletRequest request) throws Exception {
//        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
//        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
//        BTaskRecords bTaskRecords = new BTaskRecords();
//        bTaskRecords.setTaskKey(taskKey);
//        bTaskRecords.setCourseKey(courseKey);
//        bTaskRecords.setCreateTime(new Date());
//        //获取请求头token字符串
//        String token = request.getHeader("token");
//
//        //从token字符串获取用户名称（id）
//        String username = JwtHelper.getUsername(token);
//        bTaskRecords.setUsername(username);
//        bTaskRecords.setTaskFile(bucket01.getMinIoUrl());
//
//        boolean save = bTaskRecordsService.save(bTaskRecords);
//        if(save) {
//            return Result.ok(bTaskRecords);
//        } else  {
//            return Result.fail().message("上传失败，请稍后再试");
//        }
//    }
//
//
//    @PostMapping("/uploadClassCover")
//    @Transactional(rollbackFor = Exception.class)
//    public Result uploadClassCover(@RequestParam("file")MultipartFile file, @RequestParam("classKey") String classKey, HttpServletRequest request) throws Exception {
//        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
//        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
//        QueryWrapper<BOpenClass> bOpenClassQueryWrapper = new QueryWrapper<>();
//        bOpenClassQueryWrapper.eq("class_key",classKey);
//        BOpenClass one = bOpenClassService.getOne(bOpenClassQueryWrapper);
//        String fileName = "";
//        if(StringUtils.isNotBlank(one.getClassCover())) {
//            String  oldPath = one.getClassCover();
//            // 使用lastIndexOf()方法找到最后一个斜杠的索引
//            int lastIndex = oldPath.lastIndexOf("/");
//            // 使用substring()方法提取最后一个斜杠后的部分
//            fileName = oldPath.substring(lastIndex + 1);
//        }
//        // 修改cover地址
//        one.setClassCover(bucket01.getMinIoUrl());
//        boolean b = bOpenClassService.updateById(one);
//        if(b) {
//            // 如果有旧封面，删除旧封面
//            if(StringUtils.isNotBlank(fileName)) {
//                minioUtil.removeObject(bucketName,fileName);
//            }
//            return Result.ok(bucket01.getMinIoUrl());
//        } else  {
//            return Result.fail().message("上传失败，请稍后再试");
//        }
//    }
//
//    /**
//     * @description 上传课程封面
//     * @author echoes
//     * @param[1] null
//     * @throws
//     * @return
//     * @time 2024/7/19 15:43
//     */
//    @PostMapping("/uploadCourseCover")
//    @Transactional(rollbackFor = Exception.class)
//    public Result uploadCourseCover(@RequestParam("file")MultipartFile file, @RequestParam("courseKey") String courseKey, HttpServletRequest request) throws Exception {
//        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//        System.out.println("bucket01.getMinIoUrl() = " + bucket01.getMinIoUrl());
//        System.out.println("bucket01.getNginxUrl() = " + bucket01.getNginxUrl());
//        QueryWrapper<BCourse> bOpenClassQueryWrapper = new QueryWrapper<>();
//        bOpenClassQueryWrapper.eq("course_key",courseKey);
//        BCourse one = bCourseService.getOne(bOpenClassQueryWrapper);
//        String fileName = "";
//
//        if (one != null) {
//            if(StringUtils.isNotBlank(one.getCover())) {
//                String oldPath = one.getCover();
//                // 使用lastIndexOf()方法找到最后一个斜杠的索引
//                int lastIndex = oldPath.lastIndexOf("/");
//                // 使用substring()方法提取最后一个斜杠后的部分
//                fileName = oldPath.substring(lastIndex + 1);
//            }
//            // 修改cover地址
//            one.setCover(bucket01.getMinIoUrl());
//            boolean b = bCourseService.updateById(one);
//            if(b) {
//                // 如果有旧封面，删除旧封面
//                if(StringUtils.isNotBlank(fileName)) {
//                    minioUtil.removeObject(bucketName,fileName);
//                }
//                return Result.ok(one);
//            } else  {
//                return Result.fail().message("上传失败，请稍后再试");
//            }
//        } else {
//            BCourse bCourse = new BCourse();
//            bCourse.setCover(bucket01.getMinIoUrl());
//            String uuid = UUID.randomUUID().toString();
//            bCourse.setCourseKey(uuid.replaceAll("-","*"));
//            //获取请求头token字符串
//            String token = request.getHeader("token");
//            String username = JwtHelper.getUsername(token);
//            bCourse.setUpdateUser(username);
//            bCourse.setCourseStatus("待安排课时");
//            boolean save = bCourseService.save(bCourse);
//            if(save) {
//                return Result.ok(bCourse);
//            } else  {
//                return Result.fail().message("上传失败，请稍后再试");
//            }
//        }
//    }
//
//        @PostMapping("/uploadSyllabus")
//        @Transactional
//        public Result uploadSyllabus(@RequestParam("file")MultipartFile file, @RequestParam("openClassKey") String openClassKey
//                , @RequestParam("syllabusName") String syllabusName,@RequestParam("level") Integer level,@RequestParam("parentId") Long parentId, HttpServletRequest request) throws Exception {
//            UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//            BOpenClassSyllabus bOpenClassSyllabus = new BOpenClassSyllabus();
//            bOpenClassSyllabus.setOpenClassKey(openClassKey);
//            bOpenClassSyllabus.setSyllabusName(syllabusName);
//            bOpenClassSyllabus.setSyllabusLevel(level);
//            bOpenClassSyllabus.setParentId(parentId);
//            bOpenClassSyllabus.setSyllabusWare(bucket01.getMinIoUrl());
//            bOpenClassSyllabus.setCreateTime(new Date());
//            //获取请求头token字符串
//            String token = request.getHeader("token");
//
//            //从token字符串获取用户名称（id）
//            String username = JwtHelper.getUsername(token);
//
//            boolean save = bOpenClassSyllabusService.save(bOpenClassSyllabus);
//            if(save) {
//                return Result.ok(bOpenClassSyllabus);
//            } else  {
//                return Result.fail().message("上传失败，请稍后再试");
//            }
//        }
//
//    @PostMapping("/uploadEditSyllabus")
//    @Transactional
//    public Result uploadEditSyllabus(@RequestParam("file")MultipartFile file, @RequestParam("id") Long id, @RequestParam("syllabusWare") String syllabusWare
//            , @RequestParam("syllabusName") String syllabusName, HttpServletRequest request) throws Exception {
//        UploadResponse bucket01 = minioUtil.uploadFile(file, bucketName);
//        BOpenClassSyllabus bOpenClassSyllabus = new BOpenClassSyllabus();
//        bOpenClassSyllabus.setId(id);
//        bOpenClassSyllabus.setSyllabusName(syllabusName);
//        bOpenClassSyllabus.setSyllabusWare(bucket01.getMinIoUrl());
//        bOpenClassSyllabus.setCreateTime(new Date());
//        //获取请求头token字符串
//        String token = request.getHeader("token");
//
//        //从token字符串获取用户名称（id）
//        String username = JwtHelper.getUsername(token);
//
//        boolean save = bOpenClassSyllabusService.updateById(bOpenClassSyllabus);
//        if(save) {
//            // 使用lastIndexOf()方法找到最后一个斜杠的索引
//            int lastIndex = syllabusWare.lastIndexOf("/");
//            // 使用substring()方法提取最后一个斜杠后的部分
//            String fileName = syllabusWare.substring(lastIndex + 1);
//            minioUtil.removeObject(bucketName,fileName);
//            return Result.ok(bOpenClassSyllabus);
//        } else  {
//            return Result.fail().message("上传失败，请稍后再试");
//        }
//    }
//
//    @DeleteMapping("/delNextSyllabus/{id}/{syllabusWare}")
//    @Transactional
//    public Result delNextSyllabus(@PathVariable Long id,@PathVariable String syllabusWare) throws Exception {
//        boolean remove = bOpenClassSyllabusService.removeById(id);
//        if(remove) {
//            minioUtil.removeObject(bucketName,syllabusWare);
//            return Result.ok();
//        }
//        return  Result.fail();
//    }
//
//    @DeleteMapping("/delResource/{fileKey}/{filePath}")
//    @Transactional
//    public Result delResource(@PathVariable String fileKey,@PathVariable String filePath) throws Exception {
//        QueryWrapper<BCourseResource> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("file_key",fileKey);
//        boolean remove = bCourseResourceService.remove(queryWrapper);
//        if(remove) {
//            String fileName = filePath;
//            minioUtil.removeObject(bucketName,fileName);
//            return Result.ok();
//        }
//        return  Result.fail();
//    }
//
//    @DeleteMapping("/delOpenClassResource/{fileKey}/{filePath}")
//    @Transactional(rollbackFor = Exception.class)
//    public Result delOpenClassResource(@PathVariable String fileKey,@PathVariable String filePath) throws Exception {
//        QueryWrapper<BOpenClassResource> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("file_key",fileKey);
//        boolean remove = bOpenClassResourceService.remove(queryWrapper);
//        if(remove) {
//            String fileName = filePath;
//            minioUtil.removeObject(bucketName,fileName);
//            return Result.ok();
//        }
//        return  Result.fail();
//    }
//
//

//
//    @DeleteMapping("/delFileInFolder/{id}/{filePath}")
//    @Transactional(rollbackFor = Exception.class)
//    public Result delFileToFolder(@PathVariable String id,@PathVariable String filePath,HttpServletRequest request) throws Exception {
//        boolean remove = bFileService.removeById(id);
//        if(remove) {
//            minioUtil.removeObject(bucketName,filePath);
//            //获取IP地址
//            String ipAddress = IpUtil.getIpAddr(request);
//            //获取请求头token字符串
//            String token = request.getHeader("token");
//            String username = "";
//            // 判断是否存在 token
//            if (StringUtils.isNotBlank(token)) {
//                // 从 token 字符串中获取用户名称（id）
//                username = JwtHelper.getUsername(token);
//            }
//            /** 操作记录 **/
//            BFileOperation bFileOperation = new BFileOperation();
//            bFileOperation.setOperationTime(new Date());
//            bFileOperation.setOperationIp(ipAddress);
//            bFileOperation.setOperationNo(username);
//            bFileOperation.setOperationStatus("成功");
//            bFileOperation.setOperationTarget(filePath);
//            bFileOperation.setOperationTargetId(id);
//            bFileOperation.setOperationTargetType("文件");
//            bFileOperation.setOperationType("删除");
//            bFileOperationService.save(bFileOperation);
//
//            return Result.ok();
//        }
//        return  Result.fail();
//    }
//
//    @PostMapping("/delFileInFolderBatch")
//    @Transactional(rollbackFor = Exception.class)
//    public Result delFileInFolderBatch(@RequestBody BatchDelVo batchDelVo,HttpServletRequest request) throws Exception {
//        List<String> ids = batchDelVo.getIds();
//        List<String> filePaths = batchDelVo.getFilePaths();
//        List<String> fileNames = new ArrayList<>();
//        QueryWrapper<BFile> bFileQueryWrapper = new QueryWrapper<>();
//        bFileQueryWrapper.in("id",ids);
//        boolean remove = bFileService.remove(bFileQueryWrapper);
//        if(remove) {
//            for (int i = 0; i < filePaths.size(); i++) {
//                // 使用lastIndexOf()方法找到最后一个斜杠的索引
//                int lastIndex = filePaths.get(i).lastIndexOf("/");
//                // 使用substring()方法提取最后一个斜杠后的部分
//                String fileName = filePaths.get(i).substring(lastIndex + 1);
//                fileNames.add(fileName);
//                minioUtil.removeObject(bucketName,fileName);
//            }
//
//            //获取IP地址
//            String ipAddress = IpUtil.getIpAddr(request);
//            //获取请求头token字符串
//            String token = request.getHeader("token");
//            String username = "";
//            // 判断是否存在 token
//            if (StringUtils.isNotBlank(token)) {
//                // 从 token 字符串中获取用户名称（id）
//                username = JwtHelper.getUsername(token);
//            }
//            /** 操作记录 **/
//            BFileOperation bFileOperation = new BFileOperation();
//            bFileOperation.setOperationTime(new Date());
//            bFileOperation.setOperationIp(ipAddress);
//            bFileOperation.setOperationNo(username);
//            bFileOperation.setOperationStatus("成功");
//            bFileOperation.setOperationTarget(String.join(",", fileNames));
//            bFileOperation.setOperationTargetId(String.join(",", ids));
//            bFileOperation.setOperationTargetType("文件");
//            bFileOperation.setOperationType("批量删除");
//            bFileOperationService.save(bFileOperation);
//
//            return Result.ok();
//        }
//        return  Result.fail();
//    }

}
