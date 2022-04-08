package com.qyt.material.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyt.material.dto.AdminDonationInfoQueryDto;
import com.qyt.material.dto.AdminGoodsQueryDto;
import com.qyt.material.mapper.AdminGoodsMapper;
import com.qyt.material.pojo.GoodsStock;
import com.qyt.material.service.AdminGoodsService;
import com.qyt.material.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/20 18:48
 * @Version 1.0
 */
@Service
public class AdminGoodsServiceImpl implements AdminGoodsService {

    @Resource
    private AdminGoodsMapper adminGoodsMapper;

    @Override
    public PageVo<AdminGoodsVo> getNotAuditGoods(AdminGoodsQueryDto goodsQueryDto) {
        PageHelper.startPage(goodsQueryDto.getPageNum(), goodsQueryDto.getPageSize());
        List<AdminGoodsVo> goodsList = adminGoodsMapper.getNotAuditGoods(goodsQueryDto.getGoodsCategoryId(), goodsQueryDto.getGoodsName(), goodsQueryDto.getGoodsNum());
        PageInfo<AdminGoodsVo> goodsPageInfo = new PageInfo<>(goodsList);
        return new PageVo<>(goodsPageInfo.getTotal(), goodsList);
    }

    @Override
    public int passGoodsAudit(Long id, String modifiedTime) {
        return adminGoodsMapper.passGoodsAudit(id, modifiedTime);
    }

    @Override
    public int notPassGoodsAudit(Long id, String modifiedTime) {
        return adminGoodsMapper.notPassGoodsAudit(id, modifiedTime);
    }

    @Override
    public int backGoodsAudit(Long id, String modifiedTime) {
        return adminGoodsMapper.backGoodsAudit(id, modifiedTime);
    }

    @Override
    public int deleteGoodsAudit(Long id, String modifiedTime) {
        return adminGoodsMapper.deleteGoodsAudit(id, modifiedTime);
    }

    @Override
    public PageVo<AdminDonationInfoVo> getDonationInfo(AdminDonationInfoQueryDto adminDonationInfoQueryDto) {
        PageHelper.startPage(adminDonationInfoQueryDto.getPageNum(), adminDonationInfoQueryDto.getPageSize());
        List<AdminDonationInfoVo> donationInfos = adminGoodsMapper.getDonationInfo(adminDonationInfoQueryDto);
        for (AdminDonationInfoVo donationInfo : donationInfos) {
            String address = donationInfo.getAddress();
            if (address != null) {
                String newAddress = address.replace("/", "");
                donationInfo.setAddress(newAddress);
            }
        }
        PageInfo<AdminDonationInfoVo> donationPageInfo = new PageInfo<>(donationInfos);
        return new PageVo<>(donationPageInfo.getTotal(), donationInfos);
    }

    @Override
    public PageVo<GoodsVo> instockDetailInfo(Long id, String goodsRecordNum, Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<GoodsVo> instockDetailInfo = adminGoodsMapper.instockDetailInfo(goodsRecordNum);
        PageInfo<GoodsVo> pageInfo = new PageInfo<>(instockDetailInfo);
        PageVo<GoodsVo> instockDetailPageInfo = new PageVo<>(pageInfo.getTotal(), instockDetailInfo);
        return instockDetailPageInfo;
    }

    @Override
    public int instockAdd(GoodsInstockVo goodsInstockVo) {
        String goodsRecordNum = goodsInstockVo.getGoodsRecordNum();
        List<GoodsVo> instockGoodsList = goodsInstockVo.getInstockGoodsList();
        int count = 0;
        //随机生成捐赠单号
        String goodsInstockNum = UUID.randomUUID().toString().substring(0, 32).replace("-", "");
        for (GoodsVo goodsVo : instockGoodsList) {
            GoodsStock goodsStock = new GoodsStock();
            BeanUtils.copyProperties(goodsVo, goodsStock);
            goodsStock.setGoodsRecordNum(goodsRecordNum);
            goodsStock.setGoodsInstockNum(goodsInstockNum);
            goodsStock.setCreateTime(new Date());
            int i = adminGoodsMapper.insertStock(goodsStock);
            count += i;
        }
        adminGoodsMapper.updateInstockStatus(goodsRecordNum);
        return count;
    }
}
