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
import com.lz.manage.mapper.PurchaseOrderInfoMapper;
import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;

/**
 * 采购发货信息Service业务层处理
 * 
 * @author YY
 * @date 2025-03-03
 */
@Service
public class PurchaseOrderInfoServiceImpl extends ServiceImpl<PurchaseOrderInfoMapper, PurchaseOrderInfo> implements IPurchaseOrderInfoService
{
    @Resource
    private PurchaseOrderInfoMapper purchaseOrderInfoMapper;

    //region mybatis代码
    /**
     * 查询采购发货信息
     * 
     * @param id 采购发货信息主键
     * @return 采购发货信息
     */
    @Override
    public PurchaseOrderInfo selectPurchaseOrderInfoById(Long id)
    {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoById(id);
    }

    /**
     * 查询采购发货信息列表
     * 
     * @param purchaseOrderInfo 采购发货信息
     * @return 采购发货信息
     */
    @Override
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo)
    {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoList(purchaseOrderInfo);
    }

    /**
     * 新增采购发货信息
     * 
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    @Override
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo)
    {
        purchaseOrderInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseOrderInfoMapper.insertPurchaseOrderInfo(purchaseOrderInfo);
    }

    /**
     * 修改采购发货信息
     * 
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    @Override
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo)
    {
        purchaseOrderInfo.setUpdateTime(DateUtils.getNowDate());
        return purchaseOrderInfoMapper.updatePurchaseOrderInfo(purchaseOrderInfo);
    }

    /**
     * 批量删除采购发货信息
     * 
     * @param ids 需要删除的采购发货信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseOrderInfoByIds(Long[] ids)
    {
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByIds(ids);
    }

    /**
     * 删除采购发货信息信息
     * 
     * @param id 采购发货信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseOrderInfoById(Long id)
    {
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery){
        QueryWrapper<PurchaseOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = purchaseOrderInfoQuery.getOrderNumber();
        queryWrapper.like(StringUtils.isNotEmpty(orderNumber) ,"order_number",orderNumber);

        String orderType = purchaseOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType) ,"order_type",orderType);

        Date purchaseTime = purchaseOrderInfoQuery.getPurchaseTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginPurchaseTime"))&&StringUtils.isNotNull(params.get("endPurchaseTime")),"purchase_time",params.get("beginPurchaseTime"),params.get("endPurchaseTime"));

        Long storeId = purchaseOrderInfoQuery.getStoreId();
        queryWrapper.eq( StringUtils.isNotNull(storeId),"store_id",storeId);

        String buyerNumber = purchaseOrderInfoQuery.getBuyerNumber();
        queryWrapper.like(StringUtils.isNotEmpty(buyerNumber) ,"buyer_number",buyerNumber);

        String purchaseChannelType = purchaseOrderInfoQuery.getPurchaseChannelType();
        queryWrapper.eq(StringUtils.isNotEmpty(purchaseChannelType) ,"purchase_channel_type",purchaseChannelType);

        String purchaseChannelDetail = purchaseOrderInfoQuery.getPurchaseChannelDetail();
        queryWrapper.eq(StringUtils.isNotEmpty(purchaseChannelDetail) ,"purchase_channel_detail",purchaseChannelDetail);

        String purchaseAccount = purchaseOrderInfoQuery.getPurchaseAccount();
        queryWrapper.eq(StringUtils.isNotEmpty(purchaseAccount) ,"purchase_account",purchaseAccount);

        String purchaseOrder = purchaseOrderInfoQuery.getPurchaseOrder();
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder) ,"purchase_order",purchaseOrder);

        String supplierName = purchaseOrderInfoQuery.getSupplierName();
        queryWrapper.like(StringUtils.isNotEmpty(supplierName) ,"supplier_name",supplierName);

        String shipmentsOrder = purchaseOrderInfoQuery.getShipmentsOrder();
        queryWrapper.like(StringUtils.isNotEmpty(shipmentsOrder) ,"shipments_order",shipmentsOrder);

        String hasReturn = purchaseOrderInfoQuery.getHasReturn();
        queryWrapper.eq(StringUtils.isNotEmpty(hasReturn) ,"has_return",hasReturn);

        String hasBP = purchaseOrderInfoQuery.getHasBP();
        queryWrapper.eq(StringUtils.isNotEmpty(hasBP) ,"has_b_p",hasBP);

        Long userId = purchaseOrderInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = purchaseOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = purchaseOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = purchaseOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        Long deptId = purchaseOrderInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        return queryWrapper;
    }

    @Override
    public List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList) {
        if (StringUtils.isEmpty(purchaseOrderInfoList)) {
            return Collections.emptyList();
        }
        return purchaseOrderInfoList.stream().map(PurchaseOrderInfoVo::objToVo).collect(Collectors.toList());
    }

}
