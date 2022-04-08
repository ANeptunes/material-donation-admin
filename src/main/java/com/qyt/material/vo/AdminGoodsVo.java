package com.qyt.material.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
public class AdminGoodsVo {
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
    // 物品分类ID
    private Long goodsCategoryId;

    // 物品分类名称
    private String goodsCategoryName;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;

    // 物品状态
    private Integer status;

    // 评论信息
    private String remark;

    // 用户唯一ID
    private String uid;

    private String username;

    private String realName;
}
