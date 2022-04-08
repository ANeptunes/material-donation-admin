package com.qyt.material.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Component
@Data
public class FastDfsConfig {

    @Value("${fdfs.resHost}")
    private String resHost;

    @Value("${fdfs.storagePort}")
    private String storagePort;

}
