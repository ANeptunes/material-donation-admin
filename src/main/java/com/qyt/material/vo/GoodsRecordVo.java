package com.qyt.material.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRecordVo {
    private Long id;
    /**
     * 捐赠记录编号
     */
    private String goodsRecordNum;

    /**
     * 捐赠类型
     */
    private Integer type;
    /**
     * 捐赠数量
     */
    private Integer totalNumber;
    /**
     * 捐赠物品来源地址
     */
    private String address;
    /**
     * 捐赠人名称
     */
    private String name;
    /**
     * 捐赠人电话
     */
    private String phone;
    /**
     * 捐赠人邮箱
     */
    private String email;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 捐赠的物资
     */
    private List<Object> goods = new ArrayList<>();
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
     * 唯一用户ID
     */
    private String uid;
}
