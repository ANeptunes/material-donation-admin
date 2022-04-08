package com.qyt.material.service;

import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.vo.GoodsRecordVo;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */
public interface GoodsDonationService {

    /**
     * 物品捐赠
     *
     * @param goodsRecordVo
     * @return
     */
    int addGoodsDonation(GoodsRecordVo goodsRecordVo);

    /**
     * 根据条件获取所有的可以捐赠的物品列表信息
     *
     * @param goodsQueryDto 物品查询条件
     * @return 返回可以捐赠的物品列表
     */
    PageVo<GoodsVo> findDonationGoodsList(GoodsQueryDto goodsQueryDto);
}
