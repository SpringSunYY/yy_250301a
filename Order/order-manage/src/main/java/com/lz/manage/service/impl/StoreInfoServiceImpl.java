package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class StoreInfoServiceImpl extends ServiceImpl<StoreInfoMapper, StoreInfo> implements IStoreInfoService {
    @Resource
    private StoreInfoMapper storeInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    //region mybatis代码

    /**
     * 查询店铺信息
     *
     * @param id 店铺信息主键
     * @return 店铺信息
     */
    @Override
    public StoreInfo selectStoreInfoById(Long id) {
        return storeInfoMapper.selectStoreInfoById(id);
    }

    /**
     * 查询店铺信息列表
     *
     * @param storeInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    public List<StoreInfo> selectStoreInfoList(StoreInfo storeInfo) {
        List<StoreInfo> storeInfos = storeInfoMapper.selectStoreInfoList(storeInfo);
        for (StoreInfo info : storeInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setPrincipalName(user.getUserName());
            }
            SysUser principalUser = userService.selectUserById(info.getPrincipalId());
            if (StringUtils.isNotNull(principalUser)) {
                info.setPrincipalName(principalUser.getUserName());
            } else {
                info.setPrincipalName("未绑定");
            }
            SysUser operationUser = userService.selectUserById(info.getOperationId());
            if (StringUtils.isNotNull(operationUser)) {
                info.setOperationName(operationUser.getUserName());
            } else {
                info.setOperationName("未绑定");
            }
            SysUser serviceUser = userService.selectUserById(info.getServiceId());
            if (StringUtils.isNotNull(serviceUser)) {
                info.setServiceName(serviceUser.getUserName());
            } else {
                info.setServiceName("未绑定");
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return storeInfos;
    }

    /**
     * 新增店铺信息
     *
     * @param storeInfo 店铺信息
     * @return 结果
     */
    @Override
    public int insertStoreInfo(StoreInfo storeInfo) {
        // 校验用户和部门是否存在
        checkUserAndDept(storeInfo);

        storeInfo.setUserId(SecurityUtils.getUserId());
        storeInfo.setCreateTime(DateUtils.getNowDate());
        try {
            return storeInfoMapper.insertStoreInfo(storeInfo);
        } catch (Exception e) {
            log.error("新增店铺信息失败", e);
            throw new ServiceException("新增店铺信息失败，请检查数据格式是否正确，部门或者对应用户是否存在！！！");
        }
    }

    /**
     * 修改店铺信息
     *
     * @param storeInfo 店铺信息
     * @return 结果
     */
    @Override
    public int updateStoreInfo(StoreInfo storeInfo) {
        // 校验用户和部门是否存在
        checkUserAndDept(storeInfo);
        storeInfo.setUpdateBy(SecurityUtils.getUsername());
        storeInfo.setUpdateTime(DateUtils.getNowDate());
        try {
            return storeInfoMapper.updateStoreInfo(storeInfo);
        } catch (Exception e) {
            log.error("修改店铺信息失败", e);
            throw new ServiceException("修改店铺信息失败，请检查数据格式是否正确，部门或者对应用户是否存在！！！");
        }
    }

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreInfoByIds(Long[] ids) {
        return storeInfoMapper.deleteStoreInfoByIds(ids);
    }

    /**
     * 删除店铺信息信息
     *
     * @param id 店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteStoreInfoById(Long id) {
        return storeInfoMapper.deleteStoreInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<StoreInfo> getQueryWrapper(StoreInfoQuery storeInfoQuery) {
        QueryWrapper<StoreInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = storeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long principalId = storeInfoQuery.getPrincipalId();
        queryWrapper.eq(StringUtils.isNotNull(principalId), "principal_id", principalId);

        Long operationId = storeInfoQuery.getOperationId();
        queryWrapper.eq(StringUtils.isNotNull(operationId), "operation_id", operationId);

        Long serviceId = storeInfoQuery.getServiceId();
        queryWrapper.eq(StringUtils.isNotNull(serviceId), "service_id", serviceId);

        String storeName = storeInfoQuery.getStoreName();
        queryWrapper.like(StringUtils.isNotEmpty(storeName), "store_name", storeName);

        String status = storeInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String storePhone = storeInfoQuery.getStorePhone();
        queryWrapper.eq(StringUtils.isNotEmpty(storePhone), "store_phone", storePhone);

        String alipayAccount = storeInfoQuery.getAlipayAccount();
        queryWrapper.like(StringUtils.isNotEmpty(alipayAccount), "alipay_account", alipayAccount);

        String alipayPhone = storeInfoQuery.getAlipayPhone();
        queryWrapper.eq(StringUtils.isNotEmpty(alipayPhone), "alipay_phone", alipayPhone);

        String alipayAuthenticator = storeInfoQuery.getAlipayAuthenticator();
        queryWrapper.like(StringUtils.isNotEmpty(alipayAuthenticator), "alipay_authenticator", alipayAuthenticator);

        String alipayProvider = storeInfoQuery.getAlipayProvider();
        queryWrapper.like(StringUtils.isNotEmpty(alipayProvider), "alipay_provider", alipayProvider);

        String serverIp = storeInfoQuery.getServerIp();
        queryWrapper.eq(StringUtils.isNotEmpty(serverIp), "server_ip", serverIp);

        String channels = storeInfoQuery.getChannels();
        queryWrapper.like(StringUtils.isNotEmpty(channels), "channels", channels);

        String isCommissionSettlement = storeInfoQuery.getIsCommissionSettlement();
        queryWrapper.eq(StringUtils.isNotEmpty(isCommissionSettlement), "is_commission_settlement", isCommissionSettlement);

        Date departureTime = storeInfoQuery.getDepartureTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginDepartureTime")) && StringUtils.isNotNull(params.get("endDepartureTime")), "departure_time", params.get("beginDepartureTime"), params.get("endDepartureTime"));

        String businessLicenseName = storeInfoQuery.getBusinessLicenseName();
        queryWrapper.like(StringUtils.isNotEmpty(businessLicenseName), "business_license_name", businessLicenseName);

        String legalPerson = storeInfoQuery.getLegalPerson();
        queryWrapper.like(StringUtils.isNotEmpty(legalPerson), "legal_person", legalPerson);

        Date expireTime = storeInfoQuery.getExpireTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginExpireTime")) && StringUtils.isNotNull(params.get("endExpireTime")), "expire_time", params.get("beginExpireTime"), params.get("endExpireTime"));

        String isOrderAccomplish = storeInfoQuery.getIsOrderAccomplish();
        queryWrapper.eq(StringUtils.isNotEmpty(isOrderAccomplish), "is_order_accomplish", isOrderAccomplish);

        String isBonaFideRedemption = storeInfoQuery.getIsBonaFideRedemption();
        queryWrapper.eq(StringUtils.isNotEmpty(isBonaFideRedemption), "is_bona_fide_redemption", isBonaFideRedemption);

        String isBail = storeInfoQuery.getIsBail();
        queryWrapper.eq(StringUtils.isNotEmpty(isBail), "is_bail", isBail);

        String isAlipay = storeInfoQuery.getIsAlipay();
        queryWrapper.eq(StringUtils.isNotEmpty(isAlipay), "is_alipay", isAlipay);

        Long deptId = storeInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Long userId = storeInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = storeInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = storeInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = storeInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        return queryWrapper;
    }

    /**
     * description: 校验主管、客服、运营、部门是否存在
     * author: YY
     * method: checkUserAndDept
     * date: 2025/3/3 20:20
     * param:
     * param: storeInfo
     * return: void
     **/
    private void checkUserAndDept(StoreInfo storeInfo) {
        //校验主管、客服、运营、部门是否存在
        //记录部门是否存在
        Long deptId = storeInfo.getDeptId();
        List<Long> deptIds = new ArrayList<>();
        if (StringUtils.isNull(deptId)) {
            throw new ServiceException("请选择部门！！！");
        } else {
            if (StringUtils.isNull(deptService.selectDeptById(deptId))) {
                throw new ServiceException("部门不存在！！！");
            }
            //获取门包括下级部门
            deptIds = deptService.selectDeptByIdReturnIds(deptId);
        }
        if (StringUtils.isNull(storeInfo.getPrincipalId())) {
            throw new ServiceException("请选择主管！！！");
        } else {
            SysUser principalUser = userService.selectUserById(storeInfo.getPrincipalId());
            if (StringUtils.isNull(principalUser)) {
                throw new ServiceException("主管不存在！！！");
            }
            if (!deptIds.contains(principalUser.getDeptId())) {
                throw new ServiceException("主管不在该部门内！！！");
            }
        }
        if (StringUtils.isNotNull(storeInfo.getServiceId())) {
            SysUser serviceUser = userService.selectUserById(storeInfo.getServiceId());
            if (StringUtils.isNull(serviceUser)) {
                throw new ServiceException("客服不存在！！！");
            }
            if (!deptIds.contains(serviceUser.getDeptId())) {
                throw new ServiceException("客服不在该部门内！！！");
            }
        }
        if (StringUtils.isNotNull(storeInfo.getOperationId())) {
            SysUser operationUser = userService.selectUserById(storeInfo.getOperationId());
            if (StringUtils.isNull(operationUser)) {
                throw new ServiceException("运营不存在！！！");
            }
            if (!deptIds.contains(operationUser.getDeptId())) {
                throw new ServiceException("运营不在该部门内！！！");
            }
        }
    }


    @Override
    public List<StoreInfoVo> convertVoList(List<StoreInfo> storeInfoList) {
        if (StringUtils.isEmpty(storeInfoList)) {
            return Collections.emptyList();
        }
        return storeInfoList.stream().map(StoreInfoVo::objToVo).collect(Collectors.toList());
    }

}
