package com.qyt.material.controller;

import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.service.GoodsDonationService;
import com.qyt.material.service.GoodsService;
import com.qyt.material.vo.GoodsRecordVo;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestController
@RequestMapping("/donation")
public class GoodsDonationController {

    @Resource
    private GoodsDonationService goodsDonationService;

    @Resource
    private GoodsService goodsService;

    @GetMapping("/findDonationGoodsList")
    public Result findGoodsDonationList(@Valid GoodsQueryDto goodsQueryDto) {
        PageVo<GoodsVo> goodsList = goodsDonationService.findDonationGoodsList(goodsQueryDto);
        return Result.success(ResultCode.SUCCESS, goodsList);
    }

    @PostMapping("/add")
    public Result addGoodsDonation(@RequestBody GoodsRecordVo goodsRecordVo) {
        int i = goodsDonationService.addGoodsDonation(goodsRecordVo);
        if (i > 0) {
            return Result.success(ResultCode.SUCCESS, "捐赠成功");
        } else {
            return Result.success(ResultCode.FAILED, "捐赠失败");
        }
    }
}
