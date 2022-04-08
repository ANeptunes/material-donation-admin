package com.qyt.material.service;

import com.qyt.material.dto.AdminDonationInfoQueryDto;
import com.qyt.material.dto.AdminGoodsQueryDto;
import com.qyt.material.vo.*;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/20 18:46
 * @Version 1.0
 */
public interface AdminGoodsService {
    /**
     * 查询所有未审核物品
     *
     * @param goodsQueryDto 查询参数
     * @return 未审核物品
     */
    PageVo<AdminGoodsVo> getNotAuditGoods(AdminGoodsQueryDto goodsQueryDto);

    /**
     * 通过审核，修改物品状态（通过审核:3）
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int passGoodsAudit(Long id, String modifiedTime);

    /**
     * 通过审核，修改物品状态（未通过审核:1）
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int notPassGoodsAudit(Long id, String modifiedTime);

    /**
     * 通过审核，修改物品状态（待审核:2）
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int backGoodsAudit(Long id, String modifiedTime);

    /**
     * 通过审核，修改物品状态（已删除:-1）
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int deleteGoodsAudit(Long id, String modifiedTime);

    /**
     * 查询所有捐赠信息
     *
     * @param adminDonationInfoQueryDto 捐赠信息查询参数
     * @return 捐赠信息
     */
    PageVo<AdminDonationInfoVo> getDonationInfo(AdminDonationInfoQueryDto adminDonationInfoQueryDto);

    /**
     * 查询入库明细
     *
     * @param id             物品id
     * @param goodsRecordNum 物品捐赠编号
     * @param pageNum        当前页
     * @return 分页入库明细
     */
    PageVo<GoodsVo> instockDetailInfo(Long id, String goodsRecordNum, Integer pageNum);

    /**
     * 添加物品入库
     *
     * @param goodsInstockVo 入库物资列表
     * @return 入库记录数
     */
    int instockAdd(GoodsInstockVo goodsInstockVo);
}
