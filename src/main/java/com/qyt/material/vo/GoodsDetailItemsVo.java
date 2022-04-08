package com.qyt.material.vo;

import lombok.Data;

import javax.persistence.Id;

@Data
public class GoodsDetailItemsVo {
    // 物品id
    @Id
    private Long id;
    // 物品名字
    private String goodsName;
    // 物品编号
    private String goodsNum;
    // 物品捐赠数量
    private Integer number;
    // 物品单位
    private String unit;
    // 物品规格
    private String specification;
    // 物品图片
    private String imgUrl;
    // 物品分类名称
    private String goodsCategoryName;
}
