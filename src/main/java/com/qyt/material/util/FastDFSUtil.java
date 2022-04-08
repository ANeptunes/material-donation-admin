package com.qyt.material.util;

import com.github.tobato.fastdfs.domain.MateData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsException;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
public class FastDFSUtil {

    private final Logger logger = LoggerFactory.getLogger(FastDFSUtil.class);

    @Resource
    private FastFileStorageClient storageClient;


    /**
     * MultipartFile类型的文件上传ַ
     *
     * @param file 上传的文件
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return getResAccessUrl(storePath);
    }

    /**
     * 普通的文件上传
     *
     * @param file 上传的文件
     */
    public String uploadFile(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        StorePath path = storageClient.uploadFile(inputStream, file.length(),
                FilenameUtils.getExtension(file.getName()), null);
        return getResAccessUrl(path);
    }

    /**
     * 带输入流形式的文件上传
     *
     * @param is       上传的文件流
     * @param size     文件大小
     * @param fileName 文件名
     */
    public String uploadFileStream(InputStream is, long size, String fileName) {
        StorePath path = storageClient.uploadFile(is, size, fileName, null);
        return getResAccessUrl(path);
    }

    /**
     * 将一段文本文件写到FastDFS的服务器上
     *
     * @param content       文本内容
     * @param fileExtension 文件扩展名
     */
    public String uploadFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath path = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return getResAccessUrl(path);
    }

    /**
     * 返回文件上传成功后的地址名称ַ
     *
     * @param storePath 存储地址
     */
    private String getResAccessUrl(StorePath storePath) {
        return storePath.getFullPath();
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件路径
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteFile(String fileUrl) throws FdfsException {
        if (fileUrl == null || "".equals(fileUrl)) {
            return;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            logger.warn("删除图片错误：" + e.getMessage());
        }
    }

    public String uploadFileImage(InputStream is, long size, String fileExtName, Set<MateData> metaData) {
        StorePath path = storageClient.uploadImageAndCrtThumbImage(is, size, fileExtName, metaData);
        return getResAccessUrl(path);
    }
}
