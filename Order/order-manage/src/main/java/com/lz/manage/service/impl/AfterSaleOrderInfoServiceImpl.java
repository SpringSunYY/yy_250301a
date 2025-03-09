package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysDept;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

import com.lz.common.utils.DateUtils;

import javax.annotation.Resource;

import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.model.enums.CommonWhetherEnum;
import com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderCountVo;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.IReturnOrderInfoService;
import com.lz.manage.service.IStoreInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.AfterSaleOrderInfoMapper;
import com.lz.manage.model.domain.AfterSaleOrderInfo;
import com.lz.manage.service.IAfterSaleOrderInfoService;
import com.lz.manage.model.dto.afterSaleOrderInfo.AfterSaleOrderInfoQuery;
import com.lz.manage.model.vo.afterSaleOrderInfo.AfterSaleOrderInfoVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 售后订单信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-09
 */
@Service
public class AfterSaleOrderInfoServiceImpl extends ServiceImpl<AfterSaleOrderInfoMapper, AfterSaleOrderInfo> implements IAfterSaleOrderInfoService {
    @Resource
    private AfterSaleOrderInfoMapper afterSaleOrderInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IPurchaseOrderInfoService orderInfoService;

    @Resource
    @Lazy
    private IReturnOrderInfoService returnOrderInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private IStoreInfoService storeInfoService;

    //region mybatis代码

    /**
     * 查询售后订单信息
     *
     * @param id 售后订单信息主键
     * @return 售后订单信息
     */
    @Override
    public AfterSaleOrderInfo selectAfterSaleOrderInfoById(Long id) {
        return afterSaleOrderInfoMapper.selectAfterSaleOrderInfoById(id);
    }

    /**
     * 查询售后订单信息列表
     *
     * @param afterSaleOrderInfo 售后订单信息
     * @return 售后订单信息
     */
    @DataScope(userAlias = "tb_after_sale_order_info", deptAlias = "tb_after_sale_order_info")
    @Override
    public List<AfterSaleOrderInfo> selectAfterSaleOrderInfoList(AfterSaleOrderInfo afterSaleOrderInfo) {
        List<AfterSaleOrderInfo> afterSaleOrderInfos = afterSaleOrderInfoMapper.selectAfterSaleOrderInfoList(afterSaleOrderInfo);
        for (AfterSaleOrderInfo info : afterSaleOrderInfos) {
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
            StoreInfo storeInfo = storeInfoService.selectStoreInfoById(info.getStoreId());
            if (StringUtils.isNotNull(storeInfo)) {
                info.setStoreName(storeInfo.getStoreName());
            }
        }
        return afterSaleOrderInfos;
    }

