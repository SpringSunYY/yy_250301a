package com.lz.manage.service.impl;

import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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

import com.lz.manage.model.domain.*;
import com.lz.manage.model.enums.CommonWhetherEnum;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoCountVo;
import com.lz.manage.service.*;
import com.lz.system.service.ISysDeptService;
import com.lz.system.service.ISysUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.PurchaseOrderInfoMapper;
import com.lz.manage.model.dto.purchaseOrderInfo.PurchaseOrderInfoQuery;
import com.lz.manage.model.vo.purchaseOrderInfo.PurchaseOrderInfoVo;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 采购发货信息Service业务层处理
 *
 * @author YY
 * @date 2025-03-03
 */
@Service
public class PurchaseOrderInfoServiceImpl extends ServiceImpl<PurchaseOrderInfoMapper, PurchaseOrderInfo> implements IPurchaseOrderInfoService {
    @Resource
    private PurchaseOrderInfoMapper purchaseOrderInfoMapper;

    @Resource
    private IStoreInfoService storeInfoService;

    @Resource
    private IPurchaseAccountInfoService accountInfoService;

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    @Lazy
    private IBPOrderInfoService ibpOrderInfoService;

    @Resource
    @Lazy
    private IReturnOrderInfoService returnOrderInfoService;

    @Resource
    private IReplacementOrderInfoService replacementOrderInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;
    //region mybatis代码

    /**
     * 查询采购发货信息
     *
     * @param id 采购发货信息主键
     * @return 采购发货信息
     */
    @Override
    public PurchaseOrderInfo selectPurchaseOrderInfoById(Long id) {
        return purchaseOrderInfoMapper.selectPurchaseOrderInfoById(id);
    }

