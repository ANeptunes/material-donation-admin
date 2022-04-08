package com.qyt.material.service.impl;

import com.qyt.material.config.FastDfsConfig;
import com.qyt.material.mapper.ImageMapper;
import com.qyt.material.pojo.Image;
import com.qyt.material.service.UploadService;
import com.qyt.material.util.FastDFSUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private FastDFSUtil fastDFSUtil;

    private FastDfsConfig config;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }
        InputStream inputStream = file.getInputStream();
        // 文件大小
        long size = file.getSize();
        //文件的原名称
        String originalFilename = file.getOriginalFilename();
        String fileExtendName;
        String path = null;
        if (originalFilename != null) {
            fileExtendName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            path = fastDFSUtil.uploadFileImage(inputStream, size, fileExtendName.toUpperCase(), null);
            BufferedImage image = ImageIO.read(file.getInputStream());
            //如果image=null 表示上传的不是图片格式
            if (image != null) {
                Image imageAttachment = new Image();
                imageAttachment.setPath(path);
                imageAttachment.setSize(size);
                imageAttachment.setMediaType(file.getContentType());
                imageAttachment.setSuffix(fileExtendName);
                imageAttachment.setWidth(image.getWidth());
                imageAttachment.setHeight(image.getHeight());
                imageAttachment.setCreateTime(new Date());
                imageMapper.insert(imageAttachment);
            }
        }
        return path;
    }
}
