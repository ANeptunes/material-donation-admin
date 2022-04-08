package com.qyt.material.service;

import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.pojo.Goods;
import com.qyt.material.pojo.GoodsCategory;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;

import java.util.List;

public interface GoodsService {

    // 查找物品列表
    PageVo<GoodsVo> findGoodsList(GoodsQueryDto goodsQueryDto);

    // 获取商品类别
    List<GoodsCategory> findGoodsCategoryList();

    int addGoods(GoodsVo goodsVo);

    GoodsVo getEditGoods(Long id);

    int updateGoods(Long id, GoodsVo goodsVo);

    int removeGoods(Long id);

    int backGoods(Long id);

    GoodsVo getShowGoods(Long id);

    int deleteGoods(Long id);

    PageVo<GoodsVo> statisticsGoods(String uid, int pageNum, int pageSize);
}