    /**
     * 新增售后订单信息
     *
     * @param afterSaleOrderInfo 售后订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo) {
        PurchaseOrderInfo orderInfo = checkAfterOrder(afterSaleOrderInfo);
        orderInfo.setHasAfterSale(CommonWhetherEnum.COMMON_WHETHER_1.getValue());

        afterSaleOrderInfo.setCreateTime(DateUtils.getNowDate());
        int i = afterSaleOrderInfoMapper.insertAfterSaleOrderInfo(afterSaleOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }

    private PurchaseOrderInfo checkAfterOrder(AfterSaleOrderInfo afterSaleOrderInfo) {
        PurchaseOrderInfo orderInfo = orderInfoService.selectPurchaseOrderInfoByOrderNumber(afterSaleOrderInfo.getOrderNumber());
        if (StringUtils.isNull(orderInfo)) {
            throw new ServiceException("订单不存在" + afterSaleOrderInfo.getOrderNumber());
        }
        orderInfo.setHasReturn(CommonWhetherEnum.COMMON_WHETHER_1.getValue());
        afterSaleOrderInfo.setStoreId(orderInfo.getStoreId());
        afterSaleOrderInfo.setDeptId(orderInfo.getDeptId());
        afterSaleOrderInfo.setUserId(orderInfo.getUserId());
        afterSaleOrderInfo.setOrderType(orderInfo.getOrderType());
        return orderInfo;
    }

    /**
     * 修改售后订单信息
     *
     * @param afterSaleOrderInfo 售后订单信息
     * @return 结果
     */
    @Transactional
    @Override
    public int updateAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo) {
        //不可修改订单编号
        AfterSaleOrderInfo old = this.selectAfterSaleOrderInfoById(afterSaleOrderInfo.getId());
        if (StringUtils.isNotNull(old) && !old.getOrderNumber().equals(afterSaleOrderInfo.getOrderNumber())) {
            throw new ServiceException("不可以修改订单编号");
        }

        PurchaseOrderInfo orderInfo = checkAfterOrder(afterSaleOrderInfo);
        afterSaleOrderInfo.setUpdateBy(SecurityUtils.getUsername());
        afterSaleOrderInfo.setUpdateTime(DateUtils.getNowDate());
        int i = afterSaleOrderInfoMapper.updateAfterSaleOrderInfo(afterSaleOrderInfo);
        orderInfoService.updatePurchaseOrderInfo(orderInfo);
        return i;
    }

    /**
     * 批量删除售后订单信息
     *
     * @param ids 需要删除的售后订单信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSaleOrderInfoByIds(Long[] ids) {
        return afterSaleOrderInfoMapper.deleteAfterSaleOrderInfoByIds(ids);
    }

    /**
     * 删除售后订单信息信息
     *
     * @param id 售后订单信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSaleOrderInfoById(Long id) {
        return afterSaleOrderInfoMapper.deleteAfterSaleOrderInfoById(id);
    }

    @Override
    public AfterSaleOrderInfo selectAfterSaleOrderInfoByOrderNumber(String orderNumber) {
        return this.getOne(new LambdaQueryWrapper<AfterSaleOrderInfo>().eq(AfterSaleOrderInfo::getOrderNumber, orderNumber));
    }

    //endregion


    @Override
    public QueryWrapper<AfterSaleOrderInfo> getQueryWrapper(AfterSaleOrderInfoQuery afterSaleOrderInfoQuery) {
        QueryWrapper<AfterSaleOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = afterSaleOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = afterSaleOrderInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String orderNumber = afterSaleOrderInfoQuery.getOrderNumber();
        queryWrapper.eq(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber);

        String orderType = afterSaleOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType), "order_type", orderType);

        Long storeId = afterSaleOrderInfoQuery.getStoreId();
        queryWrapper.eq(StringUtils.isNotNull(storeId), "store_id", storeId);

        BigDecimal afterSalePrice = afterSaleOrderInfoQuery.getAfterSalePrice();
        queryWrapper.eq(StringUtils.isNotNull(afterSalePrice), "after_sale_price", afterSalePrice);

        Date afterSaleTime = afterSaleOrderInfoQuery.getAfterSaleTime();
        queryWrapper.eq(StringUtils.isNotNull(afterSaleTime), "after_sale_time", afterSaleTime);

        String afterSaleImage = afterSaleOrderInfoQuery.getAfterSaleImage();
        queryWrapper.eq(StringUtils.isNotEmpty(afterSaleImage), "after_sale_image", afterSaleImage);

        Long userId = afterSaleOrderInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Long deptId = afterSaleOrderInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        Date createTime = afterSaleOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = afterSaleOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = afterSaleOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        String remark = afterSaleOrderInfoQuery.getRemark();
        queryWrapper.eq(StringUtils.isNotEmpty(remark), "remark", remark);

        return queryWrapper;
    }

    @Override
    public List<AfterSaleOrderInfoVo> convertVoList(List<AfterSaleOrderInfo> afterSaleOrderInfoList) {
        if (StringUtils.isEmpty(afterSaleOrderInfoList)) {
            return Collections.emptyList();
        }
        return afterSaleOrderInfoList.stream().map(AfterSaleOrderInfoVo::objToVo).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int addOrUpdateAfterSaleOrderInfo(AfterSaleOrderInfo afterSaleOrderInfo) {
        PurchaseOrderInfo orderInfo = checkAfterOrder(afterSaleOrderInfo);
        this.saveOrUpdate(afterSaleOrderInfo);
        return orderInfoService.updatePurchaseOrderInfo(orderInfo);
    }
    @DataScope(userAlias = "tb_after_sale_order_info", deptAlias = "tb_after_sale_order_info")
    @Override
    public AfterSaleOrderCountVo getAfterSaleOrderCount(AfterSaleOrderInfo afterSaleOrderInfo) {
        return afterSaleOrderInfoMapper.getAfterSaleOrderCount(afterSaleOrderInfo);
    }

}
