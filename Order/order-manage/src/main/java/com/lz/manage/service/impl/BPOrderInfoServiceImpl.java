package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.BPOrderInfoMapper;
import com.lz.manage.model.domain.BPOrderInfo;
import com.lz.manage.service.IBPOrderInfoService;
import com.lz.manage.model.dto.bPOrderInfo.BPOrderInfoQuery;
import com.lz.manage.model.vo.bPOrderInfo.BPOrderInfoVo;

/**
 * 白嫖订单信息Service业务层处理
 * 
 * @author YY
 * @date 2025-03-03
 */
@Service
public class BPOrderInfoServiceImpl extends ServiceImpl<BPOrderInfoMapper, BPOrderInfo> implements IBPOrderInfoService
{
    @Resource
    private BPOrderInfoMapper bPOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询白嫖订单信息
     * 
     * @param id 白嫖订单信息主键
     * @return 白嫖订单信息
     */
    @Override
    public BPOrderInfo selectBPOrderInfoById(Long id)
    {
        return bPOrderInfoMapper.selectBPOrderInfoById(id);
    }

    /**
     * 查询白嫖订单信息列表
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 白嫖订单信息
     */
    @Override
    public List<BPOrderInfo> selectBPOrderInfoList(BPOrderInfo bPOrderInfo)
    {
        return bPOrderInfoMapper.selectBPOrderInfoList(bPOrderInfo);
    }

    /**
     * 新增白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    @Override
    public int insertBPOrderInfo(BPOrderInfo bPOrderInfo)
    {
        bPOrderInfo.setCreateTime(DateUtils.getNowDate());
        return bPOrderInfoMapper.insertBPOrderInfo(bPOrderInfo);
    }

    /**
     * 修改白嫖订单信息
     * 
     * @param bPOrderInfo 白嫖订单信息
     * @return 结果
     */
    @Override
    public int updateBPOrderInfo(BPOrderInfo bPOrderInfo)
    {
        bPOrderInfo.setUpdateTime(DateUtils.getNowDate());
        return bPOrderInfoMapper.updateBPOrderInfo(bPOrderInfo);
    }

    /**
     * 批量删除白嫖订单信息
     * 
     * @param ids 需要删除的白嫖订单信息主键
     * @return 结果
     */
    @Override
    public int deleteBPOrderInfoByIds(Long[] ids)
    {
        return bPOrderInfoMapper.deleteBPOrderInfoByIds(ids);
    }

    /**
     * 删除白嫖订单信息信息
     * 
     * @param id 白嫖订单信息主键
     * @return 结果
     */
    @Override
    public int deleteBPOrderInfoById(Long id)
    {
        return bPOrderInfoMapper.deleteBPOrderInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<BPOrderInfo> getQueryWrapper(BPOrderInfoQuery bPOrderInfoQuery){
        QueryWrapper<BPOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = bPOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = bPOrderInfoQuery.getOrderNumber();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNumber) ,"order_number",orderNumber);

        String orderType = bPOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType) ,"order_type",orderType);

        Long storeId = bPOrderInfoQuery.getStoreId();
        queryWrapper.eq( StringUtils.isNotNull(storeId),"store_id",storeId);

        Date bPTime = bPOrderInfoQuery.getBPTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginBPTime"))&&StringUtils.isNotNull(params.get("endBPTime")),"b_p_time",params.get("beginBPTime"),params.get("endBPTime"));

        Date afterSaleTime = bPOrderInfoQuery.getAfterSaleTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAfterSaleTime"))&&StringUtils.isNotNull(params.get("endAfterSaleTime")),"after_sale_time",params.get("beginAfterSaleTime"),params.get("endAfterSaleTime"));

        Long userId = bPOrderInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = bPOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = bPOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = bPOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        Long deptId = bPOrderInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        return queryWrapper;
    }

    @Override
    public List<BPOrderInfoVo> convertVoList(List<BPOrderInfo> bPOrderInfoList) {
        if (StringUtils.isEmpty(bPOrderInfoList)) {
            return Collections.emptyList();
        }
        return bPOrderInfoList.stream().map(BPOrderInfoVo::objToVo).collect(Collectors.toList());
    }

}
