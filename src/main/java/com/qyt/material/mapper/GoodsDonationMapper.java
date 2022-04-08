package com.qyt.material.mapper;

import com.qyt.material.dto.UpdateGoodsNumsDto;
import com.qyt.material.pojo.GoodsDonationInfo;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

public interface GoodsDonationMapper extends BaseMapper<GoodsRecord> {

    /**
     * 插入捐赠记录
     *
     * @param goodsRecord 捐赠记录数据
     * @return 插入记录条数
     */
    int addGoodsRecord(GoodsRecord goodsRecord);

    /**
     * 插入详细的捐赠信息（中间表 捐赠单号对应的所有物品编号以及数量）
     *
     * @param goodsDonationInfo 捐赠信息
     * @return 插入记录条数
     */
    int addGoodsDonationDetailedInfo(GoodsDonationInfo goodsDonationInfo);

    /**
     * 根据条件获取所有的可以捐赠的物品列表信息
     *
     * @param goodsCategoryId 物品分类
     * @param goodsName       物品名称
     * @param status          状态
     * @param uid             用户ID
     * @return 返回可以捐赠的物品列表
     */
    List<GoodsVo> findDonationGoodsList(@Param("goodsCategoryId") Long goodsCategoryId, @Param("goodsName") String goodsName, @Param("status") Integer status, @Param("uid") String uid);

    /**
     * 根据捐赠单号查询捐赠的所有物品明细信息（更新库存的数据）
     *
     * @param goodsRecordNum 物品捐赠单号
     * @return 需要更新的物品信息列表
     */
    List<UpdateGoodsNumsDto> findGoodsListByGoodsRecordNum(@Param("goodsRecordNum") String goodsRecordNum);

    /**
     * 更新库存数量
     *
     * @param updateGoodsNum 更新的物品的物品编号
     * @param updateNumber   更新的数量
     * @return 更新的记录条数
     */
    int updateGoodsStockNumber(@Param("updateGoodsNum") String updateGoodsNum, @Param("updateNumber") Integer updateNumber);
}
