package com.qyt.material.controller.admin;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.dto.AdminDonationInfoQueryDto;
import com.qyt.material.dto.AdminGoodsQueryDto;
import com.qyt.material.pojo.Goods;
import com.qyt.material.service.AdminGoodsService;
import com.qyt.material.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/3/20 18:39
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

    @Resource
    private AdminGoodsService adminGoodsService;

    /**
     * 获取未审核物品列表
     *
     * @return 所有未审核物品列表
     */
    @GetMapping("/getNotAuditGoods")
    public Result getNotAuditGoods(@Valid AdminGoodsQueryDto goodsQueryDto) {
        PageVo<AdminGoodsVo> goods = adminGoodsService.getNotAuditGoods(goodsQueryDto);
        return Result.success(ResultCode.SUCCESS, "查询成功", goods);
    }

    /**
     * 通过审核
     *
     * @param id 物品id
     * @return 审核状态
     */
    @PostMapping("/passGoodsAudit/{id}")
    public Result passGoodsAudit(@PathVariable("id") Long id) {
        Calendar calendar = Calendar.getInstance();
        Date auditTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditTimeFormat = sdf.format(auditTime);
        int count = adminGoodsService.passGoodsAudit(id, auditTimeFormat);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "审核通过");
        }
        return Result.failed(ResultCode.FAILED, "审核失败");
    }

    /**
     * 拒绝审核
     *
     * @param id 物品id
     * @return 拒绝审核状态
     */
    @PostMapping("/notPassGoodsAudit/{id}")
    public Result notPassGoodsAudit(@PathVariable("id") Long id) {
        Calendar calendar = Calendar.getInstance();
        Date auditTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditTimeFormat = sdf.format(auditTime);
        int count = adminGoodsService.notPassGoodsAudit(id, auditTimeFormat);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "审核未通过");
        }
        return Result.failed(ResultCode.FAILED, "审核失败");
    }

    /**
     * 撤销审核
     *
     * @param id 物品id
     * @return 撤销审核状态
     */
    @PostMapping("/backGoodsAudit/{id}")
    public Result backGoodsAudit(@PathVariable("id") Long id) {
        Calendar calendar = Calendar.getInstance();
        Date auditTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditTimeFormat = sdf.format(auditTime);
        int count = adminGoodsService.backGoodsAudit(id, auditTimeFormat);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "撤回审核成功");
        }
        return Result.failed(ResultCode.FAILED, "撤回审核失败");
    }

    /**
     * 删除物品审核
     *
     * @param id 物品id
     * @return 删除物品审核状态
     */
    @PostMapping("/deleteGoodsAudit/{id}")
    public Result deleteGoodsAudit(@PathVariable("id") Long id) {
        Calendar calendar = Calendar.getInstance();
        Date auditTime = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditTimeFormat = sdf.format(auditTime);
        int count = adminGoodsService.deleteGoodsAudit(id, auditTimeFormat);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "删除成功");
        }
        return Result.failed(ResultCode.FAILED, "删除失败");
    }

    /**
     * 捐赠信息
     *
     * @param adminDonationInfoQueryDto 捐赠信息查询
     * @return 捐赠信息
     */
    @GetMapping("/donationInfo")
    public Result getDonationInfo(@Valid AdminDonationInfoQueryDto adminDonationInfoQueryDto) {
        PageVo<AdminDonationInfoVo> donationInfo = adminGoodsService.getDonationInfo(adminDonationInfoQueryDto);
        return Result.success(ResultCode.SUCCESS, "查询成功", donationInfo);
    }

    @GetMapping("/instockDetail/{id}")
    public Result instockDetailInfo(@PathVariable("id") Long id, @RequestParam("goodsRecordNum") String goodsRecordNum, @RequestParam("pageNum") Integer pageNum) {
        PageVo<GoodsVo> goodsRecordDetail = adminGoodsService.instockDetailInfo(id, goodsRecordNum, pageNum);
        if (ObjectUtil.isNotNull(goodsRecordDetail)) {
            JSONObject goodsRecordDetailData = new JSONObject();
            goodsRecordDetailData.put("goodsRecordNum", goodsRecordNum);
            goodsRecordDetailData.put("goodsRecordDetail", goodsRecordDetail);
            return Result.success(ResultCode.SUCCESS, "查询成功", goodsRecordDetailData);
        } else {
            return Result.success(ResultCode.FAILED, "查询失败");
        }
    }

    @PostMapping("/instockAdd")
    public Result instockAdd(@RequestBody GoodsInstockVo goodsInstockVo) {
        int count = adminGoodsService.instockAdd(goodsInstockVo);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "更新成功");
        }
        return Result.success(ResultCode.FAILED, "查询失败");
    }
}
