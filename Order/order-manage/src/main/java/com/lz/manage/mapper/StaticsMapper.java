package com.lz.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lz.manage.model.statics.StaticsBaseRo;
import com.lz.manage.model.statics.StaticsDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Project: Order
 * Package: com.lz.manage.mapper
 * Author: YY
 * CreateTime: 2025-03-06  22:28
 * Description: StaticsMapper
 * Version: 1.0
 */
public interface StaticsMapper {
    List<StaticsBaseRo> getPurchaseOrderCount(StaticsDto statics);  // 集合参数);
}
