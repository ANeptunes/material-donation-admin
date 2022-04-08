package com.qyt.material.mapper;

import com.qyt.material.dto.AdminDonationInfoQueryDto;
import com.qyt.material.pojo.Goods;
import com.qyt.material.pojo.GoodsStock;
import com.qyt.material.vo.AdminDonationInfoVo;
import com.qyt.material.vo.AdminGoodsVo;
import com.qyt.material.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/20 18:58
 * @Version 1.0
 */
public interface AdminGoodsMapper extends BaseMapper<GoodsStock> {

    /**
     * 获取未审核物品
     *
     * @param goodsCategoryId 分类
     * @param goodsName       物品名称
     * @param goodsNum        物品编号
     * @return
     */
    List<AdminGoodsVo> getNotAuditGoods(@Param("goodsCategoryId") Long goodsCategoryId, @Param("goodsName") String goodsName, @Param("goodsNum") String goodsNum);

    /**
     * 修改物品状态: 3
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int passGoodsAudit(Long id, String modifiedTime);

    /**
     * 修改物品状态: 1
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int notPassGoodsAudit(Long id, String modifiedTime);

    /**
     * 修改物品状态: 2
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int backGoodsAudit(Long id, String modifiedTime);

    /**
     * 修改物品状态: -1
     *
     * @param id           物品id
     * @param modifiedTime 修改时间
     * @return 修改记录数
     */
    int deleteGoodsAudit(Long id, String modifiedTime);

    /**
     * 获取所有的捐赠信息
     *
     * @param adminDonationInfoQueryDto 捐赠查询条件
     * @return 捐赠信息
     */
    List<AdminDonationInfoVo> getDonationInfo(AdminDonationInfoQueryDto adminDonationInfoQueryDto);

    /**
     * 入库明细数据信息
     *
     * @param goodsRecordNum 捐赠单号
     * @return 入库明细数据信息
     */
    List<GoodsVo> instockDetailInfo(String goodsRecordNum);

    /**
     * 插入物资入库
     *
     * @param goodsStock 物资信息
     * @return 插入记录数
     */
    int insertStock(GoodsStock goodsStock);

    /**
     * 更新捐赠状态
     *
     * @param goodsRecordNum 捐赠单号
     * @return 更新记录数
     */
    int updateInstockStatus(String goodsRecordNum);

}
