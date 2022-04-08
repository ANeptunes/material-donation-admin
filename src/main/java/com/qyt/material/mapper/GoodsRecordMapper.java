package com.qyt.material.mapper;

import com.qyt.material.dto.GoodsRecordQueryDto;
import com.qyt.material.pojo.GoodsRecord;
import com.qyt.material.vo.GoodsDetailInfoVo;
import com.qyt.material.vo.GoodsDetailItemsVo;
import com.qyt.material.vo.GoodsRecordDetailVo;
import com.qyt.material.vo.GoodsVo;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

public interface GoodsRecordMapper extends BaseMapper<GoodsRecord> {

    List<GoodsRecord> selectGoodsRecord(GoodsRecordQueryDto goodsRecordQueryDto);

    int removeRecord(Long id);

    int deleteGoodsRecord(Long id);

    GoodsDetailInfoVo detailGoodsRecord(Long id);

    List<GoodsDetailItemsVo> detailGoodsRecordItems(String goodsRecordNum);
}