    /**
     * 查询采购发货信息列表
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 采购发货信息
     */
    @DataScope(userAlias = "tb_purchase_order_info", deptAlias = "tb_purchase_order_info")
    @Override
    public List<PurchaseOrderInfo> selectPurchaseOrderInfoList(PurchaseOrderInfo purchaseOrderInfo) {
        List<PurchaseOrderInfo> purchaseOrderInfos = purchaseOrderInfoMapper.selectPurchaseOrderInfoList(purchaseOrderInfo);
        for (PurchaseOrderInfo info : purchaseOrderInfos) {
            StoreInfo storeInfo = storeInfoService.selectStoreInfoById(info.getStoreId());
            if (StringUtils.isNotNull(storeInfo)) {
                info.setStoreName(storeInfo.getStoreName());
            }
            PurchaseAccountInfo purchaseAccountInfo = accountInfoService.selectPurchaseAccountInfoById(info.getPurchaseAccountId());
            if (StringUtils.isNotNull(purchaseAccountInfo)) {
                info.setPurchaseAccount(purchaseAccountInfo.getPurchaseAccount());
            }
            SysUser user = userService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(user)) {
                info.setUserName(user.getUserName());
            }
            SysDept dept = deptService.selectDeptById(info.getDeptId());
            if (StringUtils.isNotNull(dept)) {
                info.setDeptName(dept.getDeptName());
            }
        }
        return purchaseOrderInfos;
    }

    /**
     * 新增采购发货信息
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    @Override
    public int insertPurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        checkOrder(purchaseOrderInfo);
        //查询白嫖和退货订单
        BPOrderInfo bpOrderInfo = ibpOrderInfoService.selectBPOrderInfoByOrderNumber(purchaseOrderInfo.getOrderNumber());
        ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(purchaseOrderInfo.getOrderNumber());
        getOrderProfit(purchaseOrderInfo, returnOrderInfo, bpOrderInfo);
        purchaseOrderInfo.setCreateTime(DateUtils.getNowDate());
        return purchaseOrderInfoMapper.insertPurchaseOrderInfo(purchaseOrderInfo);
    }

    private void checkOrder(PurchaseOrderInfo purchaseOrderInfo) {
        ReplacementOrderInfo replacementOrderInfo = replacementOrderInfoService.selectReplacementOrderInfoByOrderNumber(purchaseOrderInfo.getOrderNumber());
        if (StringUtils.isNotNull(replacementOrderInfo)) {
            throw new ServiceException("已经存在返款订单");
        }
        //如果用户id为空，则默认为当前用户
        if (StringUtils.isNull(purchaseOrderInfo.getUserId())) {
            purchaseOrderInfo.setUserId(SecurityUtils.getUserId());
        }
        //查询店铺是否存在
        if (StringUtils.isNotNull(purchaseOrderInfo.getStoreId())) {
            StoreInfo storeInfo = storeInfoService.selectStoreInfoById(purchaseOrderInfo.getStoreId());
            if (StringUtils.isNull(storeInfo)) {
                throw new ServiceException("店铺不存在！！！");
            }
            //赋值店铺部门
            purchaseOrderInfo.setDeptId(storeInfo.getDeptId());
        }

        if (StringUtils.isNotNull(purchaseOrderInfo.getPurchaseAccountId())) {
            PurchaseAccountInfo purchaseAccountInfo = accountInfoService.selectPurchaseAccountInfoById(purchaseOrderInfo.getPurchaseAccountId());
            if (StringUtils.isNull(purchaseAccountInfo)) {
                throw new ServiceException("采购账号不存在！！！");
            }
        }
    }


    /**
     * description: 获取采购订单利润
     * author: YY
     * method: getOrderProfit
     * date: 2025/3/5 11:47
     * param:
     * param: purchaseOrderInfo
     * return: java.math.BigDecimal
     **/
    @Override
    public PurchaseOrderInfo getOrderProfit(PurchaseOrderInfo purchaseOrderInfo, ReturnOrderInfo returnOrderInfo, BPOrderInfo bpOrderInfo) {
        //计算订单利润 销售价格-采购进价-客户退货金额-客户白仅退款金额-售后补偿金额-采购补价+上家退款金额
        //先计算本实体拥有的内容 销售价格-采购进价-采购补价
        BigDecimal orderProfit = new BigDecimal(BigInteger.ZERO);
        if (StringUtils.isNotNull(purchaseOrderInfo.getSalesPrice())) {
            orderProfit = purchaseOrderInfo.getSalesPrice();
        }
        if (StringUtils.isNotNull(purchaseOrderInfo.getPurchasePrice())) {
            orderProfit = orderProfit.subtract(purchaseOrderInfo.getPurchasePrice());
        }
        if (StringUtils.isNotNull(purchaseOrderInfo.getPurchasePremium())) {
            orderProfit = orderProfit.subtract(purchaseOrderInfo.getPurchasePremium());
        }
        //退款
        if (StringUtils.isNull(returnOrderInfo)) {
            returnOrderInfo = new ReturnOrderInfo();
        }
        if (StringUtils.isNotNull(returnOrderInfo.getReturnPrice())) {
            orderProfit = orderProfit.subtract(returnOrderInfo.getReturnPrice());
        }
        //加上上家退款
        if (StringUtils.isNotNull(returnOrderInfo.getLastReturnPrice())) {
            orderProfit = orderProfit.add(returnOrderInfo.getLastReturnPrice());
        }
        //白嫖
        if (StringUtils.isNull(bpOrderInfo)) {
            bpOrderInfo = new BPOrderInfo();
        }
        if (StringUtils.isNotNull(bpOrderInfo.getAfterSalePrice())) {
            orderProfit = orderProfit.subtract(bpOrderInfo.getAfterSalePrice());
        }
        if (StringUtils.isNotNull(bpOrderInfo.getBPPrice())) {
            orderProfit = orderProfit.subtract(bpOrderInfo.getBPPrice());
        }
        purchaseOrderInfo.setOrderProfit(orderProfit);

        //利润率（利润/销售价格）
        if (orderProfit.equals(BigDecimal.ZERO) && purchaseOrderInfo.getSalesPrice().equals(BigDecimal.ZERO)) {
            purchaseOrderInfo.setOrderProfitRate(BigDecimal.ZERO);
        } else {
            BigDecimal orderProfitRate = orderProfit.divide(purchaseOrderInfo.getSalesPrice(), 2, RoundingMode.HALF_UP);
            purchaseOrderInfo.setOrderProfitRate(orderProfitRate);
        }

        return purchaseOrderInfo;
    }

    /**
     * 修改采购发货信息
     *
     * @param purchaseOrderInfo 采购发货信息
     * @return 结果
     */
    @Override
    public int updatePurchaseOrderInfo(PurchaseOrderInfo purchaseOrderInfo) {
        checkOrder(purchaseOrderInfo);
        BPOrderInfo bpOrderInfo = ibpOrderInfoService.selectBPOrderInfoByOrderNumber(purchaseOrderInfo.getOrderNumber());
        ReturnOrderInfo returnOrderInfo = returnOrderInfoService.selectReturnOrderByOrderNumber(purchaseOrderInfo.getOrderNumber());
        getOrderProfit(purchaseOrderInfo, returnOrderInfo, bpOrderInfo);
        purchaseOrderInfo.setUpdateBy(SecurityUtils.getUsername());
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
    public int deletePurchaseOrderInfoByIds(Long[] ids) {
        //ids不能为空
        if (ArrayUtils.isEmpty(ids)) {
            throw new ServiceException("删除失败，请选择要删除的数据！！！");
        }
        List<String> numbers = new ArrayList<>(ids.length);
        //先删除退货订单和白嫖订单
        for (PurchaseOrderInfo info : this.listByIds(Arrays.asList(ids))) {
            numbers.add(info.getOrderNumber());
        }
        ibpOrderInfoService.remove(new LambdaQueryWrapper<BPOrderInfo>().in(BPOrderInfo::getOrderNumber, numbers));
        returnOrderInfoService.remove(new LambdaQueryWrapper<ReturnOrderInfo>().in(ReturnOrderInfo::getOrderNumber, numbers));
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoByIds(ids);
    }

    /**
     * 删除采购发货信息信息
     *
     * @param id 采购发货信息主键
     * @return 结果
     */
    @Override
    public int deletePurchaseOrderInfoById(Long id) {
        return purchaseOrderInfoMapper.deletePurchaseOrderInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<PurchaseOrderInfo> getQueryWrapper(PurchaseOrderInfoQuery purchaseOrderInfoQuery) {
        QueryWrapper<PurchaseOrderInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = purchaseOrderInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        String orderNumber = purchaseOrderInfoQuery.getOrderNumber();
        queryWrapper.like(StringUtils.isNotEmpty(orderNumber), "order_number", orderNumber);

        String orderType = purchaseOrderInfoQuery.getOrderType();
        queryWrapper.eq(StringUtils.isNotEmpty(orderType), "order_type", orderType);

        Date purchaseTime = purchaseOrderInfoQuery.getPurchaseTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginPurchaseTime")) && StringUtils.isNotNull(params.get("endPurchaseTime")), "purchase_time", params.get("beginPurchaseTime"), params.get("endPurchaseTime"));

        Long storeId = purchaseOrderInfoQuery.getStoreId();
        queryWrapper.eq(StringUtils.isNotNull(storeId), "store_id", storeId);

        String buyerNumber = purchaseOrderInfoQuery.getBuyerNumber();
        queryWrapper.like(StringUtils.isNotEmpty(buyerNumber), "buyer_number", buyerNumber);

        String purchaseChannelType = purchaseOrderInfoQuery.getPurchaseChannelType();
        queryWrapper.eq(StringUtils.isNotEmpty(purchaseChannelType), "purchase_channel_type", purchaseChannelType);

        String purchaseChannelDetail = purchaseOrderInfoQuery.getPurchaseChannelDetail();
        queryWrapper.eq(StringUtils.isNotEmpty(purchaseChannelDetail), "purchase_channel_detail", purchaseChannelDetail);

        Long purchaseAccountId = purchaseOrderInfoQuery.getPurchaseAccountId();
        queryWrapper.eq(StringUtils.isNotNull(purchaseAccountId), "purchase_account_id", purchaseAccountId);

        String purchaseOrder = purchaseOrderInfoQuery.getPurchaseOrder();
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder), "purchase_order", purchaseOrder);

        String supplierName = purchaseOrderInfoQuery.getSupplierName();
        queryWrapper.like(StringUtils.isNotEmpty(supplierName), "supplier_name", supplierName);

        String shipmentsOrder = purchaseOrderInfoQuery.getShipmentsOrder();
        queryWrapper.like(StringUtils.isNotEmpty(shipmentsOrder), "shipments_order", shipmentsOrder);

        String hasReturn = purchaseOrderInfoQuery.getHasReturn();
        queryWrapper.eq(StringUtils.isNotEmpty(hasReturn), "has_return", hasReturn);

        String hasBP = purchaseOrderInfoQuery.getHasBP();
        queryWrapper.eq(StringUtils.isNotEmpty(hasBP), "has_b_p", hasBP);

        Long userId = purchaseOrderInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = purchaseOrderInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = purchaseOrderInfoQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        Date updateTime = purchaseOrderInfoQuery.getUpdateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateTime")) && StringUtils.isNotNull(params.get("endUpdateTime")), "update_time", params.get("beginUpdateTime"), params.get("endUpdateTime"));

        Long deptId = purchaseOrderInfoQuery.getDeptId();
        queryWrapper.eq(StringUtils.isNotNull(deptId), "dept_id", deptId);

        return queryWrapper;
    }

    @Override
    public List<PurchaseOrderInfoVo> convertVoList(List<PurchaseOrderInfo> purchaseOrderInfoList) {
        if (StringUtils.isEmpty(purchaseOrderInfoList)) {
            return Collections.emptyList();
        }
        return purchaseOrderInfoList.stream().map(PurchaseOrderInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public String importPurchaseOrderInfo(List<PurchaseOrderInfo> purchaseOrderInfoList) {
        if (StringUtils.isEmpty(purchaseOrderInfoList)) {
            return StringUtils.format("数据不能为空");
        }
        Date nowDate = DateUtils.getNowDate();
        //新增订单集合，判断补单信息是否存在此订单
        List<String> orderNumbers = new ArrayList<>(purchaseOrderInfoList.size());
        //遍历 判断店铺、账号、创建人是否存在
        for (int i = 0; i < purchaseOrderInfoList.size(); i++) {
            PurchaseOrderInfo info = purchaseOrderInfoList.get(i);
            //订单类型不能为空
            int index = i + 1;
            if (StringUtils.isEmpty(info.getOrderType())) {
                return StringUtils.format("第{}行订单类型不能为空", index);
            }
            if (StringUtils.isEmpty(info.getOrderNumber())) {
                return StringUtils.format("第{}行订单编号不能为空", index);
            }
            if (StringUtils.isNull(info.getStoreName())) {
                return StringUtils.format("第{}行店铺名称不能为空", index);
            }
            if (StringUtils.isEmpty(info.getUserName())) {
                return StringUtils.format("第{}行创建人不能为空", index);
            }
            //填入默认值
            if (StringUtils.isEmpty(info.getHasBP())) {
                info.setHasBP(CommonWhetherEnum.COMMON_WHETHER_2.getValue());
            }
            if (StringUtils.isEmpty(info.getHasReturn())) {
                info.setHasReturn(CommonWhetherEnum.COMMON_WHETHER_2.getValue());
            }
            info.setCreateTime(nowDate);
            orderNumbers.add(info.getOrderNumber());
        }
        //根据订单编号列表查询采购订单
        List<PurchaseOrderInfo> purchaseOrderInfos = this.list(new LambdaQueryWrapper<PurchaseOrderInfo>().in(PurchaseOrderInfo::getOrderNumber, orderNumbers));
        if (StringUtils.isNotEmpty(purchaseOrderInfos)) {
            return StringUtils.format("订单编号{}已在采购订单内,不可添加", purchaseOrderInfos.get(0).getOrderNumber());
        }
        //查询补货订单是否存在，存在不可添加
        List<ReplacementOrderInfo> replacementOrderInfos = replacementOrderInfoService.list(new LambdaQueryWrapper<ReplacementOrderInfo>().in(ReplacementOrderInfo::getOrderNumber, orderNumbers));
        if (StringUtils.isNotEmpty(replacementOrderInfos)) {
            return StringUtils.format("订单编号{}已在补货订单内,不可添加", replacementOrderInfos.get(0).getOrderNumber());
        }
        //通过数据库查询判断
        for (int i = 0; i < purchaseOrderInfoList.size(); i++) {
            PurchaseOrderInfo info = purchaseOrderInfoList.get(i);
            int index = i + 1;
            //数据库查询校验
            //查询店铺是否存在 存在则给订单赋值部门信息，不存在抛出店铺不存在
            StoreInfo storeInfo = storeInfoService.selectStoreInfoByStoreName(info.getStoreName());
            if (StringUtils.isNull(storeInfo)) {
                return StringUtils.format("第{}行店铺不存在", index);
            }
            info.setStoreId(storeInfo.getId());
            info.setDeptId(storeInfo.getDeptId());

            SysUser user = userService.selectUserByUserName(info.getUserName());
            if (StringUtils.isNull(user)) {
                return StringUtils.format("第{}行创建人不存在", index);
            }
            info.setUserId(user.getUserId());
            if (StringUtils.isNotEmpty(info.getPurchaseAccount())) {
                PurchaseAccountInfo purchaseAccountInfo = accountInfoService.selectPurchaseAccountInfoByAccount(info.getPurchaseAccount());
                if (StringUtils.isNull(purchaseAccountInfo)) {
                    return StringUtils.format("第{}行填入了采购账号信息，但是账号不存在", index);
                }
                info.setPurchaseAccountId(purchaseAccountInfo.getId());
            }
            this.getOrderProfit(info, new ReturnOrderInfo(), new BPOrderInfo());
        }
        Boolean execute = transactionTemplate.execute(item -> {
            try {
                purchaseOrderInfoMapper.insert(purchaseOrderInfoList);
                return true;
            } catch (Exception e) {
                log.error("导入采购订单数据失败，原因：", e);
                throw new ServiceException("导入数据失败，请检查数据结构是否正确，或者查看是否有相同的采购编号,数据格式是否和描述相同！！！");
            }
        });
        return StringUtils.format("导入成功。成功导入{}条数据！！！", purchaseOrderInfoList.size());
    }

    @Override
    public PurchaseOrderInfo selectPurchaseOrderInfoByOrderNumber(String orderNumber) {
        return this.getOne(Wrappers.<PurchaseOrderInfo>lambdaQuery().eq(PurchaseOrderInfo::getOrderNumber, orderNumber));
    }

    @Override
    @DataScope(userAlias = "tb_purchase_order_info", deptAlias = "tb_purchase_order_info")
    public PurchaseOrderInfoCountVo getPurchaseOrderInfoCount(PurchaseOrderInfo purchaseOrderInfo) {
        PurchaseOrderInfoCountVo purchaseOrderInfoCount = purchaseOrderInfoMapper.getPurchaseOrderInfoCount(purchaseOrderInfo);
        if (StringUtils.isNull(purchaseOrderInfoCount)) {
            purchaseOrderInfoCount = new PurchaseOrderInfoCountVo();
            purchaseOrderInfoCount.setOrderProfitCount(new BigDecimal(0));
            purchaseOrderInfoCount.setSalesNumberCount(0L);
            purchaseOrderInfoCount.setSalesPriceCount(new BigDecimal("0"));
            purchaseOrderInfoCount.setPurchasePriceCount(new BigDecimal("0"));
            purchaseOrderInfoCount.setPurchasePremiumCount(new BigDecimal("0"));
            purchaseOrderInfoCount.setOrderCount(0L);
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getOrderProfitCount())) {
            purchaseOrderInfoCount.setOrderProfitCount(new BigDecimal(0));
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getSalesNumberCount())) {
            purchaseOrderInfoCount.setSalesNumberCount(0L);
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getSalesPriceCount())) {
            purchaseOrderInfoCount.setSalesPriceCount(new BigDecimal("0"));
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getPurchasePriceCount())) {
            purchaseOrderInfoCount.setPurchasePriceCount(new BigDecimal("0"));
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getPurchasePremiumCount())) {
            purchaseOrderInfoCount.setPurchasePremiumCount(new BigDecimal("0"));
        }
        if (StringUtils.isNull(purchaseOrderInfoCount.getOrderCount())) {
            purchaseOrderInfoCount.setOrderCount(0L);
        }
        return purchaseOrderInfoCount;
    }

}
