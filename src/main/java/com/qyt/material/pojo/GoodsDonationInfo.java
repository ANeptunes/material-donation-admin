package com.qyt.material.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 用于物品捐赠记录明细
 *
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */
@Data
public class GoodsDonationInfo {
    /**
     * 数据ID
     */
    private Long id;

    /**
     * 捐赠记录编号
     */
    private String goodsRecordNum;

    /**
     * 捐赠物品编号
     */
    private String goodsNum;

    /**
     * 捐赠的单个物品数量
     */
    private Integer number;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

    /**
     * 用户ID
     */
    private String uid;

}
