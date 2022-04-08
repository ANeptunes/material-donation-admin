package com.qyt.material.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Goods {
    // 物品id
    @Id
    private Long id;
    // 物品名字
    private String goodsName;
    // 物品编号
    private String goodsNum;
    // 物品规格
    private String specification;
    // 物品单位
    private String unit;
    // 物品数量
    private Integer amount;
    // 物品图片
    private String imgUrl;
    // 物品分类
    private Long goodsCategoryId;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

    // 物品状态(-1:已删除 0:回收站 1:未通过 2:等待审核: 3:通过审核)
    private Integer status;

    private String remark;

    // 用户唯一ID
    String uid;
}
