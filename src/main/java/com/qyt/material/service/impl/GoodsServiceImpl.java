package com.qyt.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.mapper.GoodsDonationMapper;
import com.qyt.material.mapper.GoodsMapper;
import com.qyt.material.pojo.Goods;
import com.qyt.material.pojo.GoodsCategory;
import com.qyt.material.service.GoodsService;
import com.qyt.material.vo.GoodsVo;
import com.qyt.material.vo.PageVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsDonationMapper goodsDonationMapper;

    /**
     * 查找所有物品
     */
    @Override
    public PageVo<GoodsVo> findGoodsList(GoodsQueryDto goodsQueryDto) {
        PageHelper.startPage(goodsQueryDto.getPageNum(), goodsQueryDto.getPageSize());
        List<GoodsVo> goodsList = goodsMapper.findGoodsList(goodsQueryDto.getGoodsCategoryId(), goodsQueryDto.getGoodsName(), goodsQueryDto.getStatus(), goodsQueryDto.getUid());
        PageInfo<GoodsVo> goodsPageInfo = new PageInfo<>(goodsList);
        return new PageVo<>(goodsPageInfo.getTotal(), goodsList);
    }

    @Override
    public List<GoodsCategory> findGoodsCategoryList() {
        return goodsMapper.findGoodsCategoryList();
    }

    @Override
    @Transactional
    public int addGoods(GoodsVo goodsVo) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo, goods);
        goods.setGoodsNum(UUID.randomUUID().toString().substring(0, 18));
        goods.setCreateTime(new Date());
        goods.setStatus(2);
        return goodsMapper.insert(goods);
    }

    @Override
    public GoodsVo getEditGoods(Long id) {
        return goodsMapper.getEditGoods(id);
    }

    @Override
    @Transactional
    public int updateGoods(Long id, GoodsVo goodsVo) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo, goods);
        goods.setModifiedTime(new Date());
        return goodsMapper.updateByPrimaryKey(goods);
    }

    @Override
    @Transactional
    public int removeGoods(Long id) {
        return goodsMapper.removeGoods(id);
    }

    @Override
    @Transactional
    public int backGoods(Long id) {
        return goodsMapper.backGoods(id);
    }

    @Override
    public GoodsVo getShowGoods(Long id) {
        return goodsMapper.getShowGoods(id);
    }

    @Override
    @Transactional
    public int deleteGoods(Long id) {
        return goodsMapper.deleteGoods(id);
        // return goodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVo<GoodsVo> statisticsGoods(String uid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsVo> goodsList = goodsMapper.statisticsGoods(uid);
        PageInfo<GoodsVo> goodsPageInfo = new PageInfo<>(goodsList);
        return new PageVo<>(goodsPageInfo.getTotal(), goodsList);
    }
}
