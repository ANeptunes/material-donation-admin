package com.qyt.material.controller;

import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/image")
    public Result uploadImage(MultipartFile file) throws IOException {
        String realPath = uploadService.uploadImage(file);
        System.out.println(realPath);
        return Result.success(ResultCode.SUCCESS, "上传成功", realPath);
    }
}
