package com.qyt.material.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Data
public class GoodsQueryDto {
    @NotNull
    private Integer pageNum;
    @NotNull
    private Integer pageSize;
    private String goodsName;
    private Long goodsCategoryId;
    private Integer status;
    private String uid;
}
