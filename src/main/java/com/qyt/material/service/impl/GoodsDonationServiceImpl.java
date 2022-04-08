package com.qyt.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.dto.UpdateGoodsNumsDto;
import com.qyt.material.mapper.GoodsDonationMapper;
import com.qyt.material.pojo.GoodsDonationInfo;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.service.GoodsDonationService;
import com.qyt.material.vo.GoodsRecordVo;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@Service
public class GoodsDonationServiceImpl implements GoodsDonationService {

    @Resource
    private GoodsDonationMapper goodsDonationMapper;

    /**
     * 捐赠物品
     *
     * @param goodsRecordVo 物品捐赠信息
     * @return 插入记录条数
     */
    @Override
    public int addGoodsDonation(GoodsRecordVo goodsRecordVo) {
        //随机生成捐赠单号
        String goodsRecordNum = UUID.randomUUID().toString().substring(0, 32).replace("-", "");
        //记录该单的总数
        Integer number = 0;
        List<Object> goods = goodsRecordVo.getGoods();
        for (Object good : goods) {
            LinkedHashMap item = (LinkedHashMap) good;
            System.out.println(item);
            // 入库数量
            Integer goodsNumber = (Integer) item.get("goodsNumber");
            // 物品编号
            String goodsNum = (String) item.get("goodsNum");

            number += goodsNumber;
            // 插入捐赠记录明细
            GoodsDonationInfo goodsDonationInfo = new GoodsDonationInfo();
            goodsDonationInfo.setCreateTime(new Date());
            goodsDonationInfo.setModifiedTime(new Date());
            goodsDonationInfo.setGoodsRecordNum(goodsRecordNum);
            goodsDonationInfo.setGoodsNum(goodsNum);
            goodsDonationInfo.setNumber(goodsNumber);
            goodsDonationInfo.setUid(goodsRecordVo.getUid());
            goodsDonationMapper.addGoodsDonationDetailedInfo(goodsDonationInfo);
        }
        GoodsRecord goodsRecord = new GoodsRecord();
        BeanUtils.copyProperties(goodsRecordVo, goodsRecord);
        goodsRecord.setGoodsRecordNum(goodsRecordNum);
        goodsRecord.setCreateTime(new Date());
        goodsRecord.setModifiedTime(new Date());
        goodsRecord.setTotalNumber(number);
        goodsRecord.setStatus(1);
        goodsRecord.setUid(goodsRecordVo.getUid());
        int count = goodsDonationMapper.addGoodsRecord(goodsRecord);
        // 更新库存数量
        int updateCount = 0;
        if (count > 0) {
            List<UpdateGoodsNumsDto> updateGoodsInfo = goodsDonationMapper.findGoodsListByGoodsRecordNum(goodsRecordNum);
            for (UpdateGoodsNumsDto updateGoodInfo : updateGoodsInfo) {
                String updateGoodsNum = updateGoodInfo.getGoodsNum();
                Integer updateNumber = updateGoodInfo.getNumber();
                updateCount = goodsDonationMapper.updateGoodsStockNumber(updateGoodsNum, updateNumber);
            }
        }
        return updateCount;
    }

    /**
     * 查根据条件获取所有的可以捐赠的物品列表信息
     *
     * @param goodsQueryDto 查询的条件
     * @return 返回可以捐赠的物品列表
     */
    @Override
    public PageVo<GoodsVo> findDonationGoodsList(GoodsQueryDto goodsQueryDto) {
        PageHelper.startPage(goodsQueryDto.getPageNum(), goodsQueryDto.getPageSize());
        List<GoodsVo> goodsList = goodsDonationMapper.findDonationGoodsList(goodsQueryDto.getGoodsCategoryId(), goodsQueryDto.getGoodsName(), goodsQueryDto.getStatus(), goodsQueryDto.getUid());
        PageInfo<GoodsVo> goodsPageInfo = new PageInfo<>(goodsList);
        return new PageVo<>(goodsPageInfo.getTotal(), goodsList);
    }
}
