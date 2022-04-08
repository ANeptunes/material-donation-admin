package com.qyt.material.controller;

import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.dto.GoodsRecordQueryDto;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.service.GoodsRecordService;
import com.qyt.material.vo.GoodsRecordDetailVo;
import com.qyt.material.vo.PageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestController
@RequestMapping("/record")
public class GoodsRecordController {

    @Resource
    private GoodsRecordService goodsRecordService;

    @GetMapping("/list")
    public Result getRecordList(@Valid GoodsRecordQueryDto goodsRecordQueryDto) {
        PageVo<GoodsRecord> recordList = goodsRecordService.getRecordList(goodsRecordQueryDto);
        return Result.success(ResultCode.SUCCESS, "查询成功", recordList);
    }

    @PostMapping("/remove/{id}")
    public Result removeGoodsRecord(@PathVariable("id") Long id) {
        int i = goodsRecordService.removeGoodsRecord(id);
        if (i > 0) {
            return Result.success(ResultCode.SUCCESS, "移入回收站成功");
        } else {
            return Result.success(ResultCode.FAILED, "移入回收站失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteGoodsRecord(@PathVariable("id") Long id) {
        int i = goodsRecordService.deleteGoodsRecord(id);
        if (i > 0) {
            return Result.success(ResultCode.SUCCESS, "移入回收站成功");
        } else {
            return Result.success(ResultCode.FAILED, "移入回收站失败");
        }
    }

    @GetMapping("/detail/{id}")
    public Result detailGoodsRecord(@PathVariable("id") Long id, @RequestParam("goodsRecordNum") String goodsRecordNum, @RequestParam("pageNum") Integer pageNum) {
        GoodsRecordDetailVo goodsRecordDetail = goodsRecordService.detailGoodsRecord(id, goodsRecordNum, pageNum);
        System.out.println(goodsRecordDetail);
        if (!"".equals(goodsRecordDetail)) {
            return Result.success(ResultCode.SUCCESS, "查询成功", goodsRecordDetail);
        } else {
            return Result.success(ResultCode.FAILED, "查询失败");
        }
    }


}
