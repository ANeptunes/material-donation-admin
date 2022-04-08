package com.qyt.material.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/28 18:01
 * @Version 1.0
 */
@Data
public class GoodsStock {
    @Id
    private Long id;
    private String goodsName;
    private String specification;
    private String unit;
    private Integer amount;
    private String imgUrl;
    private Long goodsCategoryId;
    private String goodsCategoryName;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;
    private Integer status;
    private String remark;
    private String uid;
    private String goodsRecordNum;
    private String goodsInstockNum;
}
