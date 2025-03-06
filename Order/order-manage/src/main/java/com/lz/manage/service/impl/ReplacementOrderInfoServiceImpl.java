package com.lz.manage.service.impl;

import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import com.lz.manage.model.domain.PurchaseOrderInfo;
import com.lz.manage.model.domain.StoreInfo;
import com.lz.manage.service.IPurchaseOrderInfoService;
import com.lz.manage.service.IStoreInfoService;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.ReplacementOrderInfoMapper;
import com.lz.manage.model.domain.ReplacementOrderInfo;
import com.lz.manage.service.IReplacementOrderInfoService;
import com.lz.manage.model.dto.replacementOrderInfo.ReplacementOrderInfoQuery;
import com.lz.manage.model.vo.replacementOrderInfo.ReplacementOrderInfoVo;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 补单明细Service业务层处理
 *
 * @author YY
 * @date 2025-03-03
 */
@Service
public class ReplacementOrderInfoServiceImpl extends ServiceImpl<ReplacementOrderInfoMapper, ReplacementOrderInfo> implements IReplacementOrderInfoService {
    @Resource
    private ReplacementOrderInfoMapper replacementOrderInfoMapper;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private IStoreInfoService storeInfoService;

    @Resource
    @Lazy
    private IPurchaseOrderInfoService purchaseOrderInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    //region mybatis代码

    /**
     * 查询补单明细
     *
     * @param id 补单明细主键
     * @return 补单明细
     */
    @Override
    public ReplacementOrderInfo selectReplacementOrderInfoById(Long id) {
        return replacementOrderInfoMapper.selectReplacementOrderInfoById(id);
    }

