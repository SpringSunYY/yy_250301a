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
import com.lz.manage.mapper.StoreInfoMapper;
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.service.IStoreInfoService;
import com.lz.manage.model.dto.storeInfo.StoreInfoQuery;
import com.lz.manage.model.vo.storeInfo.StoreInfoVo;

/**
 * 店铺信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-03
 */
@Service
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo> implements IStoreInfoService
{
    @Resource
    private StoreInfoMapper storeInfoMapper;

    //region mybatis代码
    /**
     * 查询店铺信息
     * 
     * @param id 店铺信息主键
     * @return 店铺信息
     */
    @Override
    public StoreInfo selectStoreInfoById(Long id)
    {
        return storeInfoMapper.selectStoreInfoById(id);
    }

    /**
     * 查询店铺信息列表
     * 
     * @param storeInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    public List<StoreInfo> selectStoreInfoList(StoreInfo storeInfo)
    {
        return storeInfoMapper.selectStoreInfoList(storeInfo);
    }

    /**
     * 新增店铺信息
     * 
     * @param storeInfo 店铺信息
     * @return 结果
     */
    @Override
    public int insertStoreInfo(StoreInfo storeInfo)
    {
        storeInfo.setCreateTime(DateUtils.getNowDate());
        return storeInfoMapper.insertStoreInfo(storeInfo);
    }

    /**
     * 修改店铺信息
     * 
     * @param storeInfo 店铺信息
     * @return 结果
     */
    @Override
    public int updateStoreInfo(StoreInfo storeInfo)
    {
        storeInfo.setUpdateTime(DateUtils.getNowDate());
        return storeInfoMapper.updateStoreInfo(storeInfo);
    }

    /**
     * 批量删除店铺信息
     * 
     * @param ids 需要删除的店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreInfoByIds(Long[] ids)
    {
        return storeInfoMapper.deleteStoreInfoByIds(ids);
    }

    /**
     * 删除店铺信息信息
     * 
     * @param id 店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreInfoById(Long id)
    {
        return storeInfoMapper.deleteStoreInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<StoreInfo> getQueryWrapper(StoreInfoQuery storeInfoQuery){
        QueryWrapper<StoreInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = storeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long principalId = storeInfoQuery.getPrincipalId();
        queryWrapper.eq( StringUtils.isNotNull(principalId),"principal_id",principalId);

        Long operationId = storeInfoQuery.getOperationId();
        queryWrapper.eq( StringUtils.isNotNull(operationId),"operation_id",operationId);

        Long serviceId = storeInfoQuery.getServiceId();
        queryWrapper.eq( StringUtils.isNotNull(serviceId),"service_id",serviceId);

        String storeName = storeInfoQuery.getStoreName();
        queryWrapper.like(StringUtils.isNotEmpty(storeName) ,"store_name",storeName);

        String status = storeInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String storePhone = storeInfoQuery.getStorePhone();
        queryWrapper.eq(StringUtils.isNotEmpty(storePhone) ,"store_phone",storePhone);

        String alipayAccount = storeInfoQuery.getAlipayAccount();
        queryWrapper.like(StringUtils.isNotEmpty(alipayAccount) ,"alipay_account",alipayAccount);

        String alipayPhone = storeInfoQuery.getAlipayPhone();
        queryWrapper.eq(StringUtils.isNotEmpty(alipayPhone) ,"alipay_phone",alipayPhone);

        String alipayAuthenticator = storeInfoQuery.getAlipayAuthenticator();
        queryWrapper.like(StringUtils.isNotEmpty(alipayAuthenticator) ,"alipay_authenticator",alipayAuthenticator);

        String alipayProvider = storeInfoQuery.getAlipayProvider();
        queryWrapper.like(StringUtils.isNotEmpty(alipayProvider) ,"alipay_provider",alipayProvider);

        String serverIp = storeInfoQuery.getServerIp();
        queryWrapper.eq(StringUtils.isNotEmpty(serverIp) ,"server_ip",serverIp);

        String channels = storeInfoQuery.getChannels();
        queryWrapper.like(StringUtils.isNotEmpty(channels) ,"channels",channels);

        String isCommissionSettlement = storeInfoQuery.getIsCommissionSettlement();
        queryWrapper.eq(StringUtils.isNotEmpty(isCommissionSettlement) ,"is_commission_settlement",isCommissionSettlement);

        Date departureTime = storeInfoQuery.getDepartureTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginDepartureTime"))&&StringUtils.isNotNull(params.get("endDepartureTime")),"departure_time",params.get("beginDepartureTime"),params.get("endDepartureTime"));

        String businessLicenseName = storeInfoQuery.getBusinessLicenseName();
        queryWrapper.like(StringUtils.isNotEmpty(businessLicenseName) ,"business_license_name",businessLicenseName);

        String legalPerson = storeInfoQuery.getLegalPerson();
        queryWrapper.like(StringUtils.isNotEmpty(legalPerson) ,"legal_person",legalPerson);

        Date expireTime = storeInfoQuery.getExpireTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginExpireTime"))&&StringUtils.isNotNull(params.get("endExpireTime")),"expire_time",params.get("beginExpireTime"),params.get("endExpireTime"));

        String isOrderAccomplish = storeInfoQuery.getIsOrderAccomplish();
        queryWrapper.eq(StringUtils.isNotEmpty(isOrderAccomplish) ,"is_order_accomplish",isOrderAccomplish);

        String isBonaFideRedemption = storeInfoQuery.getIsBonaFideRedemption();
        queryWrapper.eq(StringUtils.isNotEmpty(isBonaFideRedemption) ,"is_bona_fide_redemption",isBonaFideRedemption);

        String isBail = storeInfoQuery.getIsBail();
        queryWrapper.eq(StringUtils.isNotEmpty(isBail) ,"is_bail",isBail);

        String isAlipay = storeInfoQuery.getIsAlipay();
        queryWrapper.eq(StringUtils.isNotEmpty(isAlipay) ,"is_alipay",isAlipay);

        Long deptId = storeInfoQuery.getDeptId();
        queryWrapper.eq( StringUtils.isNotNull(deptId),"dept_id",deptId);

        Long userId = storeInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = storeInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = storeInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        Date updateTime = storeInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime"))&&StringUtils.isNotNull(params.get("endUpdateTime")),"update_time",params.get("beginUpdateTime"),params.get("endUpdateTime"));

        return queryWrapper;
    }

    @Override
    public List<StoreInfoVo> convertVoList(List<StoreInfo> storeInfoList) {
        if (StringUtils.isEmpty(storeInfoList)) {
            return Collections.emptyList();
        }
        return storeInfoList.stream().map(StoreInfoVo::objToVo).collect(Collectors.toList());
    }

}
