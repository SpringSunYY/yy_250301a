package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.DictUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.StaticsMapper;
import com.lz.manage.model.enums.OrderTypeEnum;
import com.lz.manage.model.statics.StaticsBaseRo;
import com.lz.manage.model.statics.StaticsDto;
import com.lz.manage.model.statics.StaticsVo;
import com.lz.manage.service.IStaticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Project: Order
 * Package: com.lz.manage.service.impl
 * Author: YY
 * CreateTime: 2025-03-06  22:30
 * Description: StaticsServiceImpl
 * Version: 1.0
 */
@Service
public class StaticsServiceImpl implements IStaticsService {
    @Resource
    private StaticsMapper staticsMapper;

    @Override
    public StaticsVo<StaticsVo<BigDecimal>> day(StaticsDto statics) {
        //如果开始时间或者结束时间为空
        if (StringUtils.isNull(statics.getStartTime()) || StringUtils.isNull(statics.getEndTime())) {
            throw new ServiceException("必须输入开始时间和结束时间");
        }
        //查询到每日数量
        statics.setOrderType(OrderTypeEnum.ORDER_TYPE_1.getValue());
        List<StaticsBaseRo> onlineStatics = staticsMapper.getPurchaseOrderCount(statics);
        statics.setOrderType(OrderTypeEnum.ORDER_TYPE_2.getValue());
        List<StaticsBaseRo> offlineStatics = staticsMapper.getPurchaseOrderCount(statics);

        //构造返回
        StaticsVo<StaticsVo<BigDecimal>> bigDecimalStaticsVo = new StaticsVo<>();
        List<StaticsVo<BigDecimal>> values = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        StaticsVo<BigDecimal> onlineStaticsVo = new StaticsVo<>();
        ArrayList<String> onlineNames = new ArrayList<>();
        ArrayList<BigDecimal> onlineValues = new ArrayList<>();

        StaticsVo<BigDecimal> offlineStaticsVo = new StaticsVo<>();
        ArrayList<String> offlineNames = new ArrayList<>();
        ArrayList<BigDecimal> offlineValues = new ArrayList<>();
        for (int i = 0; i < onlineStatics.size(); i++) {
            names.add(onlineStatics.get(i).getName());
            onlineNames.add(onlineStatics.get(i).getName());
            onlineValues.add(onlineStatics.get(i).getValue());

            offlineNames.add(offlineStatics.get(i).getName());
            offlineValues.add(offlineStatics.get(i).getValue());
        }
        onlineStaticsVo.setNames(onlineNames);
        onlineStaticsVo.setTotals(onlineValues);
        offlineStaticsVo.setNames(offlineNames);
        offlineStaticsVo.setTotals(offlineValues);

        values.add(onlineStaticsVo);
        values.add(offlineStaticsVo);
        bigDecimalStaticsVo.setTotals(values);
        bigDecimalStaticsVo.setNames(names);
        return bigDecimalStaticsVo;
    }
}
