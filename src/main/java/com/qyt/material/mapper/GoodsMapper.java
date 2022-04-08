package com.qyt.material.mapper;

import com.qyt.material.pojo.Goods;
import com.qyt.material.pojo.GoodsCategory;
import com.qyt.material.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GoodsMapper extends Mapper<Goods> {
    // 获取物品分类
    List<GoodsCategory> findGoodsCategoryList();

    // 根据条件获取所有的物品列表
    List<GoodsVo> findGoodsList(@Param("goodsCategoryId") Long goodsCategoryId, @Param("goodsName") String goodsName, @Param("status") Integer status, @Param("uid") String uid);

    GoodsVo getEditGoods(@Param("id") Long id);

    int updateGoodsById(GoodsVo goodsVo);

    int removeGoods(Long id);

    int backGoods(Long id);

    int deleteGoods(Long id);

    GoodsVo getShowGoods(@Param("goodsId") Long goodsId);

    List<GoodsVo> statisticsGoods(String uid);
}
