package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PurchaseAccountInfoMapper;
import com.lz.manage.model.domain.PurchaseAccountInfo;
import com.lz.manage.service.IPurchaseAccountInfoService;
import com.lz.manage.model.dto.purchaseAccountInfo.PurchaseAccountInfoQuery;
import com.lz.manage.model.vo.purchaseAccountInfo.PurchaseAccountInfoVo;

/**
 * 采购账号信息Service业务层处理
 * 
 * @author YY
 * @date 2025-03-03
 */
@Service
public class PurchaseAccountInfoServiceImpl extends ServiceImpl<PurchaseAccountInfoMapper, PurchaseAccountInfo> implements IPurchaseAccountInfoService
{
    @Resource
    private PurchaseAccountInfoMapper purchaseAccountInfoMapper;

    //region mybatis代码
    /**
     * 查询采购账号信息
     * 
     * @param id 采购账号信息主键
     * @return 采购账号信息
     */
    @Override
    public PurchaseAccountInfo selectPurchaseAccountInfoById(Long id)
    {
        return purchaseAccountInfoMapper.selectPurchaseAccountInfoById(id);
    }

    /**
     * 查询采购账号信息列表
     * 
     * @param purchaseAccountInfo 采购账号信息
     * @return 采购账号信息
     */
    @Override
    public List<PurchaseAccountInfo> selectPurchaseAccountInfoList(PurchaseAccountInfo purchaseAccountInfo)
    {
        return purchaseAccountInfoMapper.selectPurchaseAccountInfoList(purchaseAccountInfo);
    }

    /**
     * 新增采购账号信息
     * 
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    @Override
    public int insertPurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo)
    {
        purchaseAccountInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseAccountInfoMapper.insertPurchaseAccountInfo(purchaseAccountInfo);
    }

    /**
     * 修改采购账号信息
     * 
     * @param purchaseAccountInfo 采购账号信息
     * @return 结果
     */
    @Override
    public int updatePurchaseAccountInfo(PurchaseAccountInfo purchaseAccountInfo)
    {
        purchaseAccountInfo.setUpdateTime(DateUtils.getNowDate());
        return purchaseAccountInfoMapper.updatePurchaseAccountInfo(purchaseAccountInfo);
    }

    /**
     * 批量删除采购账号信息
     * 
     * @param ids 需要删除的采购账号信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseAccountInfoByIds(Long[] ids)
    {
        return purchaseAccountInfoMapper.deletePurchaseAccountInfoByIds(ids);
    }

    /**
     * 删除采购账号信息信息
     * 
     * @param id 采购账号信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseAccountInfoById(Long id)
    {
        return purchaseAccountInfoMapper.deletePurchaseAccountInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<PurchaseAccountInfo> getQueryWrapper(PurchaseAccountInfoQuery purchaseAccountInfoQuery){
        QueryWrapper<PurchaseAccountInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseAccountInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String nickName = purchaseAccountInfoQuery.getNickName();
        queryWrapper.like(StringUtils.isNotEmpty(nickName) ,"nick_name",nickName);

        String purchaseAccount = purchaseAccountInfoQuery.getPurchaseAccount();
        queryWrapper.like(StringUtils.isNotEmpty(purchaseAccount) ,"purchase_account",purchaseAccount);

        Long userId = purchaseAccountInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = purchaseAccountInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = purchaseAccountInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = purchaseAccountInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        Long deptId = purchaseAccountInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        return queryWrapper;
    }

    @Override
    public List<PurchaseAccountInfoVo> convertVoList(List<PurchaseAccountInfo> purchaseAccountInfoList) {
        if (StringUtils.isEmpty(purchaseAccountInfoList)) {
            return Collections.emptyList();
        }
        return purchaseAccountInfoList.stream().map(PurchaseAccountInfoVo::objToVo).collect(Collectors.toList());
    }

}
