package com.qyt.material.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/30 17:42
 * @Version 1.0
 */

@Data
public class GoodsInstockVo {
    String goodsRecordNum;
    List<GoodsVo> instockGoodsList;
}
