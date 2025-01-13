package com.example.integratedHub.controller;


import com.example.integratedHub.entity.BResourFile;
import com.example.integratedHub.service.BResourFileService;
import com.example.integratedHub.utils.JwtUtil;
import com.example.integratedHub.utils.MinioUtil;
import com.example.integratedHub.utils.Result;
import com.example.integratedHub.utils.UploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 资源对应的文件 前端控制器
 * </p>
 *
 * @author 劳威锟
 * @since 2024-08-08
 */
@RestController
@RequestMapping("/resourFile")
public class BResourFileController {

    @Value("${minio.bucketName}")
    private String bucketName;

    @Autowired
    MinioUtil minioUtil;

    @Autowired
    private BResourFileService bResourFileService;

    @DeleteMapping("removeFile/{id}")
    public Result removeFile(@PathVariable Long id, HttpServletRequest request) throws Exception {
        // 获取请求头token字符串
        String token = request.getHeader("inhub-token");
        String username = "";
        if (token != null) {
            Map<String, String> memberIdByJwtToken = JwtUtil.getMemberIdByJwtToken(token);
            username = memberIdByJwtToken.get("username");
        }

        BResourFile byId = bResourFileService.getById(id);
        bResourFileService.removeById(id);
        String filePath = byId.getResourFilePath();
        // 使用lastIndexOf()方法找到最后一个斜杠的索引
        int lastIndex = filePath.lastIndexOf("/");
        // 使用substring()方法提取最后一个斜杠后的部分
        String fileNameDel = filePath.substring(lastIndex + 1);
        minioUtil.removeObject(bucketName,fileNameDel);

        return Result.success();

    }

}

