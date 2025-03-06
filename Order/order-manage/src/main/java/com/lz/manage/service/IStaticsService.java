package com.lz.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.manage.model.statics.StaticsDto;
import com.lz.manage.model.statics.StaticsVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface IStaticsService   {
    public StaticsVo<StaticsVo<BigDecimal>> day(StaticsDto staticsDto);
}
