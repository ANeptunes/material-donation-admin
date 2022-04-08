package com.qyt.material.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Data
public class GoodsRecordQueryDto {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    // 捐赠记录单号
    private String goodsRecordNum;
    // 捐赠类型
    private Integer type;
    // 捐赠状态
    private Integer status;
    // 捐赠开始时间
    private String startTime;
    // 捐赠结束时间
    private String endTime;
    // 用户唯一ID
    private String uid;
}
