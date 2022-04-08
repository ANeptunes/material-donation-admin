package com.qyt.material.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qyt.material.dto.GoodsQueryDto;
import com.qyt.material.dto.GoodsRecordQueryDto;
import com.qyt.material.mapper.GoodsRecordMapper;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.service.GoodsRecordService;
import com.qyt.material.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsRecordServiceImpl implements GoodsRecordService {

    @Resource
    private GoodsRecordMapper goodsRecordMapper;

    @Override
    public PageVo<GoodsRecord> getRecordList(@RequestBody GoodsRecordQueryDto goodsRecordQueryDto) {
        PageHelper.startPage(goodsRecordQueryDto.getPageNum(), goodsRecordQueryDto.getPageSize());
        List<GoodsRecord> goodsRecords = goodsRecordMapper.selectGoodsRecord(goodsRecordQueryDto);
        for (GoodsRecord goodsRecord : goodsRecords) {
            String address = goodsRecord.getAddress();
            if (address != null) {
                String newAddress = address.replace("/", "");
                goodsRecord.setAddress(newAddress);
            }
        }
        PageInfo<GoodsRecord> goodsRecordsPageInfo = new PageInfo<>(goodsRecords);
        return new PageVo<>(goodsRecordsPageInfo.getTotal(), goodsRecords);
    }

    @Override
    public int removeGoodsRecord(Long id) {
        return goodsRecordMapper.removeRecord(id);
    }

    @Override
    public int deleteGoodsRecord(Long id) {
        return goodsRecordMapper.deleteGoodsRecord(id);
    }

    @Override
    public GoodsRecordDetailVo detailGoodsRecord(Long id, String goodsRecordNum, Integer pageNum) {
        GoodsDetailInfoVo goodsRecord = goodsRecordMapper.detailGoodsRecord(id);
        GoodsRecordDetailVo goodsRecordDetailVo = new GoodsRecordDetailVo();
        goodsRecordDetailVo.setName(goodsRecord.getName());
        goodsRecordDetailVo.setPhone(goodsRecord.getPhone());
        goodsRecordDetailVo.setAddress(goodsRecord.getAddress());
        goodsRecordDetailVo.setCreateTime(goodsRecord.getCreateTime());
        PageHelper.startPage(pageNum,3);
        List<GoodsDetailItemsVo> goodsDetailItems = goodsRecordMapper.detailGoodsRecordItems(goodsRecordNum);
        PageInfo<GoodsDetailItemsVo> pageGoodsDetailItems = new PageInfo<>(goodsDetailItems);
        PageVo<GoodsDetailItemsVo> goodsDetailItemsPageVo = new PageVo<>(pageGoodsDetailItems.getTotal(), goodsDetailItems);
        goodsRecordDetailVo.setGoodsDetailItems(goodsDetailItemsPageVo);
        return goodsRecordDetailVo;
    }
}
