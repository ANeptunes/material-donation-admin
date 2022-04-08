package com.qyt.material.controller;

import com.qyt.material.api.Result;
import com.qyt.material.api.ResultCode;
import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.pojo.GoodsCategory;
import com.qyt.material.service.GoodsService;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: QiuYongTu
 * @Date: 2022/1/24 9:31
 * @Version 1.0
 */

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    /**
     * 查找所有物品（已通过、待审核、未通过、回收站）
     *
     * @param goodsQueryDto
     * @return
     */
    @GetMapping("/findGoodsList")
    public Result findGoodList(@Valid GoodsQueryDto goodsQueryDto) {
        PageVo<GoodsVo> goodsInfoList = goodsService.findGoodsList(goodsQueryDto);
        return Result.success(ResultCode.SUCCESS, "查询成功", goodsInfoList);
    }

    /**
     * 查找物品分类分类
     *
     * @return 返回分类信息
     */
    @GetMapping("/findGoodsCategoryList")
    public Result findGoodsCategoryList() {
        List<GoodsCategory> goodsCategoryList = goodsService.findGoodsCategoryList();
        return Result.success(ResultCode.SUCCESS, goodsCategoryList);
    }

    /**
     * 登记物品信息
     *
     * @param goodsVo 登记提交的物品信息
     * @return 返回登记结果
     */
    @PostMapping("/add")
    public Result addGoods(@RequestBody GoodsVo goodsVo) {
        System.out.println(goodsVo);
        if (goodsVo.getAmount() <= 0) {
            return Result.failed(ResultCode.FAILED, "登记物品数量不能小于0");
        }
        int count = goodsService.addGoods(goodsVo);
        if (count > 0) {
            return Result.success(ResultCode.SUCCESS, "物品添加成功");
        }
        return Result.failed(ResultCode.FAILED, "物品添加失败");
    }

    /**
     * 根据id修改物品信息
     *
     * @param id 物品id
     * @return 返回修改的物品信息
     */
    @GetMapping("/edit/{id}")
    public Result getEditGoods(@PathVariable("id") Long id) {
        GoodsVo editGoods = goodsService.getEditGoods(id);
        System.out.println(editGoods);
        return Result.success(ResultCode.SUCCESS, editGoods);
    }

    @GetMapping("/show/{id}")
    public Result getShowGoods(@PathVariable("id") Long id) {
        GoodsVo showGoods = goodsService.getShowGoods(id);
        System.out.println(showGoods);
        return Result.success(ResultCode.SUCCESS, showGoods);
    }

    @PutMapping("/update/{id}")
    public Result updateGoods(@PathVariable("id") Long id, @RequestBody GoodsVo goodsVo) {
        goodsService.updateGoods(id, goodsVo);
        return Result.success(ResultCode.SUCCESS, "更新物品成功");
    }

    @DeleteMapping("/remove/{id}")
    public Result removeGoods(@PathVariable("id") Long id) {
        goodsService.removeGoods(id);
        return Result.success(ResultCode.SUCCESS, "移入回收站成功");
    }

    @PutMapping("/back/{id}")
    public Result backGoods(@PathVariable("id") Long id) {
        goodsService.backGoods(id);
        return Result.success(ResultCode.SUCCESS, "从回收站恢复成功");
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteGoods(@PathVariable("id") Long id) {
        goodsService.deleteGoods(id);
        return Result.success(ResultCode.SUCCESS, "从回收站恢复成功");
    }

    @GetMapping("/statisticsGoods")
    public Result statisticsGoods(@RequestParam("uid") String uid, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        PageVo<GoodsVo> goodsInfoList = goodsService.statisticsGoods(uid, pageNum, pageSize);
        return Result.success(ResultCode.SUCCESS, "查询成功", goodsInfoList);
    }
}
