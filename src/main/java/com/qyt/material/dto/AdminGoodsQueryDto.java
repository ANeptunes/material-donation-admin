package com.qyt.material.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/20 18:55
 * @Version 1.0
 */
@Data
public class AdminGoodsQueryDto {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    private String goodsName;
    private Long goodsCategoryId;
    private String goodsNum;
}