    /**
     * 查询补单明细列表
     *
     * @param replacementOrderInfo 补单明细
     * @return 补单明细
     */
    @Override
    public List<ReplacementOrderInfo> selectReplacementOrderInfoList(ReplacementOrderInfo replacementOrderInfo) {
        List<ReplacementOrderInfo> orderInfos = replacementOrderInfoMapper.selectReplacementOrderInfoList(replacementOrderInfo);
        for (ReplacementOrderInfo info : orderInfos) {
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
        return orderInfos;
    }

    /**
     * 新增补单明细
     *
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    @Override
    public int insertReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo) {
        checkReplacementOrder(replacementOrderInfo);

        replacementOrderInfo.setCreateTime(DateUtils.getNowDate());
        return replacementOrderInfoMapper.insertReplacementOrderInfo(replacementOrderInfo);
    }

    private void checkReplacementOrder(ReplacementOrderInfo replacementOrderInfo) {
        //查询采购订单是否已经存在此订单，如果存在则不允许添加
        PurchaseOrderInfo orderInfo = purchaseOrderInfoService.selectPurchaseOrderInfoByOrderNumber(replacementOrderInfo.getOrderNumber());
        if (StringUtils.isNotNull(orderInfo)) {
            throw new ServiceException("已经存在采购订单");
        }

        StoreInfo storeInfo = storeInfoService.selectStoreInfoById(replacementOrderInfo.getStoreId());
        if (StringUtils.isNull(storeInfo)) {
            throw new ServiceException("店铺不存在！！！");
        }
        replacementOrderInfo.setDeptId(storeInfo.getDeptId());
        if (StringUtils.isNull(replacementOrderInfo.getUserId())) {
            replacementOrderInfo.setUserId(SecurityUtils.getUserId());
        }
        calculateTotalPrice(replacementOrderInfo);
    }

    private static void calculateTotalPrice(ReplacementOrderInfo replacementOrderInfo) {
        if (StringUtils.isNull(replacementOrderInfo.getActuallyPrice())) {
            replacementOrderInfo.setActuallyPrice(BigDecimal.ZERO);
        }
        if (StringUtils.isNull(replacementOrderInfo.getCommission())) {
            replacementOrderInfo.setCommission(BigDecimal.ZERO);
        }
        BigDecimal totalPrice = new BigDecimal(BigInteger.ZERO);
        totalPrice = replacementOrderInfo.getActuallyPrice().add(replacementOrderInfo.getCommission());
        replacementOrderInfo.setTotalPrice(totalPrice);
    }

    /**
     * 修改补单明细
     *
     * @param replacementOrderInfo 补单明细
     * @return 结果
     */
    @Override
    public int updateReplacementOrderInfo(ReplacementOrderInfo replacementOrderInfo) {
        checkReplacementOrder(replacementOrderInfo);
        replacementOrderInfo.setUpdateTime(DateUtils.getNowDate());
        return replacementOrderInfoMapper.updateReplacementOrderInfo(replacementOrderInfo);
    }

    /**
     * 批量删除补单明细
     *
     * @param ids 需要删除的补单明细主键
     * @return 结果
     */
    @Override
    public int deleteReplacementOrderInfoByIds(Long[] ids) {
        return replacementOrderInfoMapper.deleteReplacementOrderInfoByIds(ids);
    }

    /**
     * 删除补单明细信息
     *
     * @param id 补单明细主键
     * @return 结果
     */
    @Override
    public int deleteReplacementOrderInfoById(Long id) {
        return replacementOrderInfoMapper.deleteReplacementOrderInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<ReplacementOrderInfo> getQueryWrapper(ReplacementOrderInfoQuery replacementOrderInfoQuery) {
        QueryWrapper<ReplacementOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = replacementOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = replacementOrderInfoQuery.getOrderNumber();
        queryWrapper.like(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber);

        Long storeId = replacementOrderInfoQuery.getStoreId();
        queryWrapper.eq(StringUtils.isNotNull(storeId), "store_id", storeId);

        Date dateTime = replacementOrderInfoQuery.getDateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginDateTime")) && StringUtils.isNotNull(params.get("endDateTime")), "date_time", params.get("beginDateTime"), params.get("endDateTime"));

        String wxNumber = replacementOrderInfoQuery.getWxNumber();
        queryWrapper.like(StringUtils.isNotEmpty(wxNumber), "wx_number", wxNumber);

        String tbNumber = replacementOrderInfoQuery.getTbNumber();
        queryWrapper.like(StringUtils.isNotEmpty(tbNumber), "tb_number", tbNumber);

        String returnStatus = replacementOrderInfoQuery.getReturnStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(returnStatus), "return_status", returnStatus);

        Long userId = replacementOrderInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = replacementOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = replacementOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = replacementOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        Long deptId = replacementOrderInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<ReplacementOrderInfoVo> convertVoList(List<ReplacementOrderInfo> replacementOrderInfoList) {
        if (StringUtils.isEmpty(replacementOrderInfoList)) {
            return Collections.emptyList();
        }
        return replacementOrderInfoList.stream().map(ReplacementOrderInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public ReplacementOrderInfo selectReplacementOrderInfoByOrderNumber(String orderNumber) {
        return this.getOne(new LambdaQueryWrapper<ReplacementOrderInfo>().eq(ReplacementOrderInfo::getOrderNumber, orderNumber));
    }

    @Override
    public String importReplacementOrderInfo(List<ReplacementOrderInfo> replacementOrderInfoList) {
        //设置初值 校验
        Date nowDate = DateUtils.getNowDate();
        //新建一个订单编号集合，用于记录订单编号 查询采购订单内是否存在
        List<String> orderNumbers = new ArrayList<>(replacementOrderInfoList.size());
        for (int i = 0; i < replacementOrderInfoList.size(); i++) {
            ReplacementOrderInfo info = replacementOrderInfoList.get(i);
            int index = i + 1;
            info.setCreateTime(nowDate);

            if (StringUtils.isEmpty(info.getOrderNumber())) {
                return StringUtils.format("第{}行订单编号不能为空", index);
            }
            if (StringUtils.isNull(info.getStoreName())) {
                return StringUtils.format("第{}行店铺名称不能为空", index);
            }
            if (StringUtils.isEmpty(info.getUserName())) {
                return StringUtils.format("第{}行创建人不能为空", index);
            }
            if (StringUtils.isEmpty(info.getReturnStatus())) {
                return StringUtils.format("第{}行返款状态不能为空", index);
            }
            if (StringUtils.isNull(info.getActuallyPrice())) {
                info.setActuallyPrice(BigDecimal.ZERO);
            }
            if (StringUtils.isNull(info.getCommission())) {
                info.setCommission(BigDecimal.ZERO);
            }
            //计算合计金额
            calculateTotalPrice(info);
            orderNumbers.add(info.getOrderNumber());
        }
        //根据订单编号列表查询采购订单
        List<PurchaseOrderInfo> purchaseOrderInfos = purchaseOrderInfoService.list(new LambdaQueryWrapper<PurchaseOrderInfo>().in(PurchaseOrderInfo::getOrderNumber, orderNumbers));
        if (StringUtils.isNotEmpty(purchaseOrderInfos)) {
            return StringUtils.format("订单编号{}已在采购订单内,不可添加", purchaseOrderInfos.get(0).getOrderNumber());
        }
        //查询捕获订单是否存在，存在不可添加
        List<ReplacementOrderInfo> replacementOrderInfos = this.list(new LambdaQueryWrapper<ReplacementOrderInfo>().in(ReplacementOrderInfo::getOrderNumber, orderNumbers));
        if (StringUtils.isNotEmpty(replacementOrderInfos)) {
            return StringUtils.format("订单编号{}已在补货订单内,不可添加", replacementOrderInfos.get(0).getOrderNumber());
        }
        return "";
    }

}
