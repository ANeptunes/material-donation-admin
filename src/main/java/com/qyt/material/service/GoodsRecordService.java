package com.qyt.material.service;

import com.qyt.material.dto.GoodsRecordQueryDto;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.vo.GoodsRecordDetailVo;
import com.qyt.material.vo.PageVo;

public interface GoodsRecordService {
    PageVo<GoodsRecord> getRecordList(GoodsRecordQueryDto goodsRecordQueryDto);

    int removeGoodsRecord(Long id);

    int deleteGoodsRecord(Long id);

    GoodsRecordDetailVo detailGoodsRecord(Long id,String goodsRecordNum,Integer pageNum);
}
